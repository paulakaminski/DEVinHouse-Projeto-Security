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
public class MedicamentoTeste {

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
    public void testeBuscarTodosOsMedicamentos() throws Exception{
        path = new URI("/medicamentos");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(path)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken);

        expectedResult = MockMvcResultMatchers.status().isOk();

        mock.perform(request).andExpect(expectedResult);
    }

    @Test
    public void testeBuscarMedicamentoPorId() throws Exception{

        long idMedicamento = 1L;

        path = new URI("/medicamentos/"+idMedicamento);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(path)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken);

        expectedResult = MockMvcResultMatchers.status().isOk();

        mock.perform(request).andExpect(expectedResult);
    }

    @Test
    public void testeCadastrarNovoMedicamento() throws Exception{

        String medicamentoCadastro =
                "{\"nome\": \"Benegrip\", " +
                        "\"laboratorio\": \"Cimed\", " +
                        "\"dosagem\": \"500mg\", " +
                        "\"descricao\": \"Benegrip é um antigripal que alivia os sintomas de gripes e resfriados.\", " +
                        "\"precoUnitario\": \"25.90\", " +
                        "\"tipo\": \"Comum\"" +
                        "}";

        path = new URI("/medicamentos/cadastro");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
                .content(medicamentoCadastro)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken);

        expectedResult = MockMvcResultMatchers.status().isCreated();

        mock.perform(request).andExpect(expectedResult);
    }

    @Test
    public void testeAtualizarMedicamentoPorId() throws Exception{

        String medicamentoAtualizar =
                "{\"nome\": \"Dorflex\", " +
                        "\"laboratorio\": \"Sanofi\", " +
                        "\"dosagem\": \"300mg\", " +
                        "\"descricao\": \"Analgésico e relaxante muscular 10 comprimidos.\", " +
                        "\"precoUnitario\": \"7.08\", " +
                        "\"tipo\": \"Comum\"" +
                        "}";

        long idMedicamento = 2L;

        path = new URI("/medicamentos/update/"+idMedicamento);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(path)
                .content(medicamentoAtualizar)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken);

        expectedResult = MockMvcResultMatchers.status().isOk();

        mock.perform(request).andExpect(expectedResult);
    }

    @Test
    public void testeDeletarMedicamentoPorId() throws Exception{

        long idMedicamento = 3L;

        path = new URI("/medicamentos/delete/"+idMedicamento);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(path)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken);

        expectedResult = MockMvcResultMatchers.status().isAccepted();

        mock.perform(request).andExpect(expectedResult);
    }

}
