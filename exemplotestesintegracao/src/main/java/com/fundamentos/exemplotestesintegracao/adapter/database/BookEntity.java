package com.fundamentos.exemplotestesintegracao.adapter.database;

import com.fundamentos.exemplotestesintegracao.domain.Book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    private String isbn;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int amount;

    protected BookEntity() {
    }

    public BookEntity(String isbn, int price, int amount) {
        this.isbn = isbn;
        this.price = price;
        this.amount = amount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public static BookEntity fromDomain(Book book) {
        return new BookEntity(book.getIsbn(), book.getPrice(), book.getAmount());
    }

    public static Book toDomain(BookEntity bookEntity) {
        return new Book(bookEntity.getIsbn(), bookEntity.getPrice(), bookEntity.getAmount());
    }
}
