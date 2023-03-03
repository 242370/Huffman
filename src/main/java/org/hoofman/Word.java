package org.hoofman;

public class Word {
    private char letter;
    private String bytes; // wartość binarna przypisana do litery

    public Word(char letter, String bytes) {
        this.letter = letter;
        this.bytes = bytes;
    }
}
