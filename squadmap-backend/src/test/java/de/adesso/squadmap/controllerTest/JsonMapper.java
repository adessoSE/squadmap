package de.adesso.squadmap.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

class JsonMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    static String asJsonString(final Object obj) {
        try {
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static <T> T asResponse(MvcResult result, Class<T> tClass) {
        try {
            mapper.registerModule(new JavaTimeModule());
            String content = result.getResponse().getContentAsString();
            return mapper.readValue(content, tClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static <T> List<T> asResponseList(MvcResult result, Class<T> tClass) {
        try {
            mapper.registerModule(new JavaTimeModule());
            String content = result.getResponse().getContentAsString();
            return mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(List.class, tClass));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
