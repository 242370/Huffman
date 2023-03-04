package org.hoofman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Huffman {
    private HashMap<Character, Integer> frequencies; // klucz - litera, wartość - liczba wystąpień
    private ArrayList<Node> tree; // lista, w której przechowywane są węzły od największego
    private HashMap<Character, String> encodingDict; // klucz - litera, wartość - słowo kodowe (bity)
    private HashMap<String, Character> decodingDict; // klucz - słowo kodowe, wartość - litera

    // Tworzenie Stringa do przesłania
    public String encode(String text) throws Exception {
        if (text == null || text.isEmpty()) {
            throw new Exception("Given text contains no data");
        }
        this.encodingDict = new HashMap<>(); // czyszczenie struktur danych
        this.decodingDict = new HashMap<>();
        countFrequencies(text);
        fillTree();
        fillDicts(this.tree.get(0), "");
        String result = new String();
        for (var letter : text.toCharArray())
        {
            result += this.encodingDict.get(letter);
        }
        return result;

    }

    // Wypełnianie mapy "freqencies"
    private void countFrequencies(String text) {
        this.frequencies = new HashMap<>();
        for (var letter : text.toCharArray())  // nie można bez tego iterować po tekście
        {
            if (this.frequencies.containsKey(letter)) // w przypadku, kiedy litera już znajduje się w mapie
            {
                this.frequencies.put(letter, this.frequencies.get(letter) + 1);
            } else {
                this.frequencies.put(letter, 1);
            }
        }
    }

    // budowanie drzewa kodowego
    private void fillTree() {
        this.tree = new ArrayList<>();
        for (var key : this.frequencies.keySet()) {
            this.tree.add(new Node(this.frequencies.get(key), key, null, null)); // na razie bez struktury
        }
        if (this.tree.size() == 1) // w przypadku tekstu z tylko jednym znakiem
        {
            Node right = this.tree.get(0); // jedyny element
            Node aggregator = new Node(right.getNumericalValue(), '\0', null, right);
            this.tree.set(0, aggregator); // mimo tylko jednego znaku, powstaje węzeł
        }
        while (this.tree.size() > 1) {
            Collections.sort(this.tree); // sortowanie tablicy, żeby zawsze brać najmniejsze dwa
            Node left = this.tree.get(this.tree.size() - 1); // ostatni element
            Node right = this.tree.get(this.tree.size() - 2); // przedostatni element
            Node aggregator = new Node(left.getNumericalValue() + right.getNumericalValue(), '\0', left, right);
            this.tree.remove(this.tree.size() - 1); // pomniejszenie rozmaiaru tablicy o 1
            this.tree.set(this.tree.size() - 1, aggregator); // wpisanie na ostatnie miejsce węzła nie będącego liściem
        }
    }

    // tworzenie słowników
    private void fillDicts(Node root, String word) {
        if (root.getLeft() == null && root.getRight() == null) { // warunek brzegowy oparty na liściu
            this.encodingDict.put(root.getLetter(), word);
            this.decodingDict.put(word, root.getLetter());
        }
        if (root.getLeft() != null) {
            fillDicts(root.getLeft(), word + '0'); // konwencja - 0 na lewo, 1 na prawo
        }
        if (root.getRight() != null) { // w przypadku drzewa kodowego warunek zbędny, ale dodający czytelności
            fillDicts(root.getRight(), word + '1');
        }
    }

    // testowy main
    public static void main(String[] args) {
        Huffman huffman = new Huffman();
        try {
            System.out.println(huffman.encode("AABBCCDDDDDDDFFFEEER"));
        } catch (Exception e) {

        }
    }
}
