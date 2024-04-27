package service;

import dto.request.UserRequest;
import dto.response.UserDefaultResponse;
import dto.response.UserFullResponse;
import exeption.NoDataException;
import model.User;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static constants.ApplicationConstants.*;

public class UserService extends DefaultService<UserRequest, User, UserFullResponse, UserDefaultResponse> {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService() {
        super(User.class);
    }

    public UserFullResponse getUserByLoginAndPass(UserRequest request) throws NoDataException {
        logger.debug(LOGGER_USER_SERVICE_GET_USER_BY_LOGIN_BEGIN, request.getUserName());
        updateSession();
        Transaction transaction = session.beginTransaction();
        Optional<User> mayBeUser = userRepo.findByLoginAndPass(request.getUserName(), request.getPasswordHash());
        if (mayBeUser.isPresent()) {
            UserFullResponse response = mapper.modelToFullResponse(mayBeUser.get());
            transaction.commit();
            logger.debug(LOGGER_USER_SERVICE_GET_USER_BY_LOGIN_END, request.getUserName());
            return response;
        } else {
            transaction.rollback();
            logger.debug(LOGGER_USER_SERVICE_GET_USER_BY_LOGIN_END_EXCEPTION, NO_DATA_FOUND, request.getUserName());
            throw new NoDataException(NO_DATA_FOUND);
        }
    }

}


