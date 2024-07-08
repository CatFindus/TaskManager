package controller;

import dto.request.UserRequest;
import dto.response.LoginResponse;
import dto.response.UserFullResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import mapper.viewmapper.JsonMapper;
import mapper.viewmapper.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import view.JsonView;
import view.View;

import static constants.ApplicationConstants.*;

@WebServlet(name = "loginServlet", urlPatterns = "/login/*")
public class LoginController extends HttpServlet {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug(CONTROLLER_METHOD_BEGIN, req.getMethod(), req.getPathInfo());
        initialization(resp);
        HttpSession session = req.getSession();
        try {
            UserRequest request = mapper.getDtoFromRequest(UserRequest.class, req.getReader());
            UserFullResponse response = service.getUserByLoginAndPass(request);
            session.setAttribute(SERVLET_USER_ID, response.getId());
            session.setAttribute(SERVLET_LOGGED, true);
            resp.addCookie(new Cookie(SERVLET_USER_ID, response.getId().toString()));
            LoginResponse loginResponse = new LoginResponse(true, "pages/tasks.html");
            view.update(loginResponse);
            logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
        } catch (Exception e) {
            logger.debug(CONTROLLER_METHOD_END_EXCEPTION, req.getMethod(), req.getPathInfo(), e.getMessage());
            session.invalidate();
            LoginResponse loginResponse = new LoginResponse(false, "/");
            view.update(loginResponse);
        }
    }
}
