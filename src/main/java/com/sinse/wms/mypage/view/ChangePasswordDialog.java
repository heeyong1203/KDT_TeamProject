package com.sinse.wms.mypage.view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.sinse.wms.product.model.Member;
import com.sinse.wms.product.repository.MemberDAO;

public class ChangePasswordDialog extends JDialog {
	Member m;

	public ChangePasswordDialog(JFrame parent, Member m) {
        super(parent, "비밀번호 변경", true);
        this.m = m;

        setLayout(new GridLayout(4, 2, 10, 10));
        setSize(400, 250);
        setLocationRelativeTo(parent);


        // ====== UI 컴포넌트 생성 ======

        JLabel currentLabel = new JLabel("현재 비밀번호:");
        JLabel newLabel = new JLabel("새 비밀번호:");
        JLabel confirmLabel = new JLabel("비밀번호 확인:");

        JPasswordField currentPwd = new JPasswordField();
        JPasswordField newPwd = new JPasswordField();
        JPasswordField confirmPwd = new JPasswordField();

        JButton btChange = new JButton("변경");

        add(currentLabel); add(currentPwd);
        add(newLabel); add(newPwd);
        add(confirmLabel); add(confirmPwd);
        add(new JLabel()); add(btChange);


        // ====== 버튼 이벤트 ======
        btChange.addActionListener(e -> {
            String current = new String(currentPwd.getPassword());
            String newPass = new String(newPwd.getPassword());
            String confirm = new String(confirmPwd.getPassword());

            if (!newPass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "새 비밀번호가 일치하지 않습니다.");
                return;
            }

            MemberDAO dao = new MemberDAO();

            if (!dao.checkPassword(m.getMember_id(), current)) {
                JOptionPane.showMessageDialog(this, "현재 비밀번호가 일치하지 않습니다.");
                return;
            }

            boolean updated = dao.updatePassword(m.getMember_id(), newPass);
            if (updated) {
                JOptionPane.showMessageDialog(this, "비밀번호가 성공적으로 변경되었습니다.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "비밀번호 변경에 실패했습니다.");
            }
        });

	}

}
