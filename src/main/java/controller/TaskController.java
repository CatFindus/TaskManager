package controller;

import dto.request.TaskRequest;
import dto.response.CountResponse;
import dto.response.LoginResponse;
import dto.response.TaskForListResponse;
import dto.response.TaskFullResponse;
import enums.TaskStatus;
import exeption.IncorrectRequestException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import mapper.viewmapper.JsonMapper;
import mapper.viewmapper.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.TaskService;
import view.JsonView;
import view.View;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.ApplicationConstants.*;

@WebServlet(name = "taskServlet", urlPatterns = "/tasks/*")
public class TaskController extends HttpServlet {
    private TaskService service;
    private View view;
    private ViewMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private void initialization(HttpServletResponse response) {
        service = new TaskService();
        view = new JsonView(response);
        mapper = new JsonMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug(CONTROLLER_METHOD_BEGIN, req.getMethod(), req.getPathInfo());
        initialization(resp);
        HttpSession session = req.getSession();
        Boolean logged = (Boolean) (session.getAttribute(SERVLET_LOGGED));
        if (session.getAttribute(SERVLET_USER_ID) == null || logged == null || !logged.booleanValue()) {
            session.invalidate();
            LoginResponse loginResponse = new LoginResponse(false, FIELD_SEPARATOR);
            view.update(loginResponse);
            logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
        } else {
            try {
                Long currentUserId = (Long) session.getAttribute(SERVLET_USER_ID);
                resp.addCookie(new Cookie(SERVLET_USER_ID, currentUserId.toString()));
                if (req.getRequestURI().equals("/tasks/current"))
                    sendTaskListForCurrent(currentUserId, req.getParameterMap());
                else if (req.getRequestURI().equals("/tasks/current/count")) {
                    sendCountForCurrent(currentUserId, req.getParameterMap());
                } else if (req.getPathInfo() != null) {
                    Long taskId = Long.valueOf(req.getPathInfo().replace(FIELD_SEPARATOR, FIELD_EMPTY));
                    TaskFullResponse taskById = service.getById(taskId);
                    view.update(taskById);
                }

            } catch (Exception e) {
                session.invalidate();
                LoginResponse loginResponse = new LoginResponse(false, FIELD_SEPARATOR);
                view.update(loginResponse);
            }
        }

    }

    private void sendCountForCurrent(Long currentUserId, Map<String, String[]> query) throws IncorrectRequestException {
        Map<String, Object> parameterMap = getMapFromQuery(query);
        CountResponse response = service.getTaskCountForUserByParams(currentUserId, parameterMap);
        view.update(response);
    }

    private Map<String, Object> getMapFromQuery(Map<String, String[]> query) throws IncorrectRequestException {
        Map<String, Object> result = new HashMap<>();
        try {
            for (String key : query.keySet()) {
                String value = query.get(key)[0];
                if (value != null && !value.isEmpty()) {
                    switch (key) {
                        case FIELD_ID -> result.put(key, Long.valueOf(value));
                        case FIELD_TITLE -> result.put(key, value);
                        case FIELD_STATUS -> result.put(key, TaskStatus.valueOf(value));
                        case FIELD_PLANNED_START, FIELD_PLANNED_END -> result.put(key, LocalDateTime.parse(value));
                        case FIELD_TAG -> result.put(FIELD_TAG, value);
                        case FIELD_LIMIT, FIELD_OFFSET -> result.put(key, Integer.parseInt(value));
                    }
                }
            }
        } catch (Exception e) {
            throw new IncorrectRequestException(INCORRECT_BODY_OF_REQUEST);
        }
        return result;
    }

    private void sendTaskListForCurrent(Long currentUserId, Map<String, String[]> query) throws IncorrectRequestException {
        Map<String, Object> parameterMap = getMapFromQuery(query);
        List<TaskForListResponse> responses = service.getTasksByParametersForUser(currentUserId, parameterMap);
        view.update(responses);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug(CONTROLLER_METHOD_BEGIN, req.getMethod(), req.getPathInfo());
        initialization(resp);
        HttpSession session = req.getSession();
        Boolean logged = (Boolean) (session.getAttribute(SERVLET_LOGGED));
        if (session.getAttribute(SERVLET_USER_ID) == null || logged == null || !logged.booleanValue()) {
            session.invalidate();
            LoginResponse loginResponse = new LoginResponse(false, FIELD_SEPARATOR);
            view.update(loginResponse);
            logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
        } else {
            try {
                Long currentUserId = (Long) session.getAttribute(SERVLET_USER_ID);
                TaskRequest request = mapper.getDtoFromRequest(TaskRequest.class, req.getReader());
                request.setUserId(currentUserId);
                request.setCreatedById(currentUserId);
                TaskFullResponse task = service.write(request);
                view.update(task);
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
        if (session.getAttribute(SERVLET_USER_ID) == null || logged == null || !logged.booleanValue()) {
            session.invalidate();
            LoginResponse loginResponse = new LoginResponse(false, FIELD_SEPARATOR);
            view.update(loginResponse);
            logger.debug(CONTROLLER_METHOD_END, req.getMethod(), req.getPathInfo());
        } else {
            try {
                Long taskId = Long.valueOf(req.getPathInfo().replaceFirst(FIELD_SEPARATOR, FIELD_EMPTY));
                TaskRequest request = mapper.getDtoFromRequest(TaskRequest.class, req.getReader());
                TaskFullResponse response = service.change(taskId, request);
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
}
