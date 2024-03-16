package com.ais.consolecrud.console;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
@RequiredArgsConstructor
public class MainMenu extends AbstractMenu {
    private final BookMenu bookMenu;
    private final AuthorsMenu authorsMenu;
    private final ArchiveMenu archiveMenu;

    @Override
    public void run() {
        boolean running = true;

        do {
            ConsoleHelper.clearAndPrintHeader("Main menu");
            ConsoleHelper.printMenu(
                    "Exit",
                    "Book catalog",
                    "Authors catalog",
                    "Archive"
            );

            try {
                switch (scanner.nextInt()) {
                    case 0:
                        running = false;
                        ConsoleHelper.clearAndPrintHeader("Shutdowning...");
                        break;
                    case 1:
                        bookMenu.run();
                        break;
                    case 2:
                        authorsMenu.run();
                        break;
                    case 3:
                        archiveMenu.run();
                        break;
                    default:
                        running = isContinue("No such action exists");
                        break;
                }
            } catch (InputMismatchException e) {
                running = isContinue();
            }
        } while (running);
    }
}
