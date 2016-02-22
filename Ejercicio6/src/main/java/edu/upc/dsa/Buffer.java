/*
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Sergio Machado
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.upc.dsa;

/**
 * Created by toni on 22/02/16.
 */
public class Buffer {
    private final static int BUFFER_SIZE = 32;
    private char[] buffer;
    private int head;
    private int tail;

    public Buffer() {
        buffer = new char[BUFFER_SIZE];
        this.head = 0;
        this.tail = 0;
    }

    public synchronized char get() {
        while (isEmpty())
            try {
                wait();
            } catch (InterruptedException e) {
            }
        char c = buffer[head++];
        if (head == buffer.length) {
            head = 0;
        }

        notifyAll();
        return c;
    }

    public synchronized void put(char c) {
        while (isFull())
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        buffer[tail++] = c;
        if (tail == buffer.length) {
            tail = 0;
        }
        notifyAll();
    }

    private boolean isEmpty() {
        return head == tail;
    }

    private boolean isFull() {
        if (tail + 1 == head) {
            return true;
        }
        if (tail == (buffer.length - 1) && head == 0) {
            return true;
        }
        return false;
    }
}
