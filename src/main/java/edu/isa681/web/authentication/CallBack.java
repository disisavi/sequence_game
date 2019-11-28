package edu.isa681.web.authentication;


import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import edu.isa681.web.game.GameController;
import edu.isa681.web.server.Params;

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

@Path("/oauth2callback")
public class CallBack {
    private static final Collection<String> SCOPES = Arrays.asList("email", "profile");
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private GoogleAuthorizationCodeFlow flow;
    private GameController gameController = GameController.getGameController();
    @Context
    private HttpServletRequest request;

    @GET()
    public Response loginCallBack(@Context ServletContext context) throws IOException {

        // Ensure that this is no request forgery going on, and that the user
        // sending us this connect request is the user that was supposed to
        if (request.getSession().getAttribute("state") == null
                || !request.getParameter("state").equals((String) request.getSession().getAttribute("state"))) {
            return Response.temporaryRedirect(UriBuilder.fromPath("/index.jsp").build()).status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }


        request.getSession().removeAttribute("state");     // Remove one-time use state.

        flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                context.getInitParameter("game.clientID"),
                context.getInitParameter("game.clientSecret"),
                SCOPES).build();

        final GoogleTokenResponse tokenResponse =
                flow.newTokenRequest(request.getParameter("code"))
                        .setRedirectUri(Params.getCallBack())
                        .execute();

        request.getSession().setAttribute("token", tokenResponse.toString()); // Keep track of the token.
        IdToken.Payload payload = tokenResponse.parseIdToken().getPayload();
        gameController.signUporInNewPlayer(payload);

        return Response.temporaryRedirect(UriBuilder.fromPath("../views/PlayerDashboard.jsp").build()).build();

    }
}
