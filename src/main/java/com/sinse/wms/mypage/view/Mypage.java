package com.sinse.wms.mypage.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.product.model.Member;

public class Mypage extends BaseContentPage {
	JPanel p_north; // 로그인 현황 조회
	JPanel p_south; // 그리드 패널을 적용할 예정
	JPanel south_first_north; // 첫번째 그리드 패널의 상단 패널
	JPanel south_first_south; // 첫번째 그리드 패널의 하단 패널
	JPanel south_second_center; //두번째 그리드 패널의 중간 패널
	
	JPanel p_top = new JPanel(); // 상단 인사말 영역
	JLabel la_profile_icon = new JLabel(); // 프로필 아이콘 이미지
	JLabel la_greeting = new JLabel();     // 인사 텍스트


	JLabel la_my_info; // 내 정보보기 라벨
	JLabel la_my_name; // 내 이름 라벨
	JLabel la_my_dept; // 내 부서 라벨
	JLabel la_my_num; // 내 사번 라벨
	JLabel la_my_id; // 내 아이디라벨
	JLabel la_my_last_access; // 내 마지막 접속 라벨

	JLabel la_user_name; // 접속한 유저의 이름
	JLabel la_user_dept; // 접속한 유저의 부서
	JLabel la_user_num; // 접속한 유저의 사번
	JLabel la_user_id; // 접속한 유저의 아이디
	JLabel la_user_ip; // 접속한 유저의 현재 아이피
	
	JLabel la_change_pwd; //비밀번호 변경하기 
	JLabel la_access_log; //접속 로그
	
	JButton bt_pwd; //pwd 변경 버튼

	Member m; //현재 로그인한 멤버 정보 불러오기
	public Mypage(Color color,Member m) {
		setBackgroundColor(color);
		this.m = m;

		// TODO("레이아웃 객체 생성 ~ 6/26")=========================
		Dimension p_north_size = new Dimension(800, 300);
		Dimension p_south_size = new Dimension(800, 400);
		Dimension la_size = new Dimension(50, 50);
		Dimension text_size = new Dimension(100, 50);
		Dimension titleText = new Dimension(150, 80);

		// ======= 객체 생성 ======
		//전체 패널 ==========
		p_north = new JPanel();
		p_south = new JPanel();
		// ===== 상단 인사 UI 설정 =====

		p_top.setPreferredSize(new Dimension(800, 350));

		p_top.setLayout(new BorderLayout());
		p_top.setBackground(Color.WHITE);
		p_top.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));

		// 프로필 이미지 (임시 원형)
		la_profile_icon.setPreferredSize(new Dimension(100, 100));
		URL imageUrl = getClass().getResource("/images/default_avatar_icon.png");
		la_profile_icon.setIcon(new ImageIcon(imageUrl)); // 또는 기본 아이콘
		la_profile_icon.setHorizontalAlignment(JLabel.CENTER);

		// 인사 텍스트
		String name = m != null ? m.getMember_name() : "사용자";
		la_greeting.setText("<html><div style='text-align: center;'><b>" + name + " 님<br>안녕하세요.</b></div></html>");
		la_greeting.setFont(new Font("SansSerif", Font.BOLD, 20));
		la_greeting.setForeground(new Color(36, 104, 160));
		la_greeting.setHorizontalAlignment(JLabel.CENTER);

		// 구성
		JPanel centerWrapper = new JPanel(new GridLayout(2, 1));
		centerWrapper.setOpaque(false);
		centerWrapper.add(la_profile_icon);
		centerWrapper.add(la_greeting);

		p_top.add(centerWrapper, BorderLayout.CENTER);
		add(p_top, BorderLayout.NORTH); // 기존 p_north와 위치 겹치지 않도록 위치 조절

		//그리드의 첫번째 패널 ============
		south_first_north = new JPanel();
		south_first_south = new JPanel();

		la_my_info = new JLabel("내 정보 보기");

		la_my_name = new JLabel("이름 : ");
		la_my_dept = new JLabel("부서 : ");
		la_my_num = new JLabel("사번 : ");
		la_my_id = new JLabel("ID : ");
		la_my_last_access = new JLabel("내 마지막 접속 : ");

		la_user_name = new JLabel("이름이 출력됩니다.");
		la_user_dept = new JLabel("부서가 출력됩니다.");
		la_user_num = new JLabel("사번이 출력됩니다.");
		la_user_id = new JLabel("아이디가 출력됩니다.");
		la_user_ip = new JLabel("아이피가 출력됩니다.");
		

		add(p_north, BorderLayout.NORTH);
		add(p_south, BorderLayout.CENTER);
		south_first_north.add(la_my_name);
		south_first_north.add(la_user_name);
		south_first_north.add(la_my_dept);
		south_first_north.add(la_user_dept);
		south_first_north.add(la_my_num);
		south_first_north.add(la_user_num);
		
		
		south_first_south.add(la_my_id);
		south_first_south.add(la_user_id);
		south_first_south.add(la_my_last_access);
		south_first_south.add(la_user_ip);

		p_north.setPreferredSize(p_north_size);
		p_south.setPreferredSize(p_south_size);
		south_first_north.setPreferredSize(new Dimension(200, 170));
		//south_first_north.setBorder(new LineBorder(Color.CYAN));
		
		south_first_south.setPreferredSize(new Dimension(200,150));
		//south_first_south.setBorder(new LineBorder(Color.CYAN));

		la_my_info.setPreferredSize(titleText);
		//la_my_info.setBorder(new LineBorder(Color.black));
		la_my_info.setFont(new Font("SansSerif", Font.BOLD, 23));
		la_my_info.setHorizontalAlignment(JLabel.CENTER);
		la_my_name.setPreferredSize(la_size);
		la_my_dept.setPreferredSize(la_size);
		la_my_num.setPreferredSize(la_size);
		la_my_id.setPreferredSize(la_size);
		la_user_name.setPreferredSize(text_size);
		la_user_dept.setPreferredSize(text_size);
		la_user_num.setPreferredSize(text_size);
		la_user_id.setPreferredSize(text_size);
		//첫번째 그리드 패널의 스타일 ==========끝!
		
		//두번째 그리드 패널의 스타일 시작!! =======
		la_change_pwd = new JLabel("비밀번호 변경");
		bt_pwd = new JButton("변경하기");
		south_second_center = new JPanel();
		
		south_second_center.setPreferredSize(new Dimension(200,150));
		south_second_center.setLayout(new FlowLayout());
		
		
		la_change_pwd.setPreferredSize(titleText);
		//la_change_pwd.setBorder(new LineBorder(Color.black));
		la_change_pwd.setFont(new Font("SansSerif", Font.BOLD, 23));
		la_change_pwd.setHorizontalAlignment(JLabel.CENTER);
		bt_pwd.setPreferredSize(new Dimension(100,50));
		bt_pwd.setVerticalAlignment(JButton.CENTER);
		
		
		south_second_center.add(bt_pwd);
		
		JLabel log1 = new JLabel("2025-06-25 09:15:33 로그인 성공");
		JLabel log2 = new JLabel("2025-06-24 22:04:18 로그인 성공");
		JLabel log3 = new JLabel("2025-06-24 08:49:05 로그인 실패");

		

		//두번째 그리드 패널의 스타일 ========끝!
		
		//세번째 그리드 패널의 스타일 시작
		la_access_log = new JLabel("접속 로그");
		la_access_log.setPreferredSize(titleText);
		//la_access_log.setBorder(new LineBorder(Color.black));
		la_access_log.setFont(new Font("SansSerif", Font.BOLD, 23));
		la_access_log.setHorizontalAlignment(JLabel.CENTER);
		JPanel logPanel = new JPanel();
		logPanel.setLayout(new GridLayout(3, 1));
		logPanel.add(log1);
		logPanel.add(log2);
		logPanel.add(log3);
		
		
		//세번째 그리드 패널의 스타일 끝
		

		// 3분할 그리드 설정
		p_south.setLayout(new GridLayout(1, 3)); // 1행 3열

		// 각 셀에 들어갈 패널 생성 + 경계선 추가
		for (int i = 0; i < 3; i++) {
			JPanel cell = new JPanel();
			cell.setBorder(new LineBorder(Color.GRAY)); // 경계선 추가
			p_south.add(cell);

			if (i == 0) {
				cell.add(la_my_info);
				cell.add(south_first_north);
				cell.add(south_first_south);
			}
			else if(i==1) {
				
				cell.add(la_change_pwd);
				cell.add(south_second_center);
				
			}
			else if(i == 2) {
				cell.setLayout(new BorderLayout());
			    cell.add(la_access_log, BorderLayout.NORTH);
			    cell.add(logPanel, BorderLayout.CENTER);

			}
		}

		// p_north 경계선 그리기
		p_north.setBorder(new LineBorder(Color.GRAY));
		p_north.add(p_top);
		
		
		setVisible(true);
		setSize(new Dimension(1200, 1000));

		if (m != null) {
		    la_user_name.setText(m.getMember_name());
		    la_user_dept.setText(m.getDept().getDept_name());
		    la_user_num.setText(String.valueOf(m.getMember_id()));
		    la_user_id.setText(m.getMember_email());
		    la_user_ip.setText(getLocalIp() + "  (KST) " + java.time.LocalDate.now());

		}

		// 레이아웃 생성 완료 ========================================
		
		bt_pwd.addActionListener(e -> {
			ChangePassword();
		});
	}
	
	public void ChangePassword() {
	    new ChangePasswordDialog((JFrame) SwingUtilities.getWindowAncestor(this), m).setVisible(true);
	}

	
	private String getLocalIp() {
	    try {
	        return java.net.InetAddress.getLocalHost().getHostAddress();
	    } catch (Exception e) {
	        return "Unknown IP";
	    }
	}


}