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
public class RunnableClass implements Runnable {
    long lastExecution = 0;
    int counter = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            long currentExecution = System.currentTimeMillis();
            long elapsed = (lastExecution == 0) ? 0 : currentExecution - lastExecution;
            lastExecution = currentExecution;
            System.out.println(Thread.currentThread().getName() + " elapsed = " + elapsed + " ms. [" + (++counter)+"]");
            long sleep = (long) (Math.random() * 2000);
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
