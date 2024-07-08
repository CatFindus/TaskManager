package service;

import dto.request.TaskRequest;
import dto.response.CountResponse;
import dto.response.TaskForListResponse;
import dto.response.TaskFullResponse;
import exeption.IncorrectRequestException;
import mapper.dtomapper.TaskMapper;
import mapper.dtomapper.TaskMapperImpl;
import model.Tag;
import model.Task;
import model.User;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static constants.ApplicationConstants.*;

public class TaskService extends DefaultService<TaskRequest, Task, TaskFullResponse, TaskForListResponse> {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskMapper mapper;

    public TaskService() {
        super(Task.class);
        mapper = new TaskMapperImpl();
    }

    @SuppressWarnings(value = "unused")
    public List<TaskForListResponse> getAllTasksForUser(Long userId, int limit, int offset) {
        logger.debug(LOGGER_TASK_SERVICE_GET_ALL_BEGIN, userId);
        updateSession();
        Transaction transaction = session.beginTransaction();
        List<Task> tasks = taskRepo.findTasksForUser(userId, limit, offset);
        List<TaskForListResponse> responses = mapToShortResponseList(tasks);
        transaction.commit();
        logger.debug(LOGGER_TASK_SERVICE_GET_ALL_END, responses.size());
        return responses;
    }

    @SuppressWarnings(value = "unused")
    public Long getTaskCountByUserId(Long userId) {
        logger.debug(LOGGER_TASK_SERVICE_GET_COUNT_BEGIN, userId);
        Transaction transaction = session.beginTransaction();
        Long taskCount = taskRepo.getTaskCountForUserId(userId);
        transaction.commit();
        logger.debug(LOGGER_TASK_SERVICE_GET_COUNT_END, userId, taskCount);
        return taskCount;
    }

    @Override
    public TaskFullResponse write(TaskRequest request) {
        logger.debug(LOGGER_TASK_SERVICE_SAVE_BEGIN, request);
        updateSession();
        Transaction transaction = session.beginTransaction();
        try {
            Optional<User> user = userRepo.findById(request.getUserId());
            Optional<User> createdBy = userRepo.findById(request.getCreatedById());
            if (user.isEmpty() || createdBy.isEmpty() || request.getTagId() == null) {
                logger.debug(LOGGER_TASK_SERVICE_SAVE_END_EXCEPTION, INCORRECT_BODY_OF_REQUEST);
                transaction.rollback();
                throw new IncorrectRequestException(INCORRECT_BODY_OF_REQUEST);
            }
            Optional<Tag> mayBeTag = tagRepo.findById(request.getTagId());
            if (mayBeTag.isEmpty()) throw new IncorrectRequestException(INCORRECT_BODY_OF_REQUEST);
            Task task = mapper.dtoToModel(request, user.get(), createdBy.get(), createdBy.get());
            task.setTag(mayBeTag.get());
            task = taskRepo.save(task);
            TaskFullResponse response = mapper.modelToFullResponse(task);
            transaction.commit();
            logger.debug(LOGGER_TASK_SERVICE_SAVE_END, task.getId());
            return response;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public CountResponse getTaskCountForUserByParams(Long currentUserId, Map<String, Object> parameterMap) {
        logger.debug(LOGGER_TASK_SERVICE_GET_COUNT_BEGIN, currentUserId);
        updateSession();
        Transaction transaction = session.beginTransaction();
        Long count = taskRepo.findTaskCountForUserByParams(currentUserId, parameterMap);
        transaction.commit();
        logger.debug(LOGGER_TASK_SERVICE_GET_COUNT_END, currentUserId, count);
        return new CountResponse(count);
    }

    public List<TaskForListResponse> getTasksByParametersForUser(Long currentUserId, Map<String, Object> parameterMap) {
        logger.debug(LOGGER_TASK_SERVICE_GET_ALL_BY_PARAMS_BEGIN, currentUserId);
        updateSession();
        Transaction transaction = session.beginTransaction();
        List<Task> tasks = taskRepo.findTasksForUserByParams(currentUserId, parameterMap);
        List<TaskForListResponse> responses = mapToShortResponseList(tasks);
        transaction.commit();
        logger.debug(LOGGER_TASK_SERVICE_GET_ALL_END, responses.size());
        return responses;
    }
}

