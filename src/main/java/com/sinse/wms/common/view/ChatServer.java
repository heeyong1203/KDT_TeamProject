package com.sinse.wms.common.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 채팅 서버 - 모든 클라이언트로부터 받은 메시지를 다른 모든 클라이언트에게 브로드캐스트
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 9999;

    // 모든 클라이언트의 출력 스트림 저장
    private static Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("📡 채팅 서버 시작됨 (포트 " + PORT + ")");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("✅ 클라이언트 연결됨: " + socket.getInetAddress());
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 각 클라이언트 별 메시지 처리 쓰레드
    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                clientWriters.add(out);

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("📨 수신: " + message);
                    for (PrintWriter writer : clientWriters) {
                        writer.println(message); // 브로드캐스트
                    }
                }
            } catch (IOException e) {
                System.out.println("⚠ 연결 종료: " + socket.getInetAddress());
            } finally {
                try { socket.close(); } catch (IOException ignored) {}
                clientWriters.remove(out);
            }
        }
    }
}
