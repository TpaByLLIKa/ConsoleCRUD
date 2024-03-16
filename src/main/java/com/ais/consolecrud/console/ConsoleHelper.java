package com.ais.consolecrud.console;

import com.ais.consolecrud.model.BaseUuidEntity;

import java.util.List;

public class ConsoleHelper {
    public static void clearAndPrintHeader(String header) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.printf("""
                Book accounting system
                %s
                """, header);
    }

    public static void printMenu(String zeroAction, String... actions) {
        System.out.println("Select an action:\n");

        printElements(List.of(actions));

        System.out.printf("0. %s%n", zeroAction);
    }

    public static void printCatalogMenu(String zeroAction, List<? extends BaseUuidEntity> entities, String... actions) {
        printElements(entities);
        printMenu(zeroAction, actions);
    }

    public static void printElements(List<?> elements) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < elements.size(); i++) {
            sb.append(String.format(
                    "%d. %s\n",
                    i+1,
                    elements.get(i) instanceof BaseUuidEntity ?
                            ((BaseUuidEntity) elements.get(i)).getInfo()
                            : elements.get(i).toString())
            );
        }

        System.out.println(sb);
    }
}
