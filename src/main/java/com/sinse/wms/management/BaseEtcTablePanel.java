package com.sinse.wms.management;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableModel;

import com.sinse.wms.common.view.button.OutLineButton;

public abstract class BaseEtcTablePanel<T, DAO> extends JPanel {
	protected JLabel title;
	protected JPanel p_searchBarWrapper;
	protected JPanel p_buttonBarWrapper;
	protected JTextField tf_input;
	protected JScrollPane sp_tableScroll;
	protected JTable tb_dataTable;
	protected OutLineButton obt_search;
	protected OutLineButton obt_add;
	protected OutLineButton obt_modify;
	protected OutLineButton obt_delete;
	protected List<T> data = null;
	protected T selectedData = null;
	protected DAO currentDAO;

	public BaseEtcTablePanel(DAO dao) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		this.currentDAO = dao;
		this.title = new JLabel(getTitle(), SwingConstants.LEFT);
		this.title.setFont(new Font("Default", Font.BOLD, 20));
		this.title.setMaximumSize(new Dimension(200, 50));
		this.title.setAlignmentX(Component.LEFT_ALIGNMENT);

		this.p_buttonBarWrapper = new JPanel();
		this.p_buttonBarWrapper.setLayout(new BoxLayout(this.p_buttonBarWrapper, BoxLayout.X_AXIS));
		this.p_buttonBarWrapper.setBackground(getBackground());
		this.p_buttonBarWrapper.setMaximumSize(new Dimension(250, 30));
		this.p_buttonBarWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.obt_add = new OutLineButton("등록");
		this.obt_add.setMaximumSize(new Dimension(100, 30));
		this.obt_add.addActionListener(e -> {
			onAdd();
		});
		this.obt_modify = new OutLineButton("수정");
		this.obt_modify.setMaximumSize(new Dimension(100, 30));
		this.obt_modify.addActionListener(e -> {
			onModify();
		});
		this.obt_delete = new OutLineButton("삭제");
		this.obt_delete.setMaximumSize(new Dimension(100, 30));
		this.obt_delete.addActionListener(e -> {
			onDelete();
		});
		this.p_buttonBarWrapper.add(this.obt_add);
		this.p_buttonBarWrapper.add(Box.createHorizontalStrut(10));
		this.p_buttonBarWrapper.add(this.obt_modify);
		this.p_buttonBarWrapper.add(Box.createHorizontalStrut(10));
		this.p_buttonBarWrapper.add(this.obt_delete);

		this.p_searchBarWrapper = new JPanel();
		this.p_searchBarWrapper.setLayout(new BoxLayout(this.p_searchBarWrapper, BoxLayout.X_AXIS));
		this.p_searchBarWrapper.setBackground(getBackground());
		this.p_searchBarWrapper.setMaximumSize(new Dimension(250, 30));
		this.p_searchBarWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.obt_search = new OutLineButton("검색");
		this.obt_search.addActionListener(e -> {
			onSearch();
		});
		this.tf_input = new JTextField();
		this.p_searchBarWrapper.add(this.tf_input);
		this.p_searchBarWrapper.add(Box.createHorizontalStrut(10));
		this.p_searchBarWrapper.add(this.obt_search);

		this.tb_dataTable = new JTable();
		this.tb_dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.sp_tableScroll = new JScrollPane(this.tb_dataTable);
		this.sp_tableScroll.setBackground(getBackground());

		add(this.title);
		add(Box.createVerticalStrut(20));
		add(this.p_buttonBarWrapper);
		add(Box.createVerticalStrut(10));
		add(this.p_searchBarWrapper);
		add(Box.createVerticalStrut(10));
		add(this.sp_tableScroll);
		init();
		this.data = getData();
		setOnSelected();
	}

	protected abstract void init();

	protected abstract List<T> getData();

	protected abstract TableModel getTableModel();

	protected T getSelectedData() {
		int selectedIndex = this.tb_dataTable.getSelectedRow();
		int selectedModelIndex = this.tb_dataTable.convertRowIndexToModel(selectedIndex);
		return this.data.get(selectedModelIndex);
	};

	protected abstract String getTitle();

	protected abstract void onSearch();

	protected abstract void onAdd();

	protected abstract void onModify();

	protected abstract void onDelete();

	protected void setModel() {
		this.tb_dataTable.setModel(getTableModel());
		this.tb_dataTable.revalidate();
		this.tb_dataTable.repaint();
	}

	protected String getInput() {
		return this.tf_input.getText().trim();
	}

	protected void setModifyButtonEnable(boolean enable) {
		this.obt_modify.setEnabled(enable);
	}

	protected void setDeleteButtonEnable(boolean enable) {
		this.obt_delete.setEnabled(enable);
	}

	protected void onSelectedData(T data) {
		this.selectedData = data;
	}

	private void setOnSelected(){
		this.tb_dataTable.getSelectionModel().addListSelectionListener(e -> {
			if (e.getValueIsAdjusting()) {
				return;
			}
			if (this.tb_dataTable.getSelectedRow() == -1) {
				setModifyButtonEnable(false);
				setDeleteButtonEnable(false);
				return;
			}
			T selectedData = getSelectedData();
			onSelectedData(selectedData);
			setModifyButtonEnable(true);
			setDeleteButtonEnable(true);
		});
	}

	protected void refresh() {
		this.data = getData();
		setModel();
	}
}
