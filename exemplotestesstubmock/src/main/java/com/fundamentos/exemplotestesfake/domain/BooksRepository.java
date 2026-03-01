package com.fundamentos.exemplotestesfake.domain;

public interface BooksRepository {
    Book findByIsbn(String isbn);
    void save(Book book);
}
