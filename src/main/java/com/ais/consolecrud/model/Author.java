package com.ais.consolecrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "crud$Author")
@Table(name = "crud_author")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Author extends BaseUuidEntity {

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "Author firstname cannot be empty")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Author lastname cannot be empty")
    private String lastName;

    @Override
    public String getInfo() {
        return firstName + " " + lastName;
    }
}
