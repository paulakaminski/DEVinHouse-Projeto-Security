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
import org.springframework.transaction.annotation.Transactional;
import tech.devinhouse.pharmacymanagement.security.JwtTokenProvider;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
//@Transactional
public class MedicamentoTeste {

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
    public void testeBuscarTodosOsMedicamentos() throws Exception{
        path = new URI("/medicamentos");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(path)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtTokenGerente);

        expectedResult = MockMvcResultMatchers.status().isOk();

        mock.perform(request).andExpect(expectedResult);
    }

    @Test
    public void testeBuscarMedicamentoPorId() throws Exception{

        long idMedicamento = 8L;

        path = new URI("/medicamentos/"+idMedicamento);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(path)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtTokenColaborador);

        expectedResult = MockMvcResultMatchers.status().isOk();

        mock.perform(request).andExpect(expectedResult);
    }

    @Test
    public void testeCadastrarNovoMedicamento() throws Exception{

        String medicamentoCadastro =
                "{\"nome\": \"Dorflex\", " +
                        "\"laboratorio\": \"Sanofi\", " +
                        "\"dosagem\": \"300mg\", " +
                        "\"descricao\": \"Analgésico e relaxante muscular 10 comprimidos.\", " +
                        "\"precoUnitario\": \"6.49\", " +
                        "\"tipo\": \"Comum\"" +
                        "}";

        path = new URI("/medicamentos/cadastro");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
                .content(medicamentoCadastro)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtTokenAdmin);

        expectedResult = MockMvcResultMatchers.status().isCreated();

        mock.perform(request).andExpect(expectedResult);
    }

    @Test
    public void testeAtualizarMedicamentoPorId() throws Exception{

        String medicamentoAtualizar =
                "{\"nome\": \"Ibuprofeno\", " +
                        "\"laboratorio\": \"Medley\", " +
                        "\"dosagem\": \"100mg\", " +
                        "\"descricao\": \"Este medicamento é indicado para redução da febre e para o alívio de dores.\", " +
                        "\"precoUnitario\": \"10.74\", " +
                        "\"tipo\": \"Comum\"" +
                        "}";

        long idMedicamento = 8L;

        path = new URI("/medicamentos/update/"+idMedicamento);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(path)
                .content(medicamentoAtualizar)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtTokenAdmin);

        expectedResult = MockMvcResultMatchers.status().isOk();

        mock.perform(request).andExpect(expectedResult);
    }

    @Test
    public void testeDeletarMedicamentoPorId() throws Exception{

        long idMedicamento = 11L;

        path = new URI("/medicamentos/delete/"+idMedicamento);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(path)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtTokenAdmin);

        expectedResult = MockMvcResultMatchers.status().isAccepted();

        mock.perform(request).andExpect(expectedResult);
    }


}
