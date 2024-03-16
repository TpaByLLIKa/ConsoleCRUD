package com.ais.consolecrud.repository;

import com.ais.consolecrud.model.Author;
import com.ais.consolecrud.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    List<Author> getAllByStatusOrderByFirstName(Status status);
}
