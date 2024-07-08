package service;

import dto.request.CommentRequest;
import dto.response.CommentFullResponse;
import exeption.IncorrectRequestException;
import model.Comment;
import model.Task;
import model.User;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static constants.ApplicationConstants.*;

public class CommentService extends DefaultService<CommentRequest, Comment, CommentFullResponse, CommentFullResponse> {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    public CommentService() {
        super(Comment.class);
    }

    public List<CommentFullResponse> getAllForTask(Long taskId) {
        logger.debug(LOGGER_COMMENT_SERVICE_GET_ALL_FOR_TASK_BEGIN, taskId);
        updateSession();
        Transaction transaction = session.beginTransaction();
        List<Comment> comments = commentRepo.findAllForTask(taskId);
        List<CommentFullResponse> responses = mapToShortResponseList(comments);
        transaction.commit();
        logger.debug(LOGGER_COMMENT_SERVICE_GET_ALL_FOR_TASK_END, responses.size());
        return responses;
    }

    @SuppressWarnings(value = "unused")
    public List<CommentFullResponse> getAllForUser(Long userId) {
        logger.debug(LOGGER_COMMENT_SERVICE_GET_ALL_FOR_USER_BEGIN, userId);
        updateSession();
        Transaction transaction = session.beginTransaction();
        List<Comment> comments = commentRepo.findAllForUser(userId);
        List<CommentFullResponse> responses = mapToShortResponseList(comments);
        transaction.commit();
        logger.debug(LOGGER_COMMENT_SERVICE_GET_ALL_FOR_USER_END, responses.size());
        return responses;
    }

    @Override
    public CommentFullResponse write(CommentRequest request) throws IncorrectRequestException {
        logger.debug(LOGGER_COMMENT_SERVICE_SAVE_BEGIN, request);
        updateSession();
        Transaction transaction = session.beginTransaction();
        Comment comment = mapper.dtoToModel(request);
        if (request.getUserId() == null || request.getTaskId() == null) {
            transaction.rollback();
            throw new IncorrectRequestException(INCORRECT_BODY_OF_REQUEST);
        }
        Optional<User> mayBeUser = userRepo.findById(request.getUserId());
        Optional<Task> mayBeTask = taskRepo.findById(request.getTaskId());
        if (mayBeUser.isEmpty() || mayBeTask.isEmpty()) {
            transaction.rollback();
            throw new IncorrectRequestException(INCORRECT_BODY_OF_REQUEST);
        }
        comment.setUser(mayBeUser.get());
        comment.setTask(mayBeTask.get());
        comment = commentRepo.save(comment);
        transaction.commit();
        CommentFullResponse response = mapper.modelToFullResponse(comment);
        response.setUpdatedAt(LocalDateTime.now());
        logger.debug(LOGGER_COMMENT_SERVICE_SAVE_END, response);
        return response;
    }
}
