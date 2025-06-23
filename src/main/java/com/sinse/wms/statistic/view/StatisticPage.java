package com.sinse.wms.statistic.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.product.model.IoRequest;
import com.sinse.wms.product.model.Stock;
import com.sinse.wms.product.repository.IoRequestDAO;
import com.sinse.wms.product.repository.StockDAO;

public class StatisticPage extends BaseContentPage{
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
	
	BarChart inboundBarChart;
	BarChart outboundBarChart;
	BarChart2 stockBarChart;
	
	JPanel p_toplist1;
	JPanel p_toplist2;
	JLabel la_titleIn;
	JLabel la_resultIn;
	JLabel la_titleOut;
	JLabel la_resultOut;
	
	CircleChart circleChart1;
	
	//세번째 레이아웃에 들어갈 항목
	JPanel p_category1;
	JPanel p_category2;
	
	HorizentalBarChart hBarChart;
	
	//DAO
	StockDAO stockDAO;
	IoRequestDAO ioRequestDAO;
	
	public StatisticPage() {
		
		/*생성----------------------------------------------------------------------------*/
		//DAO
		stockDAO = new StockDAO();
		ioRequestDAO = new IoRequestDAO();
		
		//최상위 레이아웃
		p_top = new JPanel();
		p_center = new JPanel();
		p_bottom = new JPanel();
		
		//첫 번째 레이아웃
		tab = new JTabbedPane();
		p_inbound = new JPanel();
		p_outbound = new JPanel();
		p_inventory = new JPanel();
		
		//그래프
		inboundBarChart = new BarChart("기간별 총 입고 현황", ioRequestDAO.selectDailyIobound("입고"), ioRequestDAO.selectWeekIobound("입고"), ioRequestDAO.selectMonthIobound("입고"));		//입고량
		outboundBarChart = new BarChart("기간별 총 출고 현황", ioRequestDAO.selectDailyIobound("출고"), ioRequestDAO.selectWeekIobound("출고"), ioRequestDAO.selectMonthIobound("출고"));		//출고량
		
		List<Map<String,Integer>> stockList = stockDAO.selectTotalStockByCategory();
		stockBarChart = new BarChart2("카테고리별 재고 현황", stockList);		//재고량
		
		List<Stock> invenList = stockDAO.selectTotalStockByLocation();
		hBarChart = new HorizentalBarChart("재고 창고 포화도", invenList);
		
		//top 리스트
		//입고 top list
		List<IoRequest> inboundToplist = ioRequestDAO.selectInOutTop5("입고");
		String inboundLabelString = "<html>";
		for(int i=0; i<inboundToplist.size(); i++) {
			IoRequest io = inboundToplist.get(i);
			inboundLabelString += (i+1)+ ". " + io.getProduct().getProduct_name() + "&nbsp;" + io.getQuantity() + "<br>";
		}
		inboundLabelString += "</html>";
		
		//출고 top list
		List<IoRequest> outboundToplist = ioRequestDAO.selectInOutTop5("출고");
		String outboundLabelString = "<html>";
		for(int i=0; i<outboundToplist.size(); i++) {
			IoRequest io = outboundToplist.get(i);
			outboundLabelString += (i+1)+ ". " + io.getProduct().getProduct_name() + "&nbsp;" + io.getQuantity() + "<br>";
		}
		outboundLabelString += "</html>";
		
		p_toplist1 = new JPanel();
		la_titleIn = new JLabel("Top 5");
		la_resultIn = new JLabel(inboundLabelString);
		p_toplist2 = new JPanel();
		la_titleOut = new JLabel("Top 5");
		la_resultOut = new JLabel(outboundLabelString);
		
		
		//세 번째 레이아웃
		List<Map<String, Double>> categoryList =ioRequestDAO.selectCategoryQuantityPercent();
		circleChart1 = new CircleChart("카테고리 별 총 출고 백분율", categoryList);
		p_category1 = new JPanel();
		p_category2 = new JPanel();
		
		/*스타일----------------------------------------------------------------------------*/
		//최상위 레이아웃 스타일 적용
		Dimension d_layout = new Dimension(Config.CONTENT_BODY_WIDTH - Config.CONTENT_BODY_BORDER_WIDTH * 2-10,
				(Config.CONTENT_BODY_HEIGHT - Config.CONTENT_BODY_BORDER_HEIGHT * 2)/2-10);
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
		tab.setPreferredSize(new Dimension(400, 370));
		
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
		
		Dimension d_label3 = new Dimension(210, 370);
		p_toplist1.setBorder(BorderFactory.createTitledBorder("입고량"));
		p_toplist1.setPreferredSize(d_label3);
		p_toplist2.setBorder(BorderFactory.createTitledBorder("출고량"));
		p_toplist2.setPreferredSize(d_label3);
		
		
		//세번째 레이아웃 항목 스타일 지정
		Dimension d_category = new Dimension(400, 370);
		p_category1.setPreferredSize(d_category);
		p_category2.setPreferredSize(d_category);
		
		p_category1.setBorder(BorderFactory.createTitledBorder("카테고리별 출고 비율"));
		p_category2.setBorder(BorderFactory.createTitledBorder("창고 내 밀집도"));
		
		/*조립----------------------------------------------------------------------------*/
		this.setLayout(new FlowLayout());
		p_inbound.add(inboundBarChart);
		tab.add("입고량", p_inbound);
		p_outbound.add(outboundBarChart);
		tab.add("출고량", p_outbound);
		p_inventory.add(stockBarChart);
		tab.add("재고량", p_inventory);
		
		p_top.add(tab);
		p_toplist1.add(la_titleIn);
		p_toplist1.add(la_resultIn);
		p_toplist2.add(la_titleOut);
		p_toplist2.add(la_resultOut);
		p_top.add(p_toplist1);
		p_top.add(p_toplist2);
		add(p_top);
		
		p_category1.add(circleChart1);
		p_bottom.add(p_category1);
		p_category2.add(hBarChart);
		p_bottom.add(p_category2);
		add(p_bottom);

	}
}