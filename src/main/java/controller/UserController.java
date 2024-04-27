package controller;

import dto.request.UserRequest;
import dto.response.LoginResponse;
import dto.response.UserFullResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mapper.viewmapper.JsonMapper;
import mapper.viewmapper.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import view.JsonView;
import view.View;

import static constants.ApplicationConstants.*;

@WebServlet(name = "userServlet", urlPatterns = "/users/*")
public class UserController extends HttpServlet {
    private UserService service;
    private View view;
    private ViewMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private void initialization(HttpServletResponse response) {
        service = new UserService();
        view = new JsonView(response);
        mapper = new JsonMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug(CONTROLLER_METHOD_BEGIN, req.getMethod(), req.getPathInfo());
        initialization(resp);
        HttpSession session = req.getSession();
        if (session.getAttribute(SERVLET_LOGGED) == null || session.getAttribute(SERVLET_USER_ID) == null) {
            session.invalidate();
            view.update(new LoginResponse(false, FIELD_SEPARATOR));
            logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
        } else {
            if (req.getRequestURI().equals("/users/current")) getCurrentUser(session);
            logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
        }
    }

    private void getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute(SERVLET_USER_ID);
        try {
            UserFullResponse userProfile = service.getById(userId);
            view.update(userProfile);
        } catch (Exception e) {
            session.invalidate();
            view.update(new LoginResponse(false, FIELD_SEPARATOR));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug(CONTROLLER_METHOD_BEGIN, req.getMethod(), req.getPathInfo());
        initialization(resp);
        HttpSession session = req.getSession();
        if (session.getAttribute(SERVLET_LOGGED) == null || session.getAttribute(SERVLET_USER_ID) == null) {
            session.invalidate();
            view.update(new LoginResponse(false, FIELD_SEPARATOR));
            logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
        } else {
            Long userId = (Long) session.getAttribute(SERVLET_USER_ID);
            try {
                UserRequest request = mapper.getDtoFromRequest(UserRequest.class, req.getReader());
                service.change(userId, request);
                view.update(new LoginResponse(true, "tasks.html"));
                logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
            } catch (Exception e) {
                logger.debug(CONTROLLER_METHOD_END_EXCEPTION, req.getMethod(), req.getPathInfo(), e.getMessage());
                session.invalidate();
                LoginResponse loginResponse = new LoginResponse(false, FIELD_SEPARATOR);
                view.update(loginResponse);
            }
        }
    }
}
