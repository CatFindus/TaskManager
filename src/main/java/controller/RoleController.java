package controller;

import dto.response.RoleResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.JsonView;
import view.View;

import static constants.ApplicationConstants.CONTROLLER_METHOD_BEGIN;
import static constants.ApplicationConstants.CONTROLLER_METHOD_END;


@WebServlet(name = "roleServlet", urlPatterns = "/role/*")
public class RoleController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private View view;

    private void initialization(HttpServletResponse response) {
        view = new JsonView(response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug(CONTROLLER_METHOD_BEGIN, req.getMethod(), req.getPathInfo());
        initialization(resp);
        view.update(new RoleResponse());
        logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
    }
}
