package com.sinse.wms.common.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * 클라이언트 소켓 핸들러 - 서버에 연결하여 메시지 송수신 담당
 */
import java.io.*;
import java.net.*;
import java.util.function.Consumer;

public class ChatClientSocket {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Consumer<String> onMessageReceived;

    public ChatClientSocket(String host, int port, Consumer<String> onMessageReceived) throws IOException {
        this.onMessageReceived = onMessageReceived;
        socket = new Socket(host, port);
        in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // 수신 쓰레드 시작
        new Thread(() -> {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    onMessageReceived.accept(message);
                }
            } catch (IOException e) {
                onMessageReceived.accept("⚠ 서버와의 연결 종료됨.");
            }
        }).start();
    }

    public void send(String message) {
        out.println(message);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ignored) {}
    }
}
