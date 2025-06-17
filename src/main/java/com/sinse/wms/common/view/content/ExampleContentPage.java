package com.sinse.wms.common.view.content;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;

public class ExampleContentPage extends BaseContentPage {
	
	public ExampleContentPage(Color color) {
		setBackgroundColor(color);
		setLayout(new BorderLayout());
		add(new JLabel("Foo"),BorderLayout.NORTH);
	}

}
