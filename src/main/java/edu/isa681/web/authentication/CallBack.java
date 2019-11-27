package edu.isa681.web.authentication;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import edu.isa681.web.game.GameController;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

@Path("oauth2callback")
public class CallBack {
    private static final Collection<String> SCOPES = Arrays.asList("email", "profile");
    private static final String USERINFO_ENDPOINT
            = "https://www.googleapis.com/plus/v1/people/me/openIdConnect";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private GoogleAuthorizationCodeFlow flow;
    private GameController gameController = GameController.getGameController();
    @Context
    private HttpServletRequest req;

    @GET()
    public Response loginCallBack(@Context ServletContext context) throws IOException {
        // Ensure that this is no request forgery going on, and that the user
        // sending us this connect request is the user that was supposed to

        if (req.getSession().getAttribute("state") == null
                || !req.getParameter("state").equals((String) req.getSession().getAttribute("state"))) {
            return Response.temporaryRedirect(UriBuilder.fromPath("/index.jsp").build()).status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }


        req.getSession().removeAttribute("state");     // Remove one-time use state.

        flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                context.getInitParameter("game.clientID"),
                context.getInitParameter("game.clientSecret"),
                SCOPES).build();

        final GoogleTokenResponse tokenResponse =
                flow.newTokenRequest(req.getParameter("code"))
                        .setRedirectUri(context.getInitParameter("game.callback"))
                        .execute();

        req.getSession().setAttribute("token", tokenResponse.toString()); // Keep track of the token.
        final Credential credential = flow.createAndStoreCredential(tokenResponse, null);
        System.out.println(tokenResponse.parseIdToken().getPayload());
        final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);

        final GenericUrl url = new GenericUrl(USERINFO_ENDPOINT);      // Make an authenticated request.
        final HttpRequest request = requestFactory.buildGetRequest(url);
        request.getHeaders().setContentType("application/json");

        final String jsonIdentity = request.execute().parseAsString();
        @SuppressWarnings("unchecked")
        HashMap<String, String> userIdResult =
                new ObjectMapper().readValue(jsonIdentity, HashMap.class);
        // From this map, extract the relevant profile info and store it in the session.
        req.getSession().setAttribute("userEmail", userIdResult.get("email"));
        req.getSession().setAttribute("userId", userIdResult.get("sub"));
        req.getSession().setAttribute("userImageUrl", userIdResult.get("picture"));
        return Response.temporaryRedirect(UriBuilder.fromPath((String) req.getSession().getAttribute("loginDestination")).build()).build();
    }
}