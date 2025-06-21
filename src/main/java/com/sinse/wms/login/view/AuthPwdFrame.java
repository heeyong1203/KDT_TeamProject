package com.sinse.wms.login.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sinse.wms.common.service.EmailSender;
import com.sinse.wms.common.service.MemberFinder;
import com.sinse.wms.common.util.AuthCodeManager;
import com.sinse.wms.common.view.button.OutLineButton;

public class AuthPwdFrame extends JFrame {
	JPanel p_center;

	JTextField t_email;
	JTextField t_name;
	JTextField t_id;
	JTextField t_verify;

	JButton bt_verify;
	JButton bt_sender;

	OutLineButton outLineButton;

	String name;
	String email;
	
	String id;

	public AuthPwdFrame() {
		p_center = new JPanel();
		p_center.setLayout(null);
		t_email = new JTextField();
		t_name = new JTextField();
		t_id = new JTextField();
		t_verify = new JTextField();

		bt_verify = new JButton("확인");
		bt_sender = new JButton("전송");

		// 창 설정
		setTitle("WMS 비밀번 찾기");
		setSize(600, 800);
		setLayout(null);
		setLocationRelativeTo(null);

		// 패널 설정
		p_center.setBackground(Color.white);
		p_center.setBounds(100, 50, 400, 600);
		p_center.setLayout(null);

		// 이름 입력
		t_name.setBounds(100, 50, 200, 45);
		t_name.setBorder(BorderFactory.createTitledBorder("NAME"));
		p_center.add(t_name);

		// 이메일 입력
		t_email.setBounds(100, 150, 200, 45);
		t_email.setBorder(BorderFactory.createTitledBorder("EMAIL"));
		p_center.add(t_email);

		// 아이디 입력
		t_id.setBounds(100, 250, 200, 45);
		t_id.setBorder(BorderFactory.createTitledBorder("ID"));
		p_center.add(t_id);

		// 인증번호 입력
		t_verify.setBounds(100, 350, 160, 45);
		t_verify.setBorder(BorderFactory.createTitledBorder("Verify Num"));
		p_center.add(t_verify);

		// 전송 버튼
		bt_sender.setBounds(270, 350, 80, 45);
		p_center.add(bt_sender);

		// 확인 버튼
		bt_verify.setBounds(150, 450, 100, 45);
		p_center.add(bt_verify);

		// 전송 버튼 이벤트
		bt_sender.addActionListener(e -> {
			name = t_name.getText().trim();
			email = t_email.getText().trim();
			id = t_id.getText().trim();

			if (name.isEmpty() || email.isEmpty() || id.isEmpty()) {
				JOptionPane.showMessageDialog(this, "이름, 이메일, 아이디를 모두 입력해주세요.");
				return;
			}

			String code = AuthCodeManager.generateCode();
			AuthCodeManager.saveCode(email, code);

			String subject = "[WMS] 인증번호 안내";
			String content = name + "님,\n\n요청하신 인증번호는 다음과 같습니다:\n\n" + code + "\n\n감사합니다.";

			try {
				EmailSender.send(email, subject, content);
				JOptionPane.showMessageDialog(this, "인증번호가 이메일로 전송되었습니다.");
				bt_sender.setText("재전송");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "이메일 전송 실패: " + ex.getMessage());
			}
		});

		// 확인 버튼 이벤트
		bt_verify.addActionListener(e -> {
			String email = t_email.getText().trim();
			String inputCode = t_verify.getText().trim();
			String resultPwd = MemberFinder.fineMemberPwd(name, id);
			
			if (!AuthCodeManager.verifyCode(email, inputCode)) {
		        JOptionPane.showMessageDialog(this, "인증번호가 일치하지 않습니다.");
		        return;
		    }
			if (resultPwd != null) {
				JOptionPane.showMessageDialog(this, "회원님의 비밀번호는 " + resultPwd + " 입니다.");
				 dispose();
			} else {
				JOptionPane.showMessageDialog(this, "입력하신 정보와 일치하는 회원을 찾을 수 없습니다.");
			}
		});

		add(p_center);
		setVisible(true);
	}

	public static void main(String[] args) {
		new AuthPwdFrame();
	}
}