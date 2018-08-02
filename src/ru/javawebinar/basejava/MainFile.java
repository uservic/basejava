package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    private static int nestedCounter = 0;

    public static void main(String[] args) {

//        File filePath = new File("..");
//        try {
//            System.out.println(filePath.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }
//        File dir = new File("./src/ru/javawebinar/basejava");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }
//
//        try (FileInputStream fis = new FileInputStream(".gitignore")) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        traverseAndPrint("..\\basejava\\src");
    }

    public static void traverseAndPrint(String startingPath) {
        StringBuilder indent = new StringBuilder("");
        for (int i = 0; i < nestedCounter; i++) {
            indent.append("\t");
        }

        File dir = new File(startingPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(indent + "File: " + file.getName());
                } else if (file.isDirectory()) {
                    nestedCounter++;
                    System.out.println(indent + "Directory: " + file.getName());
                    traverseAndPrint(file.getPath());
                    nestedCounter--;
                }
            }
        }
    }
}