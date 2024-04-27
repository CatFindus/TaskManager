package view;


import dto.response.DtoResponse;

import java.util.List;

public interface View {
    void update(List<? extends DtoResponse> units);

    void update(DtoResponse unit);
}
