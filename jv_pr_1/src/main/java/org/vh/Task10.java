package org.vh;

/**
 * Розробити функцію (не використовувати готові методи класів обгорток), яка
 * визначає ціле позитивне число в десятковій системі, потім перетворює його в двійкову /
 * вісімкову/ шістнадцяткову системи числення.
 */
public class Task10 {

    public static void main(String[] args) {
        int numberOfPeople = 7215;
        System.out.println(getOctal(numberOfPeople));

        int numberOfCats = 2;
        System.out.println(getBinary(numberOfCats));

        int numberOfPancakes = 34515678;
        System.out.println(getHex(numberOfPancakes));
    }

    private static String getBinary(int n) {
        String s = "";

        while (n > 0) {
            s = (n % 2) + s;
            n = n / 2;
        }

        return s;
    }

    private static String getOctal(int n) {
        String s = "";

        while (n > 0) {
            s = (n % 8) + s;
            n = n / 8;
        }

        return s;
    }

    private static String getHex(int n) {
        String s = "";
        String[] symbols = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

        while (n > 0) {
            s = symbols[n % 16] + s;
            n = n / 16;
        }

        return s;
    }
}
