package com.ais.consolecrud.repository;

import com.ais.consolecrud.model.Author;
import com.ais.consolecrud.model.Book;
import com.ais.consolecrud.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> getByAuthorOrderByName(Author author);

    List<Book> getAllByStatusOrderByName(Status status);

    List<Book> getAllByNameOrderByName(String name);
}
