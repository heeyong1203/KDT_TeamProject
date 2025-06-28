package com.sinse.wms;


import com.sinse.wms.common.view.ChatServer;
import com.sinse.wms.common.view.Main;
import com.sinse.wms.product.model.Member; // ChatServer가 있는 위치에 맞게 import 경로 수정

import javax.swing.*;

public class App {

    // 서버 실행 여부 체크 변수 (중복 실행 방지)
    private static boolean serverStarted = false;

    /**
     * 테스트용 진입점 (사용자 없이 Main 실행)
     */
    public static void main(String[] args) {
        startChatServer();    // ✅ 서버 자동 실행
        SwingUtilities.invokeLater(() -> {
            new Main(null);       // 테스트 실행 (Member 없음)
        });
    }

    /**
     * 로그인 후 실행 진입점
     * @param m 로그인된 사용자 정보
     */
    public static void startWithUser(Member m) {
        startChatServer();    // ✅ 서버 자동 실행
        new Main(m);          // 로그인된 Member 넘겨줌
    }

    /**
     * 💬 채팅 서버를 백그라운드에서 1회만 실행하는 메서드
     */
    private static void startChatServer() {
        if (serverStarted) return; // 이미 실행 중이면 무시

        new Thread(() -> {
            try {
                System.out.println("💬 Chat Server Starting...");
                ChatServer.main(null); // 콘솔용 서버 실행
            } catch (Exception e) {
                System.out.println("⚠ 채팅 서버 실행 실패: " + e.getMessage());
            }
        }).start();

        serverStarted = true;
    }
}
