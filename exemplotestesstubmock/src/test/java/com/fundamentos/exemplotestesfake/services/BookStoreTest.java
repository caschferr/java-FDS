package com.fundamentos.exemplotestesfake.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.fundamentos.exemplotestesfake.domain.Book;
import com.fundamentos.exemplotestesfake.domain.BooksRepository;
import com.fundamentos.exemplotestesfake.domain.BuyBookProcess;
import com.fundamentos.exemplotestesfake.domain.Overview;
import com.fundamentos.exemplotestesfake.services.BookStore;

@ExtendWith(MockitoExtension.class)
class BookStoreTest {
    @Mock
    private BooksRepository bookRepo;
    @Mock
    private BuyBookProcess process;
    private BookStore store;

    @BeforeEach
    void setUp() {
        store = new BookStore(bookRepo, process);
    }

    @Test
    void nullOrder() {
        Overview overview = store.getPriceForCart(null);
        assertThat(overview).isNull();
        verify(process, never()).buyBook(any(Book.class), any(Integer.class));
    }

    @Test
    void emptyOrder() {
        Map<String, Integer> order = new HashMap<>();
        Overview overview = store.getPriceForCart(order);
        assertThat(overview.getTotalPrice()).isZero();
        assertThat(overview.getUnavailable()).isEmpty();
        verify(process, never()).buyBook(any(Book.class), any(Integer.class));
    }

    @Test
    void oneBookAvailable() {
        //Arrange
        Book book1 = new Book("123", 10, 10);
        when(bookRepo.findByIsbn("123")).thenReturn(book1);
        Map<String, Integer> order = new HashMap<>();
        order.put("123", 1);
        //Act
        Overview overview = store.getPriceForCart(order);
        //Assert
        assertThat(overview.getTotalPrice()).isEqualTo(10);
        assertThat(overview.getUnavailable()).isEmpty();
        verify(process).buyBook(book1, 1);
    }

    @Test
    void oneBookUnavailable() {
        //Arrange
        Book book1 = new Book("123", 10, 10);
        when(bookRepo.findByIsbn("123")).thenReturn(book1);
        Map<String, Integer> order = new HashMap<>();
        order.put("123", 11);
        //Act
        Overview overview = store.getPriceForCart(order);
        //Assert
        assertThat(overview.getTotalPrice()).isEqualTo(100);
        assertThat(overview.getUnavailable()).containsExactly(entry(book1, 1));
        verify(process).buyBook(book1, 10);
    }

    @Test
    void complexOrder() {
        /**
         * Criando 3 livros:
         * - um onde a quantidade é suficiente para o pedido
         * - um onde a quantidade é precisamente a que foi solicitada no pedido
         * - um onde a quantidade é insuficiente para o pedido
         */
        Book book1 = new Book("PRODUCT-ENOUGH-QTY", 20, 11); // 11 > 5
        Book book2 = new Book("PRODUCT-PRECISE-QTY", 25, 10); // 10 == 10
        Book book3 = new Book("PRODUCT-NOT-ENOUGH", 37, 21);  // 21 < 22
        when(bookRepo.findByIsbn("PRODUCT-ENOUGH-QTY")).thenReturn(book1);
        when(bookRepo.findByIsbn("PRODUCT-PRECISE-QTY")).thenReturn(book2);
        when(bookRepo.findByIsbn("PRODUCT-NOT-ENOUGH")).thenReturn(book3);
        Map<String, Integer> order = new HashMap<>();
        order.put("PRODUCT-ENOUGH-QTY", 5);
        order.put("PRODUCT-PRECISE-QTY", 10);
        order.put("PRODUCT-NOT-ENOUGH", 22);
        //Act
        Overview overview = store.getPriceForCart(order);
        //Assert
        int expectedPrice =
                5*20 + // from the first product
                10*25 + // from the second product
                21*37; // from the third product
        assertThat(overview.getTotalPrice()).isEqualTo(expectedPrice);
        assertThat(overview.getUnavailable()).containsExactly(entry(book3, 1));
        verify(process).buyBook(book1, 5);
        verify(process).buyBook(book2, 10);
        verify(process).buyBook(book3, 21);
    }
}
