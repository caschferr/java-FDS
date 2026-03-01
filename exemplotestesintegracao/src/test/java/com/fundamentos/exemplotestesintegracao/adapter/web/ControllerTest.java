package com.fundamentos.exemplotestesintegracao.adapter.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// Utilizando a anotação @SpringBootTest com o atributo webEnvironment definido como RANDOM_PORT,
// o Spring Boot irá iniciar o servidor web em uma porta aleatória, permitindo que os testes sejam executados em um ambiente mais próximo do ambiente de produção.
class ControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getWelcomeMessage() {
        String body = testRestTemplate.getForObject("http://localhost:" + port + "//bookstores", String.class);
        assertThat(body).isEqualTo("Welcome to the Book Store!");
    }

    @Test
    void getNonExistantBookByIsbn() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" + port + "/bookstores/books/123", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    void getExistantBookByIsbn() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" + port + "/bookstores/books/456", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotBlank();
        DocumentContext json = JsonPath.parse(response.getBody());
        String isbn = json.read("$.isbn");
        assertThat(isbn).isEqualTo("456");
        Number price = json.read("$.price");
        assertThat(price).isEqualTo(1);
        Number amount = json.read("$.amount");
        assertThat(amount).isEqualTo(1);
    }
}
