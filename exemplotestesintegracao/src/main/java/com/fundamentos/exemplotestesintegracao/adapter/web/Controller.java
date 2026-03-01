package com.fundamentos.exemplotestesintegracao.adapter.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundamentos.exemplotestesintegracao.domain.Book;
import com.fundamentos.exemplotestesintegracao.services.BookStore;

@RestController
@RequestMapping("/bookstores")
public class Controller {
    private final BookStore bookStore;
    
    public Controller(BookStore bookStore) {
        this.bookStore = bookStore;
    }

    @GetMapping
    public String getWelcomeMessage() {
        return "Welcome to the Book Store!";
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Book book = bookStore.getBook(isbn);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }
}
