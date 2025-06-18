package com.sinse.wms.statistic.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.content.ExampleContentPage;

public class StatisticPage extends ExampleContentPage{
	//크게 3개 구역으로 지정
	JPanel p_top;
	JPanel p_center;
	JPanel p_bottom;
	
	//첫번째 레이아웃에 들어갈 항목(p_top)
	//입고, 출고, 재고 막대 그래프
	JTabbedPane tab;
	JPanel p_inbound;
	JPanel p_outbound;
	JPanel p_inventory;
	
	JPanel p_toplist1;
	JPanel p_toplist2;
	JLabel la_titleIn;
	JLabel la_resultIn;
	JLabel la_titleOut;
	JLabel la_resultOut;
	
	//두번째 레이아웃에 들어갈 항목(p_center)
	JPanel p_total;
	
	//세번째 레이아웃에 들어갈 항목
	JPanel p_category1;
	JPanel p_category2;
	
	public StatisticPage(Color color) {
		super(color);
		
		/*생성----------------------------------------------------------------------------*/
		//최상위 레이아웃
		p_top = new JPanel();
		p_center = new JPanel();
		p_bottom = new JPanel();
		
		//첫 번째 레이아웃
		tab = new JTabbedPane();
		p_inbound = new JPanel();
		p_outbound = new JPanel();
		p_inventory = new JPanel();
		p_toplist1 = new JPanel();
		la_titleIn = new JLabel("Top 5");
		la_resultIn = new JLabel("<html>1. Null <br>2. Null<br>3. Null<br>4. Null<br>5. Null<br></html>");
		p_toplist2 = new JPanel();
		la_titleOut = new JLabel("Top 5");
		la_resultOut = new JLabel("<html>1. Null <br>2. Null<br>3. Null<br>4. Null<br>5. Null<br></html>");
		
		//두 번째 레이아웃
		p_total = new JPanel();
		
		//세 번째 레이아웃
		p_category1 = new JPanel();
		p_category2 = new JPanel();
		
		/*스타일----------------------------------------------------------------------------*/
		//최상위 레이아웃 스타일 적용
		Dimension d_layout = new Dimension(Config.CONTENT_BODY_WIDTH - Config.CONTENT_BODY_BORDER_WIDTH * 2,
				(Config.CONTENT_BODY_HEIGHT - Config.CONTENT_BODY_BORDER_HEIGHT * 2)/3-10);
		p_top.setPreferredSize(d_layout);
		p_center.setPreferredSize(d_layout);
		p_bottom.setPreferredSize(d_layout);
		
		Border border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY);
		p_top.setBorder(border);
		p_center.setBorder(border);
		
		p_top.setBackground(Color.white);
		p_center.setBackground(Color.white);
		p_bottom.setBackground(Color.white);
		
		//첫번째 레이아웃 항목 스타일 지정
		tab.setBorder(BorderFactory.createCompoundBorder());
		tab.setPreferredSize(new Dimension(400, 250));
		
		Font titleFont = new Font("맑은고딕", Font.BOLD, 25);
		Font listFont = new Font("맑은고딕", Font.PLAIN, 18);
		la_titleIn.setFont(titleFont);
		la_titleOut.setFont(titleFont);
		la_resultIn.setFont(listFont);
		la_resultOut.setFont(listFont);
		
		Dimension d_label1 = new Dimension(200, 30);
		Dimension d_label2 = new Dimension(200, 150);
		la_titleIn.setPreferredSize(d_label1);
		la_resultIn.setPreferredSize(d_label2);
		la_titleOut.setPreferredSize(d_label1);
		la_resultOut.setPreferredSize(d_label2);
		
		Dimension d_label3 = new Dimension(210, 250);
		p_toplist1.setBorder(BorderFactory.createTitledBorder("입고량"));
		p_toplist1.setPreferredSize(d_label3);
		p_toplist2.setBorder(BorderFactory.createTitledBorder("출고량"));
		p_toplist2.setPreferredSize(d_label3);
		
		//두번째 레이아웃 항목 스타일 지정
		p_total.setPreferredSize(new Dimension(800, 250));
		p_total.setBorder(BorderFactory.createTitledBorder("Total"));
		
		//세번째 레이아웃 항목 스타일 지정
		Dimension d_category = new Dimension(400, 250);
		p_category1.setPreferredSize(d_category);
		p_category2.setPreferredSize(d_category);
		
		p_category1.setBorder(BorderFactory.createTitledBorder("불량손실비율"));
		p_category2.setBorder(BorderFactory.createTitledBorder("창고 내 밀집도"));
		
		/*조립----------------------------------------------------------------------------*/
		this.setLayout(new FlowLayout());
		tab.add("입고량", p_inbound);
		tab.add("출고량", p_outbound);
		tab.add("재고량", p_inventory);
		p_top.add(tab);
		p_toplist1.add(la_titleIn);
		p_toplist1.add(la_resultIn);
		p_toplist2.add(la_titleOut);
		p_toplist2.add(la_resultOut);
		p_top.add(p_toplist1);
		p_top.add(p_toplist2);
		add(p_top);
		
		p_center.add(p_total);
		add(p_center);
		
		p_bottom.add(p_category1);
		p_bottom.add(p_category2);
		add(p_bottom);

	}
}