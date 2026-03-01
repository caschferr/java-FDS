package com.bcopstein.ex5testeunitariobarca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BarcaBugsTest {

    BarcaBugs barca;

    @BeforeEach
    public void inicializa() {
        barca = new BarcaBugs();
    }

    // "[F][0-9]{2}[A][0-9]{2}"
    @Test
    public void assentoLivre() {
        int op = barca.ocupaLugar("F10A12");
        assertEquals(3, op);
    }

    @Test
    public void assentoOcupado() {
        barca.ocupaLugar(10, 12);
        int op = barca.ocupaLugar("F10A12");
        assertEquals(1, op);
    }

    @ParameterizedTest(name = "Teste parametrizado")
    @CsvSource({
        "F10A12, 3",
        "F02A19, 2"
    })
    void juntaTestes(String assento, int expected) {
        int op = barca.ocupaLugar(assento);
        assertEquals(expected, op);
    }

    @Test
    public void retornaDois() {
        int op = barca.ocupaLugar("F02A19");
        assertEquals(2, op);
    }
    
}
