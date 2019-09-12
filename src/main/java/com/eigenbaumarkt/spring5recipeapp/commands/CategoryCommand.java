package com.eigenbaumarkt.spring5recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// "command beans" for data-model <=> Controller <=> thymeleaf form
// Bemerkung: es ist möglich, dem Controller direkt die Domain-Objekte zu zeigen;
// durch "Command Objects" ist es jedoch möglich, die "backend-internen" domain-Objekte
// den Bedürfnissen der Programmierung entsprechend zu gestalten und die Daten
// für das Frontend mit den command-Objekten den Bedürfnissen entsprechend bereit zu stellen
@Setter
@Getter
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String description;
}
