package com.sinse.wms.common.util;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * 인증번호를 생성하고 저장하는 유틸 클래스
 */
public class AuthCodeManager {

    private static final int CODE_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();
    private static final Map<String, String> authCodeMap = new HashMap<>();

    /**
     * 인증번호 생성
     * @return 6자리 숫자 문자열
     */
    public static String generateCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(random.nextInt(10)); // 0~9 숫자
        }
        return sb.toString();
    }

    /**
     * 인증번호 저장
     * @param email 이메일 주소
     * @param code 생성된 인증번호
     */
    public static void saveCode(String email, String code) {
        authCodeMap.put(email, code);
    }

    /**
     * 인증번호 검증
     * @param email 이메일 주소
     * @param inputCode 사용자가 입력한 코드
     * @return 일치 여부
     */
    public static boolean verifyCode(String email, String inputCode) {
        String savedCode = authCodeMap.get(email);
        return savedCode != null && savedCode.equals(inputCode);
    }

    /**
     * 인증번호 삭제 (검증 완료 후)
     * @param email 이메일 주소
     */
    public static void removeCode(String email) {
        authCodeMap.remove(email);
    }
}
