package com.fundamentos.exemplotestesintegracao.adapter.web;

import org.springframework.stereotype.Component;

import com.fundamentos.exemplotestesintegracao.domain.Book;
import com.fundamentos.exemplotestesintegracao.domain.BuyBookProcess;

@Component
public class BuyBookProcessImpl implements BuyBookProcess {
    @Override
    public void buyBook(Book book, int amount) {
        // implementation
    }

}
