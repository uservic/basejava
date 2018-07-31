package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
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

        traverseAndPrint("..\\basejava\\src\\ru\\javawebinar\\basejava");
    }

    public static void traverseAndPrint(String startingPath) {
        File dir = new File(startingPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("\t" + "File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    traverseAndPrint(file.getPath());
                }
            }
        }
    }
}