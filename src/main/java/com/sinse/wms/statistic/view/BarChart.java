package com.sinse.wms.statistic.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.sinse.wms.common.Config;

public class BarChart extends JPanel{
	String title;	//제목
	int totalInbound;
	int totalOutbound;
	int totalStock;
	
	CategoryDataset dataset;
	JFreeChart chart;
	ChartPanel chartPanel;
	
	//생성자
	public BarChart(String title, int totalInbound, int totalOutbound, int totalStock) {
		this.title = title;
		this.totalInbound = totalInbound;
		this.totalOutbound = totalOutbound;
		this.totalStock = totalStock;
		
		//생성
		dataset = createDataset();
		chart = createChart(dataset);
		chartPanel = new ChartPanel(chart);
		
		//스타일
		chart.getTitle().setFont(new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 10));
		chartPanel.setPreferredSize(new Dimension(380, 320));
		
		//부착
		this.setLayout(new BorderLayout());
		this.add(chartPanel);
	}
	
	//데이터 세팅
	private CategoryDataset createDataset() {
		//row key
		final String series = "총 합계";
		
		//column key
		final String category1 = "일별";
		final String category2 = "주별";
		final String category3 = "월별";
		
		//데이터 셋 생성
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(totalInbound, series, category1);
		dataset.addValue(totalOutbound, series, category2);
		dataset.addValue(totalStock, series, category3);
		
		return dataset;
	}
	
	//차트 생성
	private JFreeChart createChart(CategoryDataset dataset) {
		//차트 생성
		JFreeChart chart = ChartFactory.createBarChart(title, "항목", "수량", dataset, 
						PlotOrientation.VERTICAL, true, true, false);
		chart.setBackgroundPaint(Color.WHITE);
		
		//plot 스타일 지정
		CategoryPlot plot =  chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.LIGHT_GRAY);
		plot.setDomainGridlinePaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.WHITE);
		
		//한글 폰트 지정
		plot.getDomainAxis().setLabelFont(new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 10));
		plot.getDomainAxis().setTickLabelFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 8));
		plot.getRangeAxis().setLabelFont(new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 10));
		plot.getRangeAxis().setTickLabelFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 8));
		
		//범위 축 설정(정수만 표시)
		NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		//그래프 색 설정
		BarRenderer renderer = new BarRenderer() {
			private final Paint[] paints = new Paint[] {
					new GradientPaint(0f, 0f, Color.BLUE, 0f, 0f, Color.CYAN),
			        new GradientPaint(0f, 0f, Color.GREEN, 0f, 0f, Color.LIGHT_GRAY),
			        new GradientPaint(0f, 0f, Color.ORANGE, 0f, 0f, Color.YELLOW)
			};
			@Override
			public Paint getItemPaint(int row, int column) {
				return paints[column % paints.length];
			}
		};
		plot.setRenderer(renderer);

		//x축 레이블 배치
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI/6.0));
		
		return chart;
	}
}
