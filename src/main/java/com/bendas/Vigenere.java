package com.bendas;

public class Vigenere {

  static String encryption(String text, String key) {
    char[] textArray = text.toCharArray();
    char[] keyArray = key.toCharArray();
    for (int i = 0; i < text.length(); i++) {
      textArray[i] += keyArray[i % keyArray.length];
      textArray[i] %= 2048;
    }
    return new String(textArray);
  }

  static String decryption(String text, String key) {
    char[] textArray = text.toCharArray();
    char[] keyArray = key.toCharArray();
    for (int i = 0; i < text.length(); i++) {
      textArray[i] -= keyArray[i % keyArray.length];
      textArray[i] %= 2048;
    }
    return new String(textArray);
  }
}
