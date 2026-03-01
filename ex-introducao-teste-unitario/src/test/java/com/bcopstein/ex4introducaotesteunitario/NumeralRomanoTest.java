package com.bcopstein.ex4introducaotesteunitario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NumeralRomanoTest {

    NumeralRomano romano;

    @BeforeEach
    private void inicializa() {
        romano = new NumeralRomano();
    }

    @Test
    public void testaSoma() {
        int rEsp = 18;
        int rObs = romano.convert("XVIII");
        assertEquals(rEsp, rObs);
    }
}
