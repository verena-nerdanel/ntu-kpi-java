package org.vh.translator;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Translator {
    private final Path fileName;
    private final Map<String, String> dictionary;

    public Translator(Path fileName) throws IOException {
        this.fileName = fileName;
        this.dictionary = new HashMap<>();
        reloadDictionary();
    }

    public void reloadDictionary() throws IOException {
        dictionary.clear();

        try (Stream<String> lines = Files.lines(fileName)) {
            lines
                    .map(line -> line.split("\\s"))
                    .forEach(parts -> addWord(parts[0], parts[1]));
        }
    }

    public String translateWord(String word) {
        if (dictionary.containsKey(word.toLowerCase())) {
            String translation = dictionary.get(word.toLowerCase());

            // fix case
            if (StringUtils.isAllUpperCase(word)) {
                translation = translation.toUpperCase();
            }

            if (StringUtils.isMixedCase(word)) {
                translation = StringUtils.capitalize(translation);
            }

            return translation;
        } else {
            return word;
        }
    }

    public String translatePhrase(String line) {
        return Arrays.stream(line.split("\\W"))
                .filter(StringUtils::isNotBlank)
                .map(this::translateWord)
                .collect(Collectors.joining(" "));
    }

    public void addWord(String original, String translation) {
        dictionary.put(
                original.trim().toLowerCase(),
                translation.trim().toLowerCase()
        );
    }
}
