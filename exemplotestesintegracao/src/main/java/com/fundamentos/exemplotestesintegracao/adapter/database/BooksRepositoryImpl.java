package com.fundamentos.exemplotestesintegracao.adapter.database;

import org.springframework.stereotype.Repository;

import com.fundamentos.exemplotestesintegracao.domain.Book;
import com.fundamentos.exemplotestesintegracao.domain.BooksRepository;

@Repository
public class BooksRepositoryImpl implements BooksRepository{
    private final BooksRepositoryJPA booksRepositoryJPA;

    public BooksRepositoryImpl(BooksRepositoryJPA booksRepositoryJPA) {
        this.booksRepositoryJPA = booksRepositoryJPA;
    }

    @Override
    public Book findByIsbn(String isbn) {
        var book = booksRepositoryJPA.findById(isbn);
        if (book.isPresent()) {
            return BookEntity.toDomain(book.get());
        }
        return null;
    }

    @Override
    public void save(Book book) {
        BookEntity bookEntity = BookEntity.fromDomain(book);
        booksRepositoryJPA.save(bookEntity);
    }

}
