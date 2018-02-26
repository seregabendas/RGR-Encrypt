package com.bendas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class StartClass {

  public static void main(String[] args) {
    StartClass startClass = new StartClass();
    if (!startClass.access()) {
      return;
    }
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("Enter command:");
      String command = scanner.nextLine()
          .toLowerCase();
      switch (command) {
        case "exit":
          return;
        case "encrypt":
          encryptText();
          break;
        case "decrypt":
          decryptText();
          break;
        default:
          System.out.println("Unknown command");
      }
    }
  }

  private static void decryptText() {
    try {
      String fileName = getFileName();
      String fileText = getFileText(fileName, false);
      int fileHash = fileText.hashCode();
      checkFile(fileName, fileHash);
      String key = getKey();
      String decryptedText = Vigenere.decryption(fileText, key);
      writeToFile(fileName, decryptedText);
      writeToFile("hash" + fileName, Integer.toString(decryptedText.hashCode()));
      System.out.println("done");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void checkFile(String fileName, int fileHash) throws IOException {
    int realHash = 0;
    try {
      realHash = Integer.parseInt(getFileText("hash" + fileName, false));
    } catch (Exception ignored) { }
    if (realHash != fileHash) {
      System.out.println("File was changed");
    }
  }

  private static void encryptText() {
    try {
      String fileName = getFileName();
      String fileText = getFileText(fileName, true);
      int fileHash = fileText.hashCode();
      checkFile(fileName, fileHash);
      String key = getKey();
      String encryptedText = Vigenere.encryption(fileText, key);
      writeToFile(fileName, encryptedText);
      writeToFile("hash" + fileName, Integer.toString(encryptedText.hashCode()));
      System.out.println("done");
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  private static void writeToFile(String fileName, String text) throws IOException {
    ClassLoader classLoader = StartClass.class.getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());
    FileWriter fr = new FileWriter(file);
    fr.write(text);
    fr.close();
  }

  private static String getFileText(String fileName, boolean encr)
      throws java.lang.NullPointerException, IOException {
    ClassLoader classLoader = StartClass.class.getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());
    Scanner scanner = new Scanner(file);
    StringBuilder sb = new StringBuilder();
    String s;
    while (true) {
      try {
        s = scanner.nextLine();
        sb.append(s);
        if (encr) {
          sb.append('\n');
        }
      } catch (Exception e) {
        break;
      }
    }
    s = sb.toString();
    return s;
  }

  private static String getFileName() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter file: ");
    return scanner.nextLine();
  }

  private static String getKey() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter key for this file: ");
    return scanner.nextLine();
  }

  private boolean access() {
    List<String> users = getUsers();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter user name: ");
    String userName = scanner.nextLine();
    System.out.println("Enter user password: ");
    String userPass = scanner.nextLine();
    for (String user : users) {
      if ((userName + ":" + userPass).equals(user)) {
        System.out.println("Access is provided");
        return true;
      }
    }
    System.out.println("Access is denied");
    return false;
  }

  private List<String> getUsers() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("users.txt").getFile());
    List<String> users = new ArrayList<>();
    System.out.println(file.toPath());
    try (Stream<String> stream = Files.lines(file.toPath())) {
      stream.forEach(e -> users.add(e));
    } catch (IOException ignored) {
    }
    return users;
  }
}
