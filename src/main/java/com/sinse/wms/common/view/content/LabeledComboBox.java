package com.sinse.wms.common.view.content;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LabeledComboBox extends JPanel {
	private JLabel label;
    private JComboBox<String> comboBox;
	
	public LabeledComboBox(String labelText, String[] items, Dimension labelSize, Dimension comboSize) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        label = new JLabel(labelText);
        label.setPreferredSize(labelSize);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        comboBox = new JComboBox<>(items);
        comboBox.setPreferredSize(comboSize);

        add(label);
        add(comboBox);
        setOpaque(false);
	}
}
