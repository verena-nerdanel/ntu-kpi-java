package org.vh.generics;

import org.vh.generics.model.humans.Human;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

public class HumanIO {

    public static void save(List<Human> humans, Path fileName) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName.toFile()))) {
            outputStream.writeObject(humans);
        }
    }

    public static List<Human> load(Path fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName.toFile()))) {
            return (List<Human>) inputStream.readObject();
        }
    }
}
