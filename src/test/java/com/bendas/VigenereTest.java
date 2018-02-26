package com.bendas;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class VigenereTest {

  @Test
  public void cryptoTest() {
    String text = "Hello friend, it's I am";
    String key = "It's key";
    String encr = Vigenere.encryption(text, key);
    String decr = Vigenere.decryption(encr, key);

    assertThat(decr, is(text));
  }
}
