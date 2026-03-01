package com.fundamentos.exemplotestesintegracao.domain;

public interface BooksRepository {
    Book findByIsbn(String isbn);
    void save(Book book);
}
