package org.hoofman;

public class Node implements Comparable<Node> {
    private int numericalValue; // wartość oznaczająca liczbę wystąpień węzła
    private char letter = '\0'; // domyślnie znak zerowy, bo nie wszystkie węzły mają przypisaną literę
    private Node left = null; // hipotetyczne lewe dziecko
    private Node right = null; // hipotetyczne prawe dziecko

    public Node(int numericalValue, char letter, Node left, Node right) {
        this.numericalValue = numericalValue;
        this.letter = letter;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Node o) throws NullPointerException {
        if (o == null) {
            throw new NullPointerException("");
        }
        return o.numericalValue - this.numericalValue; // jeśli dodatnie, to obiekt jest większy, najpierw największe
    }
}
