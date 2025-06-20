package com.sinse.wms.login.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.sinse.wms.product.model.Member;
import com.sinse.wms.product.repository.MemberDAO;

public class LoginLayout extends JFrame {

	JPanel p_center;
	JLabel la_welcome;
	JLabel la_welcome_text;
	JTextField t_id;
	JPasswordField t_pwd;
	JLabel la_find_id;
	JLabel la_seperator2;
	JLabel la_find_pw;
	JButton bt_login;
	JSeparator la_seperator;

	AuthIDFrame authIDFrame;
	AuthPwdFrame authPwdFrame;
	
	String id;
	String pwd;

	public LoginLayout() {
		// 프레임 설정
		setTitle("WMS 로그인");
		setSize(1440, 960);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 중앙 패널 설정
		p_center = new JPanel();
		p_center.setLayout(null); // 절대 위치
		p_center.setBounds(220, 150, 1000, 600); // 화면 중앙에 적절히 배치
		p_center.setBackground(Color.WHITE);
		add(p_center);

		// Welcome 텍스트
		la_welcome = new JLabel("Welcome Back !!");
		la_welcome.setFont(new Font("Arial", Font.BOLD, 35));
		la_welcome.setBounds(370, 100, 300, 30);
		p_center.add(la_welcome);

		// 서브 텍스트
		la_welcome_text = new JLabel("오늘도 안정적인 물류 운영을 도와드릴게요.");
		la_welcome_text.setBounds(390, 155, 350, 20);
		p_center.add(la_welcome_text);

		la_seperator = new JSeparator();
		la_seperator.setBounds(400, 180, 180, 3);
		la_seperator.setForeground(Color.black);
		p_center.add(la_seperator);

		// ID 필드
		t_id = new JTextField();
		t_id.setBounds(400, 220, 200, 45);
		t_id.setBorder(BorderFactory.createTitledBorder("ID"));
		p_center.add(t_id);

		// PW 필드
		t_pwd = new JPasswordField();
		t_pwd.setBounds(400, 280, 200, 45);
		t_pwd.setBorder(BorderFactory.createTitledBorder("PASSWORD"));
		p_center.add(t_pwd);

		// 아이디/비밀번호 찾기
		la_find_id = new JLabel("아이디 찾기");
		la_find_pw = new JLabel("비밀번호 찾기");
		la_seperator2 = new JLabel("|");
		la_seperator2.setBounds(500, 400, 50, 20);
		la_find_id.setFont(new Font("돋움", Font.PLAIN, 12));
		la_find_pw.setFont(new Font("돋움", Font.PLAIN, 12));
		la_find_id.setBounds(380, 400, 100, 20);
		la_find_pw.setBounds(560, 400, 120, 20);

		p_center.add(la_find_id);
		p_center.add(la_find_pw);
		p_center.add(la_seperator2);

		bt_login = new JButton("SIGN IN");
		bt_login.setBounds(440, 480, 120, 40);
		bt_login.setOpaque(true);
		bt_login.setContentAreaFilled(true);
		bt_login.setBorderPainted(false);
		bt_login.setForeground(Color.WHITE);
		bt_login.setBackground(new Color(66, 133, 244));
		p_center.add(bt_login);

		// 배경색 설정
		getContentPane().setBackground(new Color(245, 247, 252));
		setVisible(true);

		id = null;
		pwd = null;

		// ==================Action 추가================
		bt_login.addActionListener((e) -> {
			id = t_id.getText();
			pwd = new String(t_pwd.getPassword());
			if (id == null) {
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요.");
			} else if (pwd == null) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요.");
			} else {
				check();
			}
		});
		
		la_find_id.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				authIDFrame = new AuthIDFrame();
			}
		});
		la_find_pw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				authPwdFrame = new AuthPwdFrame();
			}
		});

		// 프레임 상에서 ente를 누를 시 로그인 시도
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					id = t_id.getText();
					pwd = new String(t_pwd.getPassword());

					if (id == null || id.trim().isEmpty()) {
						JOptionPane.showMessageDialog(LoginLayout.this, "아이디를 입력해주세요.");
					} else if (pwd == null || pwd.trim().isEmpty()) {
						JOptionPane.showMessageDialog(LoginLayout.this, "비밀번호를 입력해주세요.");
					} else {
						check(); // 로그인 시도
					}
				}
			}
		});

	}

	public void check() {
		id = t_id.getText();

		pwd = new String(t_pwd.getPassword());

		MemberDAO dao = new MemberDAO();
		Member m = dao.selectToLogin(id, pwd);

		if (m != null) {
			JOptionPane.showMessageDialog(this, m.getMember_name() + "님 환영합니다!");
			this.dispose(); // 로그인 창 닫기

			// 여기서 App.main() 실행!
			com.sinse.wms.App.main(null);
		} else {
			JOptionPane.showMessageDialog(this, "로그인 실패!");
		}
	}



	public static void main(String[] args) {
		new LoginLayout();
	}
}
