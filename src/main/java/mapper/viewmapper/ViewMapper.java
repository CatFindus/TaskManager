package mapper.viewmapper;


import dto.request.DtoRequest;
import dto.response.DtoResponse;
import exeption.IncorrectRequestException;

import java.io.BufferedReader;
import java.util.List;

public interface ViewMapper {
    <T extends DtoRequest> T getDtoFromRequest(Class<T> clazz, BufferedReader reader) throws IncorrectRequestException;

    String getStringFromResponse(List<? extends DtoResponse> response);
}
