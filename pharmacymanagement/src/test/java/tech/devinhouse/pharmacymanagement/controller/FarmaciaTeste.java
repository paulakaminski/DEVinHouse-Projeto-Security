package tech.devinhouse.pharmacymanagement.controller;

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
import tech.devinhouse.pharmacymanagement.security.JwtTokenProvider;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
//@Transactional
public class FarmaciaTeste {

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

    @Test
    public void testeBuscarTodasAsFarmacias() throws Exception{
        path = new URI("/farmacia");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(path)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtTokenColaborador);

        expectedResult = MockMvcResultMatchers.status().isOk();

        mock.perform(request).andExpect(expectedResult);
    }
    @Test
    public void testeBuscarFarmaciaPorId() throws Exception{

        long idFarmacia = 9L;

        path = new URI("/medicamentos/"+idFarmacia);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(path)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtTokenColaborador);

        expectedResult = MockMvcResultMatchers.status().isOk();

        mock.perform(request).andExpect(expectedResult);
    }

    @Test
    public void testeCadastrarNovaFarmacia() throws Exception{

        String farmaciaCadastro =
                "{\"razaoSocial\": \"Clamed\", " +
                        "\"cnpj\": \"99.999.999/9999-99\", " +
                        "\"nomeFantasia\": \"Clamed Centro\", " +
                        "\"email\": \"clamed@teste.com.br\", " +
                        "\"telefoneCelular\": \"47 99999-9999\", " +
                        "\"cep\": \"89222-363\", " +
                        "\"numero\": \"1000\", " +
                        "\"latitude\": \"-26.266376\", " +
                        "\"longitude\": \"-48.843727\"" +
                        "}";

        path = new URI("/farmacia/cadastro");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
                .content(farmaciaCadastro)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtTokenGerente);

        expectedResult = MockMvcResultMatchers.status().isCreated();

        mock.perform(request).andExpect(expectedResult);
    }

    @Test
    public void testeAtualizarFarmaciaPorId() throws Exception{

        String farmaciaAtualizar =
                "{\"razaoSocial\": \"Clamed Att\", " +
                        "\"cnpj\": \"99.999.999/9999-99\", " +
                        "\"nomeFantasia\": \"Clamed Centro\", " +
                        "\"email\": \"clamed@teste.com.br\", " +
                        "\"telefoneCelular\": \"47 99999-9999\", " +
                        "\"cep\": \"89222-363\", " +
                        "\"numero\": \"1000\", " +
                        "\"latitude\": \"-26.266376\", " +
                        "\"longitude\": \"-48.843727\"" +
                        "}";

        long idFarmacia = 11L;

        path = new URI("/farmacia/update/"+idFarmacia);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(path)
                .content(farmaciaAtualizar)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtTokenGerente);

        expectedResult = MockMvcResultMatchers.status().isOk();

        mock.perform(request).andExpect(expectedResult);
    }

    @Test
    public void testeDeletarFarmaciaPorId() throws Exception{

        long idFarmacia = 13L;

        path = new URI("/farmacia/delete/"+idFarmacia);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(path)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtTokenAdmin);

        expectedResult = MockMvcResultMatchers.status().isAccepted();

        mock.perform(request).andExpect(expectedResult);
    }

}
