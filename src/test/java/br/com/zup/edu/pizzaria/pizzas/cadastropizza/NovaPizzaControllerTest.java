package br.com.zup.edu.pizzaria.pizzas.cadastropizza;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import br.com.zup.edu.pizzaria.ingredientes.IngredienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NovaPizzaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Test
    void deveCadastrarPizza() throws Exception {
        ingredienteRepository.save(new Ingrediente("Peperone", 1, new BigDecimal(10.0)));
        ingredienteRepository.save(new Ingrediente("Tomate", 1, new BigDecimal(10.0)));

        List<Long> ingredientes = new ArrayList<>();
        ingredientes.add(1L);
        ingredientes.add(2L);

        NovaPizzaRequest body = new NovaPizzaRequest("Peperone", ingredientes);
        MockHttpServletRequestBuilder request = post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("/api/pizzas/*"));

    }

    @Test
    void naoDeveCadatrarSemIngrediente() throws Exception {
        List<Long> ingredientes = new ArrayList<>();

        NovaPizzaRequest body = new NovaPizzaRequest("Peperone", ingredientes);
        MockHttpServletRequestBuilder request = post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCadastrarPizzaComSaborRepetido() throws Exception {
        ingredienteRepository.save(new Ingrediente("Peperone", 1, new BigDecimal(10.0)));
        ingredienteRepository.save(new Ingrediente("Tomate", 1, new BigDecimal(10.0)));

        List<Long> ingredientes = new ArrayList<>();
        ingredientes.add(1L);
        ingredientes.add(2L);

        NovaPizzaRequest body = new NovaPizzaRequest("Peperone", ingredientes);
        MockHttpServletRequestBuilder request = post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));


        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("/api/pizzas/*"));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    void naoDeveCadastrarPizzaComSaborNulo() throws Exception {
        ingredienteRepository.save(new Ingrediente("Peperone", 1, new BigDecimal(10.0)));
        ingredienteRepository.save(new Ingrediente("Tomate", 1, new BigDecimal(10.0)));

        List<Long> ingredientes = new ArrayList<>();
        ingredientes.add(1L);
        ingredientes.add(2L);

        NovaPizzaRequest body = new NovaPizzaRequest(null, ingredientes);
        MockHttpServletRequestBuilder request = post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

}