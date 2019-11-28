//package edu.isa681.web.landingpage;
//
//
//import org.apache.log4j.Logger;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
//
//
//@Provider
//public class LandingFilter implements ContainerRequestFilter {
//    Logger log = Logger.getLogger(LandingFilter.class);
//
//    @Override
//    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse resp = (HttpServletResponse) servletResponse;
//        String path = req.getRequestURI();
//        log.info("Path is" + path);
//
//        if (!path.contains("/index") && !path.contains("/404") && !path.endsWith(".js") && !path.endsWith(".css") && !path.contains("sq")) {
//            log.info("Inside Login logic");
//            resp.sendRedirect(req.getContextPath() + "/404.jsp");
//        } else if (path.contains("sq/")) {
//            resp.sendRedirect(req.getContextPath() + "/sq/");
//        } else {
//            filterChain.doFilter(req, resp);
//        }
//    }
//}
////
////todo
////1. Create mini mvc thingy
////2. start with Player creattion, Player DashBoard and Player hearbeat.