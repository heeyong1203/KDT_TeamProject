package com.sinse.wms.membermanagement.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.exception.MemberDeleteException;
import com.sinse.wms.common.view.button.OutLineButton;
import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.product.model.Auth;
import com.sinse.wms.product.model.Dept;
import com.sinse.wms.product.model.JobGrade;
import com.sinse.wms.product.model.Member;
import com.sinse.wms.product.repository.AuthDAO;
import com.sinse.wms.product.repository.DeptDAO;
import com.sinse.wms.product.repository.JobGradeDAO;
import com.sinse.wms.product.repository.MemberDAO;

public class MemberManagementPage extends BaseContentPage implements MemberInfoDialog.MemberInfoDialogListener {
	private JPanel p_top_wrapper;
	private JPanel p_top_info_wrapper;
	private JPanel p_top_button_wrapper;
	private JPanel p_top_search_wrapper;
	private JScrollPane sp_table_scroll;
	private JTable tb_member;

	private JLabel la_title;
	private JLabel la_user;
	private JLabel la_user_count;
	private JLabel la_deactivated;
	private OutLineButton obt_add_user;
	private OutLineButton obt_modify_user;
	private OutLineButton obt_delete_user;
	private OutLineButton obt_search_user;
	private JTextField tf_search_user;

	private String userCountFormat = "%d명";
	private String deactivatedFormat = "(휴면유저 : %d)";
	private String deleteUserFormat = "\"%s\" 유저를 정말 삭제 하시겠습니까?";

	private MemberInfoDialog member_info_dialog;

	private MemberDAO memberDAO = new MemberDAO();
	private DeptDAO deptDAO = new DeptDAO();
	private AuthDAO authDAO = new AuthDAO();
	private JobGradeDAO jobGradeDAO = new JobGradeDAO();

	private List<Member> members = new ArrayList<>();
	private Member selectedMember = null;

	public MemberManagementPage() {
		// 초기화
		setLayout(new BorderLayout());
		this.p_top_wrapper = new JPanel();
		this.p_top_wrapper.setLayout(new BoxLayout(p_top_wrapper, BoxLayout.Y_AXIS));
		this.p_top_wrapper.setPreferredSize(new Dimension(0, 160));
		this.p_top_wrapper.setBackground(Color.WHITE);

		this.p_top_info_wrapper = new JPanel();
		this.p_top_info_wrapper.setLayout(new BoxLayout(p_top_info_wrapper, BoxLayout.X_AXIS));
		this.p_top_info_wrapper.setBackground(Color.WHITE);

		this.p_top_button_wrapper = new JPanel();
		this.p_top_button_wrapper.setLayout(new BoxLayout(p_top_button_wrapper, BoxLayout.X_AXIS));
		this.p_top_button_wrapper.setBackground(Color.WHITE);

		this.p_top_search_wrapper = new JPanel();
		this.p_top_search_wrapper.setLayout(new BoxLayout(p_top_search_wrapper, BoxLayout.X_AXIS));
		this.p_top_search_wrapper.setBackground(Color.WHITE);

		this.la_title = new JLabel("사용자 관리");
		this.la_title.setFont(new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 25));

		this.la_user = new JLabel("사용자");
		this.la_user_count = new JLabel("조회중...");
		this.la_user_count.setFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 12));

		this.la_deactivated = new JLabel("조회중...");
		this.la_deactivated.setForeground(new Color(121, 121, 121));
		this.la_deactivated.setFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 12));

		this.obt_add_user = new OutLineButton("등록", 80, 40, 10, 1, Color.BLACK, Color.WHITE);
		this.obt_modify_user = new OutLineButton("수정", 80, 40, 10, 1, Color.BLACK, Color.WHITE);
		this.obt_delete_user = new OutLineButton("삭제", 80, 40, 10, 1, Color.BLACK, Color.WHITE);
		this.obt_search_user = new OutLineButton("검색", 60, 20, 10, 1, Color.BLACK, Color.WHITE);
		this.obt_add_user.setEnabled(true);
		this.obt_add_user.addActionListener(e -> {
			member_info_dialog.setType(MemberDialogType.ADD);
			getDepts();
			getAuth();
			getJobGrade();
			member_info_dialog.setMember(null);
			member_info_dialog.setVisible(true);
		});
		this.obt_modify_user.setEnabled(false);
		this.obt_modify_user.addActionListener(e -> {
			member_info_dialog.setType(MemberDialogType.MODIFY);
			getDepts();
			getAuth();
			getJobGrade();
			member_info_dialog.setMember(selectedMember);
			member_info_dialog.setVisible(true);
		});
		this.obt_delete_user.setEnabled(false);
		this.obt_delete_user.addActionListener(e -> {
			int result = JOptionPane.showConfirmDialog(this,
					String.format(deleteUserFormat, this.selectedMember.getMember_name()));
			if (result == JOptionPane.YES_OPTION) {
				try {
					this.memberDAO.delete(this.selectedMember.getMember_id());
					JOptionPane.showMessageDialog(this, "삭제를 완료하였습니다.");
					getData();
				} catch (MemberDeleteException e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(this, "삭제를 실패하였습니다.");
					getData();
				}
			}
		});
		this.obt_search_user.setEnabled(false);
		this.obt_search_user.addActionListener(e -> {
			findUsers();
		});
		this.tf_search_user = new JTextField(20) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (getText().isEmpty()) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setBackground(Color.LIGHT_GRAY);
					g2.drawString("email, 이름 검색", 5, 17);
					g2.dispose();
				}
			}
		};
		this.tf_search_user.setMaximumSize(new Dimension(300, 30));
		this.tf_search_user.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateSearchButton();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateSearchButton();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateSearchButton();
			}
		});

		this.tb_member = new JTable();
		this.tb_member.setModel(new MemberManagementTableModel(Arrays.asList()));
		this.tb_member.setAutoCreateRowSorter(true);
		this.tb_member.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tb_member.getSelectedRow();
				if (row != -1) {
					int memberRow = tb_member.convertRowIndexToModel(row);
					if (tb_member.getModel() instanceof MemberManagementTableModel) {
						selectedMember = ((MemberManagementTableModel) tb_member.getModel()).getMemberAt(memberRow);
						updateUi();
					}
				}
			}
		});
		this.sp_table_scroll = new JScrollPane(tb_member);

		// 조립
		this.p_top_info_wrapper.add(this.la_user);
		this.p_top_info_wrapper.add(Box.createHorizontalStrut(10));
		this.p_top_info_wrapper.add(this.la_user_count);
		this.p_top_info_wrapper.add(Box.createHorizontalStrut(5));
		this.p_top_info_wrapper.add(this.la_deactivated);

		this.p_top_button_wrapper.add(this.obt_add_user);
		this.p_top_button_wrapper.add(Box.createHorizontalStrut(15));
		this.p_top_button_wrapper.add(this.obt_modify_user);
		this.p_top_button_wrapper.add(Box.createHorizontalStrut(15));
		this.p_top_button_wrapper.add(this.obt_delete_user);

		this.p_top_search_wrapper.add(this.tf_search_user);
		this.p_top_search_wrapper.add(Box.createHorizontalStrut(10));
		this.p_top_search_wrapper.add(this.obt_search_user);

		this.la_title.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.p_top_info_wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.p_top_button_wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.p_top_search_wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

		this.p_top_wrapper.add(la_title);
		this.p_top_wrapper.add(Box.createVerticalStrut(30));
		this.p_top_wrapper.add(p_top_info_wrapper);
		this.p_top_wrapper.add(Box.createVerticalStrut(10));
		this.p_top_wrapper.add(p_top_button_wrapper);
		this.p_top_wrapper.add(Box.createVerticalStrut(10));
		this.p_top_wrapper.add(p_top_search_wrapper);
		this.p_top_wrapper.add(Box.createVerticalStrut(10));

		add(this.p_top_wrapper, BorderLayout.NORTH);
		add(this.sp_table_scroll, BorderLayout.CENTER);
		member_info_dialog = new MemberInfoDialog(JOptionPane.getFrameForComponent(this), MemberDialogType.ADD, this);
		getData();
	}

	/**
	 * 검색 텍스트필드에 글자가 입력되었는지 확인하여 검색버튼 활/비활성화 처리
	 */
	private void updateSearchButton() {
		boolean enable = !this.tf_search_user.getText().isEmpty();
		this.obt_search_user.setEnabled(enable);
	}

	private void updateUi() {
		if (selectedMember != null) {
			this.obt_modify_user.setEnabled(true);
			this.obt_delete_user.setEnabled(true);
		} else {
			this.obt_modify_user.setEnabled(false);
			this.obt_delete_user.setEnabled(false);
		}
	}

	private void getData() {
		getUsers();
		initUsersStatus();
	}

	private void getDepts() {
		List<Dept> depts = this.deptDAO.selectAll();
		this.member_info_dialog.setDepts(depts);
	}

	private void getAuth() {
		List<Auth> auths = this.authDAO.selectAll();
		this.member_info_dialog.setAuth(auths);
	}

	private void getJobGrade() {
		List<JobGrade> jobGrades = this.jobGradeDAO.selectAll();
		this.member_info_dialog.setJobGrades(jobGrades);
	}

	private void getUsers() {
		this.members.clear();
		this.members.addAll(this.memberDAO.selectAll());
		setTableModel(members);
	}

	private void findUsers() {
		List<Member> members = this.memberDAO.selectByNameOrEmail(this.tf_search_user.getText().trim());
		setTableModel(members);
	}

	private void setTableModel(List<Member> members) {
		MemberManagementTableModel model = new MemberManagementTableModel(members);
		this.tb_member.setModel(model);
		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(JLabel.RIGHT);
		this.tb_member.getColumnModel().getColumn(0).setCellRenderer(rightRender);
		this.tb_member.getColumnModel().getColumn(1).setCellRenderer(rightRender);
		this.tb_member.getColumnModel().getColumn(2).setCellRenderer(centerRender);
		this.tb_member.getColumnModel().getColumn(3).setCellRenderer(rightRender);
		this.tb_member.getColumnModel().getColumn(4).setCellRenderer(rightRender);
		this.tb_member.getColumnModel().getColumn(5).setCellRenderer(centerRender);
		this.tb_member.updateUI();
	}

	private void initUsersStatus() {
		int deactivated_count = this.members.stream().filter((m) -> m.isDormant()).toArray().length;
		this.la_user_count.setText(String.format(userCountFormat, this.members.size()));
		this.la_deactivated.setText(String.format(deactivatedFormat, deactivated_count));
	}

	@Override
	public void onRegistration() {
		getData();
	}

	@Override
	public void onModified() {
		getData();
	}
}
