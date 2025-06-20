package com.sinse.wms.common.service;

import com.sinse.wms.common.util.AuthCodeManager;

public class AuthService {

    /**
     * 인증메일 발송 서비스
     * @param email 사용자 이메일
     */
    public static void sendVerificationCode(String email) {
        String code = AuthCodeManager.generateCode();
        AuthCodeManager.saveCode(email, code);

        String subject = "[WMS] 인증번호 발송 안내";
        String content = "안녕하세요.\n인증번호는 다음과 같습니다:\n\n" + code + "\n\n감사합니다.";

        try {
            EmailSender.send(email, subject, content);
            System.out.println("인증번호 전송 완료");
        } catch (Exception e) {
            System.err.println("이메일 전송 실패: " + e.getMessage());
        }
    }
}
