package edu.upc.dsa;

/**
 * Created by toni on 18/02/16.
 */
public class Tree {
    private String classe;
    private int altura;

    public Tree(String classe) {
        this.classe = classe;
    }

    public Tree(int altura) {
        this.altura = altura;
    }

    public Tree(String classe, int altura) {
        this.classe = classe;
        this.altura = altura;
    }
    public Tree() {
        this (null,0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Un ");
        if (classe == null)
            sb.append("árbol");
        else
            sb.append(classe);
        if (altura > 0)
            sb.append(" de ").append(altura).append(" metros");
        return sb.toString();
    }


}
