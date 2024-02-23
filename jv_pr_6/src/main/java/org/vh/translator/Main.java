package org.vh.translator;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Напишіть консольний додаток, який:
 * - описує клас, який є перекладачем з англійської мови та містить словник
 * англійських та українських слів у вигляді колекції HashMap <String,
 * String>, метод для додавання пар слів та метод для перекладу отриманої
 * фрази;
 * - створює екземпляр описаного класу, наповнює словник (також підтримує
 * введення даних з клавіатури);
 * - вводить деяку фразу англійською мовою та відображає її переклад
 * українською мовою.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        final Scanner scanner = new Scanner(System.in);
        final Translator translator = new Translator(Paths.get("dictionary.txt"));

        while (true) {
            System.out.println("Enter phrase in English, or 'exit' to exit:");
            final String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("exit")) {
                return;
            }

            translator.reloadDictionary();
            System.out.println(translator.translatePhrase(line));
            System.out.println("================================================");
        }
    }
}