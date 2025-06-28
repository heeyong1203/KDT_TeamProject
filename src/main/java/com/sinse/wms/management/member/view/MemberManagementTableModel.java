package com.sinse.wms.management.member.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.sinse.wms.product.model.Member;

public class MemberManagementTableModel extends AbstractTableModel {
	private List<Member> members;

	public MemberManagementTableModel(List<Member> members) {
		this.members = members;
	}
	
	public Member getMemberAt(int row) {
		return members.get(row);
	}

	@Override
	public int getRowCount() {
		return this.members.size();
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "이름";
		case 1:
			return "이메일";
		case 2:
			return "관리자";
		case 3:
			return "부서";
		case 4: 
			return "직급";
		default:
			return "휴면상태";
		}
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Member member = this.members.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return member.getMember_name();
		case 1:
			return member.getMember_email();
		case 2:
			if (member.getAuth().getAuth_id() <= 3) {
				return "O";
			} else {
				return "X";
			}
		case 3:
			return member.getDept().getDept_name();
		case 4:
			return member.getJobGrade().getJobGradeName();
		default:
			if (member.isDormant()) {
				return "O";
			} else {
				return "X";
			}
		}
	}

}
