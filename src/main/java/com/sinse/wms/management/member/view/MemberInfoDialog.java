package com.sinse.wms.management.member.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.exception.MemberUpdateException;
import com.sinse.wms.common.util.EmailValidator;
import com.sinse.wms.common.view.button.FillButton;
import com.sinse.wms.product.model.Auth;
import com.sinse.wms.product.model.Dept;
import com.sinse.wms.product.model.JobGrade;
import com.sinse.wms.product.model.Member;
import com.sinse.wms.product.repository.MemberDAO;

public class MemberInfoDialog extends JDialog {
	private JPanel p_wrapper;
	private JLabel la_title;
	private JLabel la_name;
	private JLabel la_dept;
	private JLabel la_auth;
	private JLabel la_job_grade;
	private JLabel la_email;
	private JLabel la_pwd;
	private JLabel la_hire_day;
	private JTextField tf_name;
	private JComboBox<String> cb_dept;
	private JComboBox<String> cb_auth;
	private JComboBox<String> cb_job_grade;
	private JTextField tf_email;
	private JPasswordField tf_pwd;
	private JTextField tf_hire_day;
	private JSeparator separator;
	private FillButton bt_submit;

	private List<Dept> depts = null;
	private List<Auth> auths = null;
	private List<JobGrade> jobGrades = null;
	private Member member = null;

	private MemberInfoDialogListener listener = null;
	private MemberDAO memberDAO = new MemberDAO();

	public MemberInfoDialog(Frame parent, MemberInfoDialogListener listener) {
		super(parent, true);
		setLayout(new FlowLayout());
		setSize(700, 780);
		setBackground(Color.WHITE);
		setLocationRelativeTo(null);
		this.listener = listener;

		Dimension title_size = new Dimension(600, 30);
		Dimension la_size = new Dimension(100, 30);
		Dimension tf_size = new Dimension(400, 30);
		Dimension bt_size = new Dimension(100, 50);
		this.p_wrapper = new JPanel() {
			@Override
			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.WHITE);
				g2.fillRoundRect(25, 25, 700 - 25 * 2, 780 - 50 * 2, 70, 70);
				g2.dispose();
				super.paintComponents(g);
			}
		};
		this.p_wrapper.setPreferredSize(new Dimension(700, 780));
		this.p_wrapper.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		this.la_title = new JLabel();
		this.la_title.setHorizontalAlignment(SwingConstants.CENTER);
		this.la_title.setPreferredSize(title_size);
		this.la_title.setFont(new Font("Default", Font.BOLD, 32));
		this.la_name = new JLabel("이름");
		this.la_name.setPreferredSize(la_size);
		this.la_dept = new JLabel("부서");
		this.la_dept.setPreferredSize(la_size);
		this.la_auth = new JLabel("권한");
		this.la_auth.setPreferredSize(la_size);
		this.la_job_grade = new JLabel("직급");
		this.la_job_grade.setPreferredSize(la_size);
		this.la_email = new JLabel("이메일");
		this.la_email.setPreferredSize(la_size);
		this.la_pwd = new JLabel("패스워드");
		this.la_pwd.setPreferredSize(la_size);
		this.la_hire_day = new JLabel("입사일");
		this.la_hire_day.setPreferredSize(la_size);
		this.tf_name = new JTextField();
		this.tf_name.setPreferredSize(tf_size);
		this.cb_dept = new JComboBox<String>();
		this.cb_dept.setPreferredSize(tf_size);
		this.cb_dept.setBackground(getBackground());
		this.cb_auth = new JComboBox<String>();
		this.cb_auth.setPreferredSize(tf_size);
		this.cb_auth.setBackground(getBackground());
		this.cb_job_grade = new JComboBox<String>();
		this.cb_job_grade.setPreferredSize(tf_size);
		this.cb_job_grade.setBackground(getBackground());
		this.tf_email = new JTextField();
		this.tf_email.setPreferredSize(tf_size);
		this.tf_pwd = new JPasswordField();
		this.tf_pwd.setPreferredSize(tf_size);
		this.tf_hire_day = new JTextField();
		this.tf_hire_day.setPreferredSize(tf_size);
		this.separator = new JSeparator(SwingConstants.HORIZONTAL);
		this.separator.setPreferredSize(title_size);
		this.bt_submit = new FillButton("", 15, Config.PRIMARY_COLOR);
		this.bt_submit.setFont(new Font("Default", Font.PLAIN, 20));
		this.bt_submit.setForeground(Color.WHITE);
		this.bt_submit.setPreferredSize(bt_size);
		this.bt_submit.addActionListener(e -> {
			if (this.member == null) {
				insertMember();
			}else{
				updateMember();
			}
		});

		p_wrapper.add(la_title);
		p_wrapper.add(separator);
		p_wrapper.add(la_name);
		p_wrapper.add(tf_name);
		p_wrapper.add(la_dept);
		p_wrapper.add(cb_dept);
		p_wrapper.add(la_auth);
		p_wrapper.add(cb_auth);
		p_wrapper.add(la_job_grade);
		p_wrapper.add(cb_job_grade);
		p_wrapper.add(la_email);
		p_wrapper.add(tf_email);
		p_wrapper.add(la_pwd);
		p_wrapper.add(tf_pwd);
		p_wrapper.add(la_hire_day);
		p_wrapper.add(tf_hire_day);
		p_wrapper.add(bt_submit);
		add(p_wrapper);
		initView();
	}

	public void initView() {
		setTitle();
		setButton();
		setInputHireDay();
	}

	private void setTitle() {
		String title = "";
		if (this.member == null) {
			title = "등록하기";
		} else {
			title = "수정하기";
		}
		this.setTitle(title);
		this.la_title.setText(title);
	}

	private void setButton() {
		String button_text = "";
		if(this.member == null){
			button_text = "등록";
		} else {
			button_text = "수정";
		}
		this.bt_submit.setText(button_text);
	}

	private void setInputHireDay() {
		if(this.member == null){
			la_hire_day.setVisible(false);
			tf_hire_day.setVisible(false);
			tf_email.setEditable(true);
		} else {
			la_hire_day.setVisible(true);
			tf_hire_day.setVisible(true);
			tf_hire_day.setEditable(false);
			tf_email.setEditable(false);
		}
		revalidate();
		repaint();
	}

	private void insertMember() {
		List<String> invalids = getInvailds();
		if (!invalids.isEmpty()) {
			String script = String.join(",", invalids) + "입력을 확인해주세요";
			JOptionPane.showMessageDialog(this, script);
			return;
		}

		String name = this.tf_name.getText().trim();
		String email = this.tf_email.getText().trim();
		String password = String.valueOf(this.tf_pwd.getPassword()).trim();
		Dept dept = getSelectedDept();
		Auth auth = getSelectedAuth();
		JobGrade jobGrade = getSelectedJobGrade();

		Member insertMember = new Member();
		insertMember.setMember_name(name);
		insertMember.setMember_email(email);
		insertMember.setMember_password(password);
		insertMember.setDept(dept);
		insertMember.setAuth(auth);
		insertMember.setJobGrade(jobGrade);

		try {
			memberDAO.insert(insertMember);
			JOptionPane.showMessageDialog(this, "등록에 성공하였습니다.");
			if (this.listener != null) {
				listener.onRegistration();
			}
			this.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "등록에 실패하였습니다.");
		}
	}

	private void updateMember() {
		List<String> invalids = getInvailds();
		if (!invalids.isEmpty()) {
			String script = String.join(",", invalids) + " 를 확인해주세요";
			JOptionPane.showMessageDialog(this, script);
			return;
		}

		String name = this.tf_name.getText().trim();
		String email = this.tf_email.getText().trim();
		String password = String.valueOf(this.tf_pwd.getPassword()).trim();
		Dept dept = getSelectedDept();
		Auth auth = getSelectedAuth();
		JobGrade jobGrade = getSelectedJobGrade();

		Member updateMember = this.member;
		updateMember.setMember_name(name);
		updateMember.setMember_email(email);
		updateMember.setMember_password(password);
		updateMember.setDept(dept);
		updateMember.setAuth(auth);
		updateMember.setJobGrade(jobGrade);

		try {
			this.memberDAO.update(updateMember);
			JOptionPane.showMessageDialog(this, "수정에 성공하였습니다.");
			if (this.listener != null) {
				listener.onModified();
			}
			this.dispose();
		} catch (MemberUpdateException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "수정에 실패하였습니다.");
		}
	}

	private Dept getSelectedDept() {
		return this.depts.get(this.cb_dept.getSelectedIndex()-1);
	}
	
	private Auth getSelectedAuth() {
		return this.auths.get(this.cb_auth.getSelectedIndex()-1);
	}

	private JobGrade getSelectedJobGrade() {
		return this.jobGrades.get(this.cb_job_grade.getSelectedIndex()-1);
	}

	private List<String> getInvailds() {
		List<String> invaild = new ArrayList<>();
		if (this.tf_name.getText().trim().length() == 0) {
			invaild.add("이름");
		}

		if (!EmailValidator.isValidEmailRegex(this.tf_email.getText())) {
			invaild.add("이메일형식");
		}

		if (this.member == null && String.valueOf(this.tf_pwd.getPassword()).trim().length() == 0) {
			invaild.add("비밀번호");
		}

		if (this.cb_dept.getSelectedIndex() == 0) {
			invaild.add("부서");
		}

		if (this.cb_auth.getSelectedIndex() == 0) {
			invaild.add("권한");
		}
		
		if(this.cb_job_grade.getSelectedIndex() == 0) {
			invaild.add("직급");
		}

		return invaild;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
		String[] deptNames = Stream.concat(Stream.of("부서를 선택해주세요"), depts.stream().map(d -> d.getDept_name()))
				.toArray(String[]::new);
		this.cb_dept.setModel(new DefaultComboBoxModel<>(deptNames));
	}

	public void setAuth(List<Auth> auths) {
		this.auths = auths;
		String[] authNames = Stream.concat(Stream.of("권한을 선택해주세요"), auths.stream().map(a -> a.getAuth_name()))
				.toArray(String[]::new);
		this.cb_auth.setModel(new DefaultComboBoxModel<>(authNames));
	}

	public void setJobGrades(List<JobGrade> jobGrades) {
		this.jobGrades = jobGrades;
		String[] jobGradeNames = Stream.concat(Stream.of("직급을 선택해주세요"), jobGrades.stream().map(a -> a.getJobGradeName()))
				.toArray(String[]::new);
		this.cb_job_grade.setModel(new DefaultComboBoxModel<>(jobGradeNames));
	}

	public void setMember(Member member) {
		this.member = member;
		onChangeMember();
	}

	private void onChangeMember() {
		if (this.member != null) {
			this.tf_name.setText(this.member.getMember_name());
			for (int i = 0; i < this.cb_dept.getItemCount(); i++) {
				if (this.cb_dept.getItemAt(i).equals(this.member.getDept().getDept_name())) {
					this.cb_dept.setSelectedIndex(i);
					break;
				}
			}
			for (int i = 0; i < this.cb_auth.getItemCount(); i++) {
				if (this.cb_auth.getItemAt(i).equals(this.member.getAuth().getAuth_name())) {
					this.cb_auth.setSelectedIndex(i);
					break;
				}
			}

			for (int i = 0; i < this.cb_job_grade.getItemCount(); i++) {
				if (this.cb_job_grade.getItemAt(i).equals(this.member.getJobGrade().getJobGradeName())) {
					this.cb_job_grade.setSelectedIndex(i);
					break;
				}
			}
			this.tf_email.setText(this.member.getMember_email());
			this.tf_hire_day.setText(this.member.getMemberhiredate().toString());
		} else {
			this.tf_name.setText(null);
			this.cb_dept.setSelectedIndex(0);
			this.cb_auth.setSelectedIndex(0);
			this.cb_job_grade.setSelectedIndex(0);
			this.tf_email.setText(null);
			this.tf_hire_day.setText(null);
		}
	}

	public interface MemberInfoDialogListener {
		void onRegistration();

		void onModified();
	}
}
