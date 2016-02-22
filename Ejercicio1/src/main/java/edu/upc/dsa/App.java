package edu.upc.dsa;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Tree[] arboles = new Tree[4];
        arboles[0] = new Tree(4);
        arboles[1] = new Tree("Roble");
        arboles[2] = new Tree();
        arboles[3] = new Tree("Pino", 5 );
        for (Tree tree : arboles)
            System.out.println(tree);
    }

}