package com.ais.consolecrud.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
    DETECTIVE("Detective"),
    FANTASY("Fantasy"),
    NOVEL("Novel"),
    THRILLER("Thriller"),
    HORROR("Horror"),
    ADVENTURE("Adventure"),
    SCIENCE_FICTION("Science Fiction"),
    HISTORICAL_NOVEL("Historical Novel"),
    DRAMA("Drama"),
    COMEDY("Comedy"),
    CHILDREN_LITERATURE("Children's Literature"),
    POETRY("Poetry"),
    BIOGRAPHY("Biography"),
    OTHER("Other");

    private final String name;
}
