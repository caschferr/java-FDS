package com.fundamentos.exemplotestesintegracao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fundamentos.exemplotestesintegracao.adapter.web.Controller;

@SpringBootTest
class ExemplotestesintegracaoApplicationTests {

	@Autowired
	private Controller controller;

	@Test
	void contextLoads() {
		//implementa um teste de fumaça para verificar se o contexto da aplicação está rodando
		assertThat(controller).isNotNull();
	}

}
