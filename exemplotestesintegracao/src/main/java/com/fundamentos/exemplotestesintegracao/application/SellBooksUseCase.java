package com.fundamentos.exemplotestesintegracao.application;

import org.springframework.stereotype.Component;

import com.fundamentos.exemplotestesintegracao.services.BookStore;

@Component
public class SellBooksUseCase {
    private final BookStore bookStore;

    public SellBooksUseCase(BookStore bookStore) {
        this.bookStore = bookStore;
    }

    public void sell(String isbn, int quantity) {
        
    }
}
