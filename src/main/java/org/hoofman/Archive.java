package org.hoofman;

import java.io.Serializable;
import java.util.HashMap;

public class Archive implements Serializable { // obiekt tej klasy będzie przesyłany przez sieć
    private final byte[] binaryValue;
    private final HashMap<String, Character> decodingDict;

    private final int length;

    public Archive(byte[] binaryValue, HashMap<String, Character> decodingDict, int length) {
        this.binaryValue = binaryValue;
        this.decodingDict = decodingDict;
        this.length = length;
    }

    public byte[] getBinaryValue() {
        return binaryValue;
    }

    public HashMap<String, Character> getDecodingDict() {
        return decodingDict;
    }

    public int getLength() {
        return length;
    }
}
