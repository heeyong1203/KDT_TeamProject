package com.sinse.wms.statistic.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.text.TextBlockAnchor;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.sinse.wms.common.Config;
import com.sinse.wms.product.model.Stock;

public class HorizentalBarChart extends JPanel{
	String title;
	DefaultCategoryDataset dataset;
	JFreeChart chart;
	ChartPanel chartPanel;
	
	public HorizentalBarChart(String title, List<Stock> list) {
		this.title = title;
		//생성
		dataset = new DefaultCategoryDataset();
		chart = createChart(dataset);
		chartPanel = new ChartPanel(chart);
		
		//스타일
		chart.getTitle().setFont(new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 10));
		chartPanel.setPreferredSize(new Dimension(380, 320));
		
		//데이터 입력
		for(int i=0; i<list.size(); i++) {
			Stock stock = list.get(i);
			dataset.addValue(stock.getStock_quantity(), "상품 총 수량", stock.getLocation().getLocation_name());
		}
		
		
		//조립
		this.setLayout(new BorderLayout());
		this.add(chartPanel);
	}
	
	private JFreeChart createChart(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(title, "창고명", "포화도(개수)", dataset,
							PlotOrientation.HORIZONTAL, true, true, false);
		
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setForegroundAlpha(1.0f);
		
		CategoryAxis axis = plot.getDomainAxis();
		CategoryLabelPositions p = axis.getCategoryLabelPositions();
		
		CategoryLabelPosition left = new CategoryLabelPosition(
				RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT,
				TextAnchor.CENTER_LEFT, 0.0,
				CategoryLabelWidthType.RANGE, 0.3f);
		axis.setCategoryLabelPositions(CategoryLabelPositions.replaceLeftPosition(p, left));
		
		plot.getDomainAxis().setLabelFont(new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 10));
		plot.getDomainAxis().setTickLabelFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 8));
		plot.getRangeAxis().setLabelFont(new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 10));
		plot.getRangeAxis().setTickLabelFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 8));
		
		return chart;
	}
}
