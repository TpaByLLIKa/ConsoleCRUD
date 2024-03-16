package com.ais.consolecrud.model;

import com.ais.consolecrud.model.enums.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "crud$Book")
@Table(name = "crud_book")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Book extends BaseUuidEntity {

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Book title cannot be empty")
    private String name;

    @JoinColumn(name = "author_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Author author;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    @Column(name = "written_year")
    private Integer writtenYear;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(name = "publishing")
    private String publishing;

    @Override
    public String getInfo() {
        return String.format(
                "%s - %s, Genre: %s, Writing/publication year: %s/%s, Publisher: %s",
                name,
                author.getInfo(),
                genre.getName(),
                writtenYear.toString(),
                publicationYear.toString(),
                publishing
        );
    }
}
