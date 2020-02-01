package com.eigenbaumarkt.spring5recipeapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // if recipe-Object is deleted, the 1-to-1-related notes also will be deleted
    // if notes-object is deleted, the 1-to-1-related recipe doesn't care
    @OneToOne
    private Recipe recipe;

    // we will store longer than 255-char-Strings,
    // @Lob tells Hibernate here to use a "Character Large Object-" (CLOB-) field in the database
    @Lob
    private String recipeNotes;
}
