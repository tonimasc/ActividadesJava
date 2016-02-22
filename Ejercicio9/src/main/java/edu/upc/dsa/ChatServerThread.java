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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by toni on 22/02/16.
 */
public class ChatServerThread implements Runnable {
    private static List<ChatServerThread> threadList = Collections.synchronizedList(new ArrayList<ChatServerThread>());

    private Socket socket = null;
    private String username = null;
    private BufferedReader reader = null;
    private PrintWriter writer = null;

    public ChatServerThread(Socket socket) throws IOException {
        this.socket = socket;

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            String[] msg = null;
            do {
                msg = reader.readLine().split(" ", 2);
                if (msg[0].equals("JOIN")) {
                    username = msg[1];
                    Thread.currentThread().setName(username + " thread");
                    threadList.add(this);
                    broadcast("estoy dentro.");
                } else if (msg[0].equals("MESSAGE")) {
                    broadcast(msg[1]);
                }
            } while (!msg[0].equals("LEAVE"));

            threadList.remove(this);
            broadcast("me piro.");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcast(String msg) {
        for (ChatServerThread t : threadList)
            t.send(username + "> " + msg);
    }

    private void send(String msg) {
        writer.println(msg);
        writer.flush();
    }
}