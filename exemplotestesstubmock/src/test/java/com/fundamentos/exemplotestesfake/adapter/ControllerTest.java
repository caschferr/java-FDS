package com.fundamentos.exemplotestesfake.adapter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.fundamentos.exemplotestesfake.domain.Book;
import com.fundamentos.exemplotestesfake.services.BookStore;

@WebMvcTest(Controller.class)
// Utilizando a anotação @WebMvcTest, o Spring Boot irá carregar apenas o contexto do Spring MVC,
// sem a necessidade de carregar o contexto completo da aplicação.
class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BookStore bookStore;

    @Test
    void getWelcomeMessage() throws Exception {
        mockMvc
            .perform(get("/bookstores"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(content().string("Welcome to the Book Store!"));
    }

    @Test
    void getNonExistantBookByIsbn() throws Exception {
        mockMvc
            .perform(get("/bookstores/books/123"))
            .andExpect(status().isNotFound())
            .andExpect(content().string(""));
    }

    @Test
    void getExistantBookByIsbn() throws Exception {
        when(bookStore.getBook("456")).thenReturn(new Book("456", 1, 1));
        //ou com estilo BDD
        //given(bookStore.getBook("456")).willReturn(new Book("456", 1, 1));
        mockMvc
            .perform(get("/bookstores/books/456"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().json("{\"isbn\":\"456\",\"price\":1,\"amount\":1}"));
    }

}
