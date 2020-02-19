package com.holubinka;

import com.holubinka.controller.model.UserExt;
import com.holubinka.model.User;
import com.holubinka.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootDemoApplicationTests {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @MockBean
    private UserService userService;

    private List<User> users;
    private User userExt;
    private MockMvc mockMvc;

    @Before
    public void setUser() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("email1");
        user1.setFirstName("name1");
        user1.setPassword(encoder.encode("123123"));
        user1.setAge(6);

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("email2");
        user2.setFirstName("name2");
        user2.setPassword(encoder.encode("123123"));
        user2.setAge(18);

        User user3 = new User();
        user3.setId(3L);
        user3.setEmail("email4");
        user3.setFirstName("name4");
        user3.setPassword(encoder.encode("123123"));
        user3.setAge(20);

        users.add(user1);
        users.add(user2);
        users.add(user3);

        userExt = new User();
        userExt.setId(5L);
        userExt.setAge(16);
        userExt.setEmail("email10");
        userExt.setPassword(encoder.encode("123123"));
        userExt.setFirstName("name10");
    }

    @Test
    public void findAll() throws Exception {
        Mockito.when(userService.getAll()).thenReturn(Optional.ofNullable(users));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .with(user("email1").password("123123"))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String expected = "[" +
                "{" +
                "\"id\":1," +
                "\"firstName\":\"name1\"," +
                "\"age\":6," +
                "\"email\":\"email1\"," +
                "\"article\":[]" +
                "}," +
                "{" +
                "\"id\":2," +
                "\"firstName\":\"name2\"," +
                "\"age\":18," +
                "\"email\":\"email2\"," +
                "\"article\":[]" +
                "}," +
                "{" +
                "\"id\":3," +
                "\"firstName\":\"name4\"," +
                "\"age\":20," +
                "\"email\":\"email4\"," +
                "\"article\":[]" +
                "}" +
                "]";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void create() throws Exception {
        Mockito.when(userService.create(Mockito.any(UserExt.class))).thenReturn(Optional.of(userExt));

        String content = "{\n" +
                "\t\"email\":\"email5@ukr.net\",\n" +
                "\t\"password\":\"123123123\",\n" +
                "\t\"confirmPassword\":\"123123123\",\n" +
                "\t\"firstName\":\"name5\",\n" +
                "\t\"age\":40\n" +
                "}";

        MvcResult result = mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON).content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("/users/5",
                response.getHeader(HttpHeaders.LOCATION));
    }
}
