package edu.isa681.web.landingpage;


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "LandingFilter", value = "/index")
public class LandingFilter implements Filter {
    Logger log = Logger.getLogger(LandingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path = req.getRequestURI();
        log.info("Path is" + path);

        if (!path.contains("/index") && !path.contains("/404") && !path.endsWith(".js") && !path.endsWith(".css") && !path.contains("sq")) {
            log.info("Inside Login logic");
            resp.sendRedirect(req.getContextPath() + "/404.jsp");
        } else if (path.contains("sq/")) {
            resp.sendRedirect(req.getContextPath() + "/sq/");
        } else {
            filterChain.doFilter(req, resp);
        }
    }


    @Override
    public void destroy() {
    }
}
//
//todo
//1. Create mini mvc thingy
//2. start with Player creattion, Player DashBoard and Player hearbeat.