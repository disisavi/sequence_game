package edu.isa681.web.login;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

@Path("/login")
public class login {
    private GoogleAuthorizationCodeFlow flow;
    private static final Collection<String> SCOPES = Arrays.asList("email", "profile");
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();


    @Context
    private HttpServletRequest request;

    @GET()
    public Response login(@Context ServletContext context) throws URISyntaxException {
        String state = new BigInteger(130, new SecureRandom()).toString(32);  // prevent request forgery
        request.getSession().setAttribute("state", state);

        if (request.getAttribute("loginDestination") != null) {
            request
                    .getSession()
                    .setAttribute("loginDestination", (String) request.getAttribute("loginDestination"));
        } else {
            request.getSession().setAttribute("loginDestination", "/books");
        }

        flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                context.getInitParameter("game.clientID"),
                context.getInitParameter("game.clientSecret"),
                SCOPES)
                .build();

        // Callback url should be the one registered in Google Developers Console
        String url =
                flow.newAuthorizationUrl()
                        .setRedirectUri(context.getInitParameter("game.callback"))
                        .setState(state)            // Prevent request forgery
                        .build();
        URI redirect = new URI(url);
        return Response.temporaryRedirect(redirect).build();
    }

}
