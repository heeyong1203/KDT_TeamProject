package com.sinse.wms.common.view;

import com.sinse.wms.common.view.content.BaseContentPage;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Chat extends BaseContentPage {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private ChatClientSocket socket;

    public Chat() {
        this.setLayout(new BorderLayout());

        // 채팅 표시 영역
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // 입력창 + 버튼 영역
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("보내기");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // 패널 배치
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(inputPanel, BorderLayout.SOUTH);

        // 이벤트 연결
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        // 소켓 연결
        try {
            socket = new ChatClientSocket("localhost", 9999, this::receiveMessage);
            receiveMessage("✅ 서버에 연결되었습니다.");
        } catch (IOException e) {
            receiveMessage("❌ 서버 연결 실패: " + e.getMessage());
        }
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            socket.send( text); // 서버로 전송
            inputField.setText("");
        }
    }

    private void receiveMessage(String msg) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(msg + "\n");
        });
    }
}
