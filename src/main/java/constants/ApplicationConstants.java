package constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationConstants {

    //Exceptions
    public static final String INCORRECT_BODY_OF_REQUEST = "Incorrect body of request";
    public static final String ERROR_JSON_STRING = "Error occurred while creating JSON string";
    public static final String NO_DATA_FOUND = "No data found for received request";
    public static final String NO_MAPPER_FOUND = "No mapper found for class: %s";

    //Logger
    public static final String LOGGER_WARN_MSG = "UUID:{}, message:{}";
    public static final String LOGGER_ERROR_JSON_STRING = "Error occurred while creating JSON string. Error Id: {}";
    public static final String LOGGER_MAPPING_TO_RESP_BEGIN = "Mapping to Response begin";
    public static final String LOGGER_MAPPING_TO_RESP_END = "Mapping to Response end successfully";
    public static final String LOGGER_USER_SERVICE_SAVE_BEGIN = "Begin user save method with request {}";
    public static final String LOGGER_USER_SERVICE_SAVE_FINISH = "Save method finished successfully id: {}";
    public static final String LOGGER_USER_SERVICE_SAVE_FINISH_EXCEPTION = "Save method finished with exception: {}";
    public static final String LOGGER_USER_SERVICE_GET_PROFILE_BEGIN = "Begin user get method with request id: {}";
    public static final String LOGGER_USER_SERVICE_GET_PROFILE_END = "End user get method with request id: {}";
    public static final String LOGGER_USER_SERVICE_GET_PROFILE_END_EXCEPTION = "End user get method finished with exception: {}, Id:{}";
    public static final String LOGGER_USER_SERVICE_GET_USER_BY_LOGIN_BEGIN = "Begin user getByLogin method with request login: {}";
    public static final String LOGGER_USER_SERVICE_GET_USER_BY_LOGIN_END = "End user getByLogin method with request login: {}";
    public static final String LOGGER_USER_SERVICE_GET_USER_BY_LOGIN_END_EXCEPTION = "End user getByLogin method finished with exception: {}, login:{}";
    public static final String LOGGER_USER_SERVICE_UPDATE_PROFILE_BEGIN = "Begin user update method with request id: {}";
    public static final String LOGGER_USER_SERVICE_UPDATE_PROFILE_END = "End user update method with request id: {}";
    public static final String LOGGER_USER_SERVICE_UPDATE_PROFILE_END_EXCEPTION = "End user update method finished with exception: {}, Id:{}";
    public static final String LOGGER_TASK_SERVICE_GET_ALL_BEGIN = "Begin get all tasks method with request userId:{}";
    public static final String LOGGER_TASK_SERVICE_GET_ALL_END = "Begin get all tasks method with request results:{}";
    public static final String LOGGER_TASK_SERVICE_GET_BY_ID_BEGIN = "Begin task get method with request id: {}";
    public static final String LOGGER_TASK_SERVICE_GET_BY_ID_END = "End task get method successfully id: {}";
    public static final String LOGGER_TASK_SERVICE_GET_BY_ID_END_EXCEPTION = "End task get method with exception: {}";
    public static final String LOGGER_TASK_SERVICE_GET_COUNT_BEGIN = "Begin get task count method userId: {}";
    public static final String LOGGER_TASK_SERVICE_GET_COUNT_END = "Begin get task count method userId: {}, count: {}";
    public static final String LOGGER_TASK_SERVICE_UPDATE_BEGIN = "Begin task update method with id {}";
    public static final String LOGGER_TASK_SERVICE_UPDATE_END = "End task update method successfully with id {}";
    public static final String LOGGER_TASK_SERVICE_UPDATE_END_EXCEPTION = "End task update method successfully with exception {}";
    public static final String LOGGER_TASK_SERVICE_SAVE_BEGIN = "Begin task save method with request {}";
    public static final String LOGGER_TASK_SERVICE_SAVE_END = "End task save method with response {}";
    public static final String LOGGER_TASK_SERVICE_SAVE_END_EXCEPTION = "End task save method with exception {}";
    public static final String LOGGER_TASK_SERVICE_DELETE_BEGIN = "Begin task delete method with request id: {}";
    public static final String LOGGER_TASK_SERVICE_DELETE_END = "End task delete method with request id: {} and status :{}";
    public static final String LOGGER_TASK_SERVICE_GET_ALL_BY_PARAMS_BEGIN = "Begin get all tasks by parameters method with request userId:{}";
    public static final String LOGGER_TASK_SERVICE_GET_ALL_BY_PARAMS_END = "Begin get all tasks by parameters method with request results size:{}";
    public static final String LOGGER_DEFAULT_SERVICE_BEGIN_METHOD = "Begin {} {} method with request {}";
    public static final String LOGGER_DEFAULT_SERVICE_END_METHOD = "End {} {} method with request {}";
    public static final String LOGGER_DEFAULT_SERVICE_END_METHOD_EXCEPTION = "\"End {} method method with request {} and exception {}";
    public static final String LOGGER_DEFAULT_SERVICE_NO_MAPPER_EXCEPTION = "No mapper installed for {}";

    public static final String LOGGER_TAG_SERVICE_SAVE_BEGIN = "Begin tag save method with request {}";
    public static final String LOGGER_TAG_SERVICE_SAVE_END = "End tag save method with response {}";
    public static final String LOGGER_TAG_SERVICE_UPDATE_BEGIN = "Begin tag update method with id {}";
    public static final String LOGGER_TAG_SERVICE_UPDATE_END = "End tag update method successfully with id {}";
    public static final String LOGGER_TAG_SERVICE_UPDATE_END_EXCEPTION = "End task update method successfully with exception {}";
    public static final String LOGGER_TAG_SERVICE_DELETE_BEGIN = "Begin tag delete method with request id: {}";
    public static final String LOGGER_TAG_SERVICE_DELETE_END = "End tag delete method with request id: {} and status :{}";
    public static final String LOGGER_TAG_SERVICE_GET_BY_ID_BEGIN = "Begin tag get method with request id: {}";
    public static final String LOGGER_TAG_SERVICE_GET_BY_ID_END = "End tag get method successfully id: {}";
    public static final String LOGGER_TAG_SERVICE_GET_BY_ID_END_EXCEPTION = "End tag get method with exception: {}";
    public static final String LOGGER_TAG_SERVICE_GET_ALL_BEGIN = "Begin get all tags method";
    public static final String LOGGER_TAG_SERVICE_GET_ALL_END = "Begin get all tags method with request results:{}";
    public static final String LOGGER_TAG_SERVICE_COUNT_BEGIN = "Begin count all tags method";;
    public static final String LOGGER_TAG_SERVICE_COUNT_END = "Begin count all tags method with result: {}";;
    public static final String LOGGER_TAG_SERVICE_GET_ALL_FOR_TASK_BEGIN = "Begin get all tags for task method with request id: {}";
    public static final String LOGGER_TAG_SERVICE_GET_ALL_FOR_TASK_END = "Begin get all tags for task method with request results:{}";
    public static final String LOGGER_COMMENT_SERVICE_GET_BY_ID_BEGIN = "Begin comment get method with request id: {}";
    public static final String LOGGER_COMMENT_SERVICE_GET_BY_ID_END = "End comment get method successfully id: {}";
    public static final String LOGGER_COMMENT_SERVICE_GET_BY_ID_END_EXCEPTION = "End comment get method with exception: {}";
    public static final String LOGGER_COMMENT_SERVICE_GET_ALL_FOR_TASK_BEGIN = "Begin get all comments for task method with request id: {}";
    public static final String LOGGER_COMMENT_SERVICE_GET_ALL_FOR_TASK_END = "Begin get all comments for task method with request results:{}";
    public static final String LOGGER_COMMENT_SERVICE_GET_ALL_FOR_USER_BEGIN = "Begin get all comments for user method with request id: {}";
    public static final String LOGGER_COMMENT_SERVICE_GET_ALL_FOR_USER_END = "Begin get all comments for user method with request results:{}";
    public static final String LOGGER_COMMENT_SERVICE_SAVE_BEGIN = "Begin comment save method with request {}";
    public static final String LOGGER_COMMENT_SERVICE_SAVE_END = "End comment save method with response {}";
    public static final String LOGGER_COMMENT_SERVICE_UPDATE_BEGIN = "Begin comment update method with id {}";
    public static final String LOGGER_COMMENT_SERVICE_UPDATE_END = "End comment update method successfully with id {}";
    public static final String LOGGER_COMMENT_SERVICE_UPDATE_END_EXCEPTION = "End comment update method successfully with exception {}";
    public static final String LOGGER_COMMENT_SERVICE_DELETE_BEGIN = "Begin comment delete method with request id: {}";
    public static final String LOGGER_COMMENT_SERVICE_DELETE_END = "End comment delete method with request id: {} and status :{}";
    public static final String CONTROLLER_METHOD_BEGIN = "Begin controller {} method with path: {}";
    public static final String CONTROLLER_METHOD_END = "End controller {} method with path: {}";
    public static final String CONTROLLER_METHOD_END_EXCEPTION = "End controller {} method with path{} with exception: {}";
    //Controller const
    public static final String SERVLET_USER_ID = "userId";
    public static final String SERVLET_LOGGED = "logged";
    //Fields const
    public static final String FIELD_ID="id";
    public static final String FIELD_STATUS="status";
    public static final String FIELD_COMMENT="comment";
    public static final String FIELD_COMMENT_ID="commentId";
    public static final String FIELD_TITLE="title";
    public static final String FIELD_CONTENT="content";
    public static final String FIELD_PLANNED_START="plannedStart";
    public static final String FIELD_PLANNED_END="plannedEnd";
    public static final String FIELD_TAG_ID="tagId";
    public static final String FIELD_OFFSET="offset";
    public static final String FIELD_LIMIT="limit";
    public static final String FIELD_TAG="tag";
    public static final String FIELD_USER = "user";
    public static final String FIELD_SEPARATOR = "/";
    public static final String FIELD_EMPTY = "";


}
