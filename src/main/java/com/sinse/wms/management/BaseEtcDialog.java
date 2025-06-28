package com.sinse.wms.management;

import java.awt.*;

import javax.swing.*;

import com.sinse.wms.common.view.button.OutLineButton;

public abstract class BaseEtcDialog<T> extends JDialog {
	private JPanel p_centerWrapper;
	private JPanel p_inputWrapper;
	protected JLabel title;
	protected JLabel la_name;
	protected JTextField tf_input;
	protected OutLineButton obt_submit;
	protected T data;
	protected Runnable completeRunnable;

	public BaseEtcDialog(String titleName) {
		this(titleName, null);
	}

	public BaseEtcDialog(String titleName,T data) {
		setSize(400, 200);
		setLayout(new BorderLayout());
		setTitle(titleName);
		this.data = data;
		this.p_centerWrapper = new JPanel();
		this.p_centerWrapper.setLayout(new GridBagLayout());
		this.p_inputWrapper = new JPanel();
		this.p_inputWrapper.setLayout(new BoxLayout(p_inputWrapper, BoxLayout.X_AXIS));
		this.p_inputWrapper.setPreferredSize(new Dimension(300, 40));
		this.la_name = new JLabel("이름 :", SwingConstants.RIGHT);
		this.tf_input = new JTextField();
		if (this.data == null) {
			this.tf_input.setText(null);
			this.obt_submit = new OutLineButton("등록");
			this.obt_submit.addActionListener(e -> {
				insert();
			});
			this.title = new JLabel("등록하기");
		} else {
			this.tf_input.setText(getDefaultTextFieldData());

			this.obt_submit = new OutLineButton("수정");
			this.obt_submit.addActionListener(e -> {
				update();
			});
			this.title = new JLabel("수정하기");
		}
		this.title.setBorder(BorderFactory.createEmptyBorder(10, 10,10, 0));
		this.title.setFont(new Font("Default", Font.BOLD, 20));
		this.p_inputWrapper.add(this.la_name);
		this.p_inputWrapper.add(this.tf_input);
		this.p_centerWrapper.add(this.p_inputWrapper);
		add(this.title, BorderLayout.NORTH);
		add(this.p_centerWrapper, BorderLayout.CENTER);
		add(this.obt_submit, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
	}

	protected abstract String getDefaultTextFieldData();

	protected void insert() {

	}

	protected void update() {

	}

	protected void disposeWithComplete() {
		dispose();
		if (this.completeRunnable != null) {
			this.completeRunnable.run();
		}
	}

	public void setOnComplete(Runnable runnable){
		this.completeRunnable = runnable;
	}
}
