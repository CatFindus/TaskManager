package view;

import dto.response.DtoResponse;
import jakarta.servlet.http.HttpServletResponse;
import mapper.viewmapper.JsonMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class JsonView implements View {
    private final HttpServletResponse response;
    private final JsonMapper mapper;

    public JsonView(HttpServletResponse response) {
        this.response = response;
        mapper = new JsonMapper();
    }

    @Override
    public void update(List<? extends DtoResponse> units) {
        String responseBody = mapper.getStringFromResponse(units);
        System.out.println(responseBody);
        response.setContentType("application/json");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(responseBody);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(DtoResponse unit) {
        String responseBody = mapper.getStringFromResponse(unit);
        System.out.println(responseBody);
        response.setContentType("application/json");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(responseBody);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
