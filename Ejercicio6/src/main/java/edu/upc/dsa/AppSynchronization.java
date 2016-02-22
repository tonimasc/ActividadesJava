package edu.upc.dsa;

/**
 * Created by toni on 22/02/16.
 */
public class AppSynchronization {
    public static void main(String[] args) {
        String line = "En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y galgo corredor.\n";
        Buffer buffer = new Buffer();
        Thread producer = new Thread(new Producer(buffer, line), "producer");
        Thread consumer = new Thread(new Consumer(buffer), "consumer");

        consumer.start();
        producer.start();
    }
}
