package br.com.zup.edu.pizzaria.pizzas;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PizzaTest {

    @Test
    void deveCalcularOPrecoDaPizza(){

        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(new Ingrediente("Queijo", 500, new BigDecimal(20.0)));
        Pizza pizza = new Pizza("Muçarela", ingredientes);

        Assertions.assertEquals(40.0, pizza.getPreco());
    }


}