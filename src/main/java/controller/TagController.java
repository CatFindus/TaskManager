package controller;

import dto.request.TagRequest;
import dto.response.LoginResponse;
import dto.response.TagResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mapper.viewmapper.JsonMapper;
import mapper.viewmapper.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.TagService;
import view.JsonView;
import view.View;

import java.util.List;

import static constants.ApplicationConstants.*;

@WebServlet(name = "tagServlet", urlPatterns = "/tag/*")
public class TagController extends HttpServlet {
    private TagService service;
    private View view;
    private ViewMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    private void initialization(HttpServletResponse response) {
        service = new TagService();
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
                TagRequest request = mapper.getDtoFromRequest(TagRequest.class, req.getReader());
                TagResponse response = service.write(request);
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
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
                Long tagId = Long.valueOf(req.getPathInfo().replaceFirst(FIELD_SEPARATOR, FIELD_EMPTY));
                TagRequest request = mapper.getDtoFromRequest(TagRequest.class, req.getReader());
                TagResponse update = service.change(tagId, request);
                view.update(update);
                logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
            } catch (Exception e) {
                session.invalidate();
                LoginResponse loginResponse = new LoginResponse(false, FIELD_SEPARATOR);
                view.update(loginResponse);
                logger.debug(CONTROLLER_METHOD_END_EXCEPTION, req.getMethod(), req.getPathInfo(), e.getMessage());
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
            if (req.getPathInfo() == null) {
                try {
                    Long count = service.getCount();
                    List<TagResponse> responses = service.getAll(Math.toIntExact(count), 0);
                    view.update(responses);
                    logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
                } catch (Exception e) {
                    session.invalidate();
                    LoginResponse loginResponse = new LoginResponse(false, FIELD_SEPARATOR);
                    view.update(loginResponse);
                    logger.debug(CONTROLLER_METHOD_END_EXCEPTION, req.getMethod(), req.getPathInfo(), e.getMessage());
                }
            }
        }
    }
}
