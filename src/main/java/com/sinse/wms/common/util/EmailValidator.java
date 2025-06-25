package com.sinse.wms.common.util;

import java.util.regex.Pattern;

public class EmailValidator {
    //이메일 정규표현식
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    //이메일 정규표현식 패턴
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * 문자열을 정규표현식을 이용하여 이메일 형식에 맞는지 확인 가능한 메소드
     *
     * @param email 확인 하고자하는 문자열
     * @return 해당 문자열이 이메일 형식으로 작성 되었는지 여부
     */
    public static boolean isValidEmailRegex(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
}
