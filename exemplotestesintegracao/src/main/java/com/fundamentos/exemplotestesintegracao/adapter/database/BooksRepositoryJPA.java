package com.fundamentos.exemplotestesintegracao.adapter.database;

import org.springframework.data.repository.ListCrudRepository;

public interface BooksRepositoryJPA extends ListCrudRepository<BookEntity, String> {
    
}
