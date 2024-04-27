package service;

import dto.request.DtoRequest;
import dto.response.FullResponse;
import dto.response.ShortResponse;
import exeption.NoDataException;
import mapper.dtomapper.BaseMapper;
import mapper.dtomapper.MapperFabric;
import model.BaseEntity;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.BaseRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static constants.ApplicationConstants.*;

public abstract class DefaultService<Q extends DtoRequest, E extends BaseEntity, F extends FullResponse, S extends ShortResponse> extends BaseService{

    private static final Logger logger = LoggerFactory.getLogger(DefaultService.class);
    private final Class<E> entityClass;
    private final BaseRepository<E> repo;
    protected final BaseMapper<E,F,S,Q> mapper;

    public DefaultService(Class<E> entityClass) {
        this.entityClass = entityClass;
        repo = new BaseRepository<>(entityClass);
        mapper = MapperFabric.getMapper(entityClass);
    }

    @Override
    protected void updateSession() {
        super.updateSession();
        repo.setSession(getSession());
    }
    private void checkMapper() throws NoDataException {
        if(mapper==null) {
            logger.error(LOGGER_DEFAULT_SERVICE_NO_MAPPER_EXCEPTION, entityClass.getName());
            throw new NoDataException(String.format(NO_MAPPER_FOUND, entityClass.getName()));
        }
    }

    public F getById(Long id) throws Exception {
        logger.debug(LOGGER_DEFAULT_SERVICE_BEGIN_METHOD, entityClass.getName(), "getById", id);
        checkMapper();
        updateSession();
        Transaction transaction = session.beginTransaction();
        try {
            Optional<E> mayBeEntity = repo.findById(id);
            if (mayBeEntity.isEmpty()) throw new NoDataException(NO_DATA_FOUND);
                else {
                E entity = mayBeEntity.get();
                F response = mapper.modelToFullResponse(entity);
                transaction.commit();
                logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD, entityClass.getName(), "getById", id);
                return response;
            }
        } catch (Exception e) {
            transaction.rollback();
            logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD_EXCEPTION, entityClass.getName(), "getById", e.getMessage());
            throw new Exception(e);
        }
    }

    public List<S> getAll(int limit , int offset) throws Exception {
        logger.debug(LOGGER_DEFAULT_SERVICE_BEGIN_METHOD, entityClass.getName(), "getAll", null);
        checkMapper();
        updateSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<E> entities = repo.findAll(limit, offset);
            List<S> responses = mapToShortResponseList(entities);
            transaction.commit();
            logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD, entityClass.getName(), "getAll", null);
            return responses;
        } catch (Exception e) {
            transaction.rollback();
            logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD_EXCEPTION, entityClass.getName(), "getAll", e.getMessage());
            throw new Exception(e);
        }
    }

    public F write(Q request) throws Exception {
        logger.debug(LOGGER_DEFAULT_SERVICE_BEGIN_METHOD, entityClass.getName(), "save", null);
        checkMapper();
        updateSession();
        Transaction transaction = session.beginTransaction();
        try {
            E entity = mapper.dtoToModel(request);
            entity = repo.save(entity);
            session.refresh(entity);
            F response = mapper.modelToFullResponse(entity);
            transaction.commit();
            logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD, entityClass.getName(), "save", null);
            return response;
        } catch (Exception e) {
            transaction.rollback();
            logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD_EXCEPTION, entityClass.getName(), "save", e.getMessage());
            throw new Exception(e);
        }
    }
    @SuppressWarnings(value = "unused")
    public boolean delete(Long id) throws Exception {
        logger.debug(LOGGER_DEFAULT_SERVICE_BEGIN_METHOD, entityClass.getName(), "delete", null);
        checkMapper();
        updateSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean deleted = repo.delete(id);
            logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD, entityClass.getName(), "delete", null);
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            transaction.rollback();
            logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD_EXCEPTION, entityClass.getName(), "delete", e.getMessage());
            throw new Exception(e);
        }
    }

    public Long getCount() throws Exception {
        logger.debug(LOGGER_DEFAULT_SERVICE_BEGIN_METHOD, entityClass.getName(), "count", null);
        checkMapper();
        updateSession();
        Transaction transaction = session.beginTransaction();
        try {
            Long count = repo.count();
            logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD, entityClass.getName(), "count", null);
            transaction.commit();
            return count;
        } catch (Exception e) {
            transaction.rollback();
            logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD_EXCEPTION, entityClass.getName(), "count", e.getMessage());
            throw new Exception(e);
        }
    }

    public F change(Long id, Q request) throws Exception {
        logger.debug(LOGGER_DEFAULT_SERVICE_BEGIN_METHOD, entityClass.getName(), "update", id);
        checkMapper();
        updateSession();
        Transaction transaction = session.beginTransaction();
        try {
            Optional<E> mayBeEntity = repo.findById(id);
            if (mayBeEntity.isEmpty()) throw new NoDataException(NO_DATA_FOUND);
            else {
                E entity = mayBeEntity.get();
                E newFieldsEntity = mapper.dtoToModel(request);
                Field[] fields = entityClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(newFieldsEntity);
                    if(value!=null) {
                        field.set(entity, value);
                    }
                }
                entity = repo.update(entity);
                F response = mapper.modelToFullResponse(entity);
                transaction.commit();
                logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD, entityClass.getName(), "update", null);
                return response;
            }
        } catch (Exception e) {
            transaction.rollback();
            logger.debug(LOGGER_DEFAULT_SERVICE_END_METHOD_EXCEPTION, entityClass.getName(), "update", e.getMessage());
            throw new Exception(e);
        }
    }

    protected List<S> mapToShortResponseList(List<E> entities) {
        List<S> responses = new ArrayList<>();
        for (E entity : entities) {
            responses.add(mapper.modelToListResponse(entity));
        }
        return responses;
    }

}
