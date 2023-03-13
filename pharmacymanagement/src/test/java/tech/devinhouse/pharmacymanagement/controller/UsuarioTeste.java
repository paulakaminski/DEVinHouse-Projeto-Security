package tech.devinhouse.pharmacymanagement.controller;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class UsuarioTeste {

    private URI path;

    private ResultMatcher expectedResult;

    @Autowired
    MockMvc mock;

    private String jwtToken;

    @Before
    public void setUp() throws Exception{

        String usuarioLogin = "{\"email\": \"admin@usuario.com\", \"senha\": \"102030\"}";

        path = new URI("/usuario/login");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
                .content(usuarioLogin)
                .header("Content-Type", "application/json");

        expectedResult = MockMvcResultMatchers.status().isOk();

        String response = mock.perform(request).andExpect(expectedResult).andReturn().getResponse()
                .getContentAsString();

        JSONObject data = new JSONObject(response);

        jwtToken = data.getString("jwtToken");

    }

    @Test
    public void testeCadastrar() throws Exception{

        String usuarioCadastro = "{\"email\": \"teste@teste.com\", \"senha\": \"teste123\"}";

        path = new URI("/usuario/cadastro");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
                .content(usuarioCadastro)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken);

        expectedResult = MockMvcResultMatchers.status().isCreated();

        mock.perform(request).andExpect(expectedResult);
    }

}
