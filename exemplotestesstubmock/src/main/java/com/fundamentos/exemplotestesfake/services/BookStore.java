package com.fundamentos.exemplotestesfake.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fundamentos.exemplotestesfake.domain.Book;
import com.fundamentos.exemplotestesfake.domain.BooksRepository;
import com.fundamentos.exemplotestesfake.domain.BuyBookProcess;
import com.fundamentos.exemplotestesfake.domain.Overview;

@Service
public class BookStore {

    private BooksRepository booksRepository;
    private BuyBookProcess process;

    public BookStore(BooksRepository bookRepository, BuyBookProcess process) {
        this.booksRepository = bookRepository;
        this.process = process;
    }

    private void retrieveBook(String isbn, int amount, Overview overview) {
        Book book = booksRepository.findByIsbn(isbn);
        if (book.getAmount() < amount) {
            overview.addUnavailable(book, amount - book.getAmount());
            amount = book.getAmount();
        }
        overview.addToTotalPrice(amount * book.getPrice());
        process.buyBook(book, amount);
    }

    public Overview getPriceForCart(Map<String, Integer> order) {
        if(order==null)
            return null;
        Overview overview = new Overview();
        for (String isbn : order.keySet()) {
            retrieveBook(isbn, order.get(isbn), overview);
        }
        return overview;
    }

    public Book getBook(String isbn) {
        return booksRepository.findByIsbn(isbn);
    }
}