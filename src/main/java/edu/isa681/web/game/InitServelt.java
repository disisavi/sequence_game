package edu.isa681.web.game;

import edu.isa681.DOA.DOA;

import javax.servlet.*;
import java.io.IOException;

public class InitServelt implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        DOA doa = DOA.getDoa();
        GameController gameController = GameController.getGameController();
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
