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

/**
 * Created by toni on 22/02/16.
 */
public class ChatClient implements Runnable {
    private class ReaderThread implements Runnable {
        BufferedReader reader = null;

        public ReaderThread(BufferedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            String msg = null;
            try {
                while ((msg = reader.readLine()) != null)
                    System.out.println(msg);
            } catch (IOException e) {
                if (!socket.isClosed())
                    e.printStackTrace();
            }
        }
    }

    private String server;
    private int port;
    private Socket socket = null;
    private BufferedReader reader = null;
    private PrintWriter writer = null;

    public ChatClient(String server) {
        this(server, ChatServer.DEFAULT_PORT);
    }

    public ChatClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter your username: ");
            String username = reader.readLine();
            join(username);

            System.out.println("start chat");
            String msg = null;
            while ((msg = reader.readLine()).length() != 0) {
                send(msg);
            }
            leave();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void join(String username) throws IOException {
        socket = new Socket(server, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        (new Thread((new ReaderThread(reader)))).start();
        writer = new PrintWriter(socket.getOutputStream());

        writer.println("JOIN " + username);
        writer.flush();
    }

    private void leave() throws IOException {
        writer.println("LEAVE");
        writer.flush();

        socket.close();
    }

    private void send(String msg) {
        writer.println("MESSAGE " + msg);
        writer.flush();
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("You have to pass the server name and the server port");
            System.exit(-1);
        }
        String server = args[0];
        int port = Integer.parseInt(args[1]);
        ChatClient client = new ChatClient(server, port);
        (new Thread(client)).start();
    }
}
