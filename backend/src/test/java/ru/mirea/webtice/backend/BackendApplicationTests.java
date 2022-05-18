package ru.mirea.webtice.backend;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.mirea.webtice.backend.service.QuestionParserService;
import ru.mirea.webtice.backend.service.StyleParserService;
import ru.mirea.webtice.backend.service.TagParserService;
import ru.mirea.webtice.backend.service.UserService;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest()
@AutoConfigureMockMvc
class BackendApplicationTests {

    @MockBean
    private TagParserService tagParserService;

    @MockBean
    private QuestionParserService questionParserService;

    @MockBean
    private UserService userService;

    @MockBean
    private StyleParserService styleParserService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void checkTagService() throws Exception {

        //Проверка на получение Тега по Имени
        this.mvc.perform(MockMvcRequestBuilders.get("/api/tag/name").param("name", "<a>").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Проверка на получение Тега по Имени. (Ошибка 404)
        this.mvc.perform(MockMvcRequestBuilders.get("/api/tag/name").param("name", "<asds>").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse();

        //Проверка на получение Тега по Id.
        this.mvc.perform(MockMvcRequestBuilders.get("/api/tag/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Проверка на получение всех тегов
        this.mvc.perform(MockMvcRequestBuilders.get("/api/tag").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Проверка на получение Тега по Id. (Ошибка 404)
        this.mvc.perform(MockMvcRequestBuilders.get("/api/tag/10000000").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse();
//        this.mvc.perform(MockMvcRequestBuilders.delete("/api/tag/1").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void checkStyleService() throws Exception {

        //Проверка на получение стиля по имени
        this.mvc.perform(MockMvcRequestBuilders.get("/api/style/name").param("name", "background").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Проверка на получение стиля по имени. (Ошибка 404)
        this.mvc.perform(MockMvcRequestBuilders.get("/api/style/name").param("name", "dsdsf").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse();

        //Проверка на получение стиля по id.
        this.mvc.perform(MockMvcRequestBuilders.get("/api/style/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Проверка на получение всех стилей.
        this.mvc.perform(MockMvcRequestBuilders.get("/api/style").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Проверка на получение стиля по id. (Ошибка 404)
        this.mvc.perform(MockMvcRequestBuilders.get("/api/style/10000000").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse();
//        this.mvc.perform(MockMvcRequestBuilders.delete("/api/style/1").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void checkQuestionService() throws Exception {

        //Проверка на получение вопросов по имени.
        this.mvc.perform(MockMvcRequestBuilders.get("/api/question/name").param("name", "Найдите ошибочное определение гиперссылки.").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Проверка на получение вопросов по имени. (Ошибка 404)
        this.mvc.perform(MockMvcRequestBuilders.get("/api/question/name").param("name", "Как дела?").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse();

        //Проверка на получение вопросов по id.
        this.mvc.perform(MockMvcRequestBuilders.get("/api/question/3").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Проверка всех вопросов
        this.mvc.perform(MockMvcRequestBuilders.get("/api/question").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Проверка на получение вопросов по id. (Ошибка 404)
        this.mvc.perform(MockMvcRequestBuilders.get("/api/question/10000000").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse();
//        this.mvc.perform(MockMvcRequestBuilders.delete("/api/question/1").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void checkUserService() throws Exception {
        String username = UUID.randomUUID().toString();
        String email = UUID.randomUUID().toString();

        //Регистрация пользователя
        this.mvc.perform(MockMvcRequestBuilders.post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "  \"username\": \""+username+"\",\n" +
                        "  \"email\": \""+email+"\",\n" +
                        "  \"role\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"password\": \"string\"\n" +
                        "}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Авторизация пользователя, получения Access Token
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"username\": \""+username+"\",\n" +
                                "  \"password\": \"string\"\n" +
                                "}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");
        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
//        System.out.println(token);

        //Получение списка юзеров
        this.mvc.perform(MockMvcRequestBuilders.get("/api/users").header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Получение конкретного юзера
        this.mvc.perform(MockMvcRequestBuilders.get("/api/users/"+id).header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        //Удаление конкретного юзера
        this.mvc.perform(MockMvcRequestBuilders.delete("/api/users/"+id).header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();
    }

}
