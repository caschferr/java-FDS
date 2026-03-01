package com.fundamentos.exemplotestesfake.domain;

import java.util.Objects;

public class Book {
    private String isbn;
    private int price;
    private int amount;

    public Book(String isbn, int price, int amount) {
        this.isbn = isbn;
        this.price = price;
        this.amount = amount;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPrice() {
        return price;
    }
    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}