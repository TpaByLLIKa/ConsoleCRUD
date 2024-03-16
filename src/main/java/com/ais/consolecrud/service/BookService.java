package com.ais.consolecrud.service;

import com.ais.consolecrud.model.Author;
import com.ais.consolecrud.model.Book;
import com.ais.consolecrud.model.enums.Status;
import com.ais.consolecrud.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAll() {
        return bookRepository.getAllByStatusOrderByName(Status.ACTIVE);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void safeDelete(Book book) {
        book.setStatus(Status.DELETED);
        save(book);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public List<Book> getByAuthor(Author author) {
        return bookRepository.getByAuthorOrderByName(author)
                .stream()
                .filter(book -> book.getStatus().equals(Status.ACTIVE))
                .toList();
    }

    public List<Book> getAllByName(String name, Status status) {
        return bookRepository.getAllByNameOrderByName(name)
                .stream()
                .filter(book -> book.getStatus().equals(status))
                .toList();
    }

    public List<Book> getAllSafeDeleted() {
        return bookRepository.getAllByStatusOrderByName(Status.DELETED);
    }

    public void restore(Book book) {
        book.setStatus(Status.ACTIVE);
        save(book);
    }

}
