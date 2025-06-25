package com.sinse.wms;

import com.sinse.wms.common.view.Main;
import com.sinse.wms.product.model.Member;

public class App {
	// 테스트용 진입
	public static void main(String[] args) {
		new Main(null); // 테스트용 실행
	}

	// 로그인된 사용자 정보를 받는 별도 메서드
	public static void startWithUser(Member m) {
		new Main(m); // 로그인된 Member 넘겨줌
	}
}
