package tech.devinhouse.pharmacymanagement.controller;

//import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import tech.devinhouse.pharmacymanagement.security.JwtTokenProvider;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class UsuarioTeste {

    private URI path;
    private MockHttpServletRequest request;
    private ResultMatcher expectedResult;

    @Autowired
    MockMvc mock;

    private String jwtTokenAdmin;
    private String jwtTokenGerente;
    private String jwtTokenColaborador;

    @Before
    public void setUp() throws Exception {
        jwtTokenAdmin = new JwtTokenProvider().generateToken("admin@usuario.com");
        jwtTokenGerente = new JwtTokenProvider().generateToken("gerente@usuario.com");
        jwtTokenColaborador = new JwtTokenProvider().generateToken("colaborador@usuario.com");
    }

//    @Before
//    public void setUp() throws Exception{
//
//        String usuarioLogin = "{\"login\": \"admin@teste.com\", \"senha\": \"102030\"}";
//
//        path = new URI("/login");
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
//                .contentType(MediaType.APPLICATION_JSON).content(usuarioLogin);
//
//        expectedResult = MockMvcResultMatchers.status().isOk();
//
//        String response = mock.perform(request).andExpect(expectedResult).andReturn().getResponse()
//                .getContentAsString();
//
//        JSONObject data = new JSONObject(response);
//
//        jwtToken = data.getString("Authorization");
//    }
    @Test
    public void testeCadastrar() throws Exception{

        String usuarioCadastro = "{\"email\": \"usuariotesteunit@teste.com\", \"senha\": \"teste123\"}";

        path = new URI("/usuario/cadastro");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
                .content(usuarioCadastro)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtTokenAdmin);

        expectedResult = MockMvcResultMatchers.status().isCreated();

        mock.perform(request).andExpect(expectedResult);
    }

}
