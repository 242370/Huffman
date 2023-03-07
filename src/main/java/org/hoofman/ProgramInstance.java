package org.hoofman;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProgramInstance {
    private static String sourceFile = "source.txt"; // statyczne, żeby funkcja main mogła się odwołać
    private static String destinationFile = "destination.txt";
    private static String binaryFile = "source.bin"; // binarna wersja pliku źródłowego

    // kompresja pliku tekstowego
    private static byte[] charsToBinary(String code) // byte, aby zaszła kompresja, bez tego 2 bajtowe chary
    {
        return new BigInteger(code, 2).toByteArray(); // zwracana jest jedna liczba w systemie binarnym
    }

    // dekompresja
    private static String binaryToChars(byte[] binaryValue, int length) {
        String result = new BigInteger(binaryValue).toString(2); // zamiana ciagu bitów na tekst, bez zmiany systemu
        String padding = new String();
        for (int i = 0; i < length - result.length(); i++) {
            padding += '0';
        }
        return padding + result;
    }

    // tworzenie obiektu wykorzystywanego do przesyłu
    private static Archive makeZip() {
        String text = new String();
        Archive archive = null; // zserializowany obiekt archiwum
        try {
            text = Files.readString(Path.of(sourceFile));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Huffman huffman = new Huffman();
        try {
            String code = huffman.encode(text);
            archive = new Archive(charsToBinary(code), huffman.getDecodingDict(), code.length());
            System.out.println(code);
            Files.write(Path.of(binaryFile), charsToBinary(code));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return archive;
    }

    // odczyt z odebranego oobjektu archiwum
    private static void readZip(Archive archive) throws Exception {
        if (archive == null) {
            throw new Exception("No binary object provided");
        }
        Files.write(Path.of(binaryFile), archive.getBinaryValue()); // zapis skompresowanej wiadomości u odbiorcy
        Huffman huffman = new Huffman();
        String text = new String();
        System.out.println(binaryToChars(archive.getBinaryValue(), archive.getLength()));
        try {
            text = huffman.decode(binaryToChars(archive.getBinaryValue(), archive.getLength()), archive.getDecodingDict());
            Files.writeString(Path.of(destinationFile), text);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        int flag = 0; // określenie funkcji gniazda
        switch (flag)
        {
            case 0: // nasłuch
                System.out.println("I'm listening");
                break;
            case 1: // przesył
                try {
                    readZip(makeZip());
                    System.out.println("Data sent");
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            default:
                System.out.println("Unknown code");
        }
    }
}
