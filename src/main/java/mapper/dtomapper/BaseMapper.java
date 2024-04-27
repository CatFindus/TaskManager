package mapper.dtomapper;

import dto.request.DtoRequest;
import dto.response.FullResponse;
import dto.response.ShortResponse;
import model.BaseEntity;

public interface BaseMapper<E extends BaseEntity, F extends FullResponse, S extends ShortResponse, R extends DtoRequest> {
    E dtoToModel(R request);

    F modelToFullResponse(E entity);

    S modelToListResponse(E entity);
}
