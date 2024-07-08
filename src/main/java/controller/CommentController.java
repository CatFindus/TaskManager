package controller;

import dto.request.CommentRequest;
import dto.response.CommentFullResponse;
import dto.response.LoginResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mapper.viewmapper.JsonMapper;
import mapper.viewmapper.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CommentService;
import view.JsonView;
import view.View;

import java.util.List;

import static constants.ApplicationConstants.*;

@WebServlet(name = "commentServlet", urlPatterns = "/comments/*")
public class CommentController extends HttpServlet {
    private CommentService service;
    private View view;
    private ViewMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private void initialization(HttpServletResponse response) {
        service = new CommentService();
        view = new JsonView(response);
        mapper = new JsonMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug(CONTROLLER_METHOD_BEGIN, req.getMethod(), req.getPathInfo());
        initialization(resp);
        HttpSession session = req.getSession();
        Boolean logged = (Boolean) (session.getAttribute(SERVLET_LOGGED));
        if (session.getAttribute(SERVLET_USER_ID) == null ||
                logged == null || !logged.booleanValue()) {
            session.invalidate();
            LoginResponse loginResponse = new LoginResponse(false, FIELD_SEPARATOR);
            view.update(loginResponse);
            logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
        } else {
            try {
                CommentRequest request = mapper.getDtoFromRequest(CommentRequest.class, req.getReader());
                CommentFullResponse response = service.write(request);
                view.update(response);
                logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
            } catch (Exception e) {
                logger.debug(CONTROLLER_METHOD_END_EXCEPTION, req.getMethod(), req.getPathInfo(), e.getMessage());
                session.invalidate();
                LoginResponse loginResponse = new LoginResponse(false, FIELD_SEPARATOR);
                view.update(loginResponse);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug(CONTROLLER_METHOD_BEGIN, req.getMethod(), req.getPathInfo());
        initialization(resp);
        HttpSession session = req.getSession();
        Boolean logged = (Boolean) (session.getAttribute(SERVLET_LOGGED));
        if (session.getAttribute(SERVLET_USER_ID) == null ||
                logged == null || !logged.booleanValue()) {
            session.invalidate();
            LoginResponse loginResponse = new LoginResponse(false, FIELD_SEPARATOR);
            view.update(loginResponse);
            logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
        } else {
            Long taskId = Long.valueOf(req.getPathInfo().replaceFirst(FIELD_SEPARATOR, FIELD_EMPTY));
            List<CommentFullResponse> allForTask = service.getAllForTask(taskId);
            view.update(allForTask);
            logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
        }
    }
}
