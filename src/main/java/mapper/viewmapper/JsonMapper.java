package mapper.viewmapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.response.DtoResponse;
import dto.request.DtoRequest;
import dto.response.ErrorResponse;
import exeption.IncorrectRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static constants.ApplicationConstants.*;


public class JsonMapper implements ViewMapper {
    private final static Logger logger = LoggerFactory.getLogger(JsonMapper.class);
    private final ObjectMapper mapper;

    public JsonMapper() {
        mapper = new ObjectMapper();
    }

    @Override
    public <T extends DtoRequest> T getDtoFromRequest(Class<T> clazz, BufferedReader reader) throws IncorrectRequestException {
        T result;
        try (BufferedReader br = reader) {
            StringBuilder json = new StringBuilder();
            String part;
            while ((part = br.readLine()) != null) {
                json.append(part);
            }
            result = mapper.readValue(json.toString(), clazz);
        } catch (IOException e) {
            throw new IncorrectRequestException(INCORRECT_BODY_OF_REQUEST);
        }
        return result;
    }

    public String getStringFromResponse(List<? extends DtoResponse> response) {
        String result;
        try {
            result = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            ErrorResponse errResp = new ErrorResponse(ERROR_JSON_STRING);
            logger.error(LOGGER_ERROR_JSON_STRING, errResp.getErrorID());
            return List.of(errResp).toString();
        }
        return result;
    }

    public String getStringFromResponse(DtoResponse response) {
        String result;
        try {
            result = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            ErrorResponse errResp = new ErrorResponse(ERROR_JSON_STRING);
            logger.error(LOGGER_ERROR_JSON_STRING, errResp.getErrorID());
            return List.of(errResp).toString();
        }
        return result;
    }
}
