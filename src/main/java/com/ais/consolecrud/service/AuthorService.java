package com.ais.consolecrud.service;

import com.ais.consolecrud.model.Author;
import com.ais.consolecrud.model.enums.Status;
import com.ais.consolecrud.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<Author> getAll() {
        return authorRepository.getAllByStatusOrderByFirstName(Status.ACTIVE);
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    public void safeDelete(Author author) {
        author.setStatus(Status.DELETED);
        save(author);
    }

    public void delete(Author author) {
        authorRepository.delete(author);
    }

    public List<Author> getAllSafeDeleted() {
        return authorRepository.getAllByStatusOrderByFirstName(Status.DELETED);
    }

    public void restore(Author author) {
        author.setStatus(Status.ACTIVE);
        save(author);
    }
}
