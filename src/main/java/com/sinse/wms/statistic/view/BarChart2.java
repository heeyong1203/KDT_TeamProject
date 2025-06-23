package com.sinse.wms.statistic.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.util.List;
import java.util.Map;

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

public class BarChart2 extends JPanel{
	String title;	//제목
	
	CategoryDataset dataset;
	JFreeChart chart;
	ChartPanel chartPanel;
	List<Map<String, Integer>> list;
	
	//생성자
	public BarChart2(String title, List<Map<String, Integer>> list) {
		this.title = title;
		this.list = list;
		
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
		
		//데이터 셋 생성
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for(Map<String, Integer> map : list) {
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				dataset.addValue(entry.getValue(), series, entry.getKey());
			}
		}
		
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
			        new GradientPaint(0f, 0f, Color.ORANGE, 0f, 0f, Color.YELLOW),
			        new GradientPaint(0f, 0f, Color.RED, 0f, 0f, Color.PINK),
			        new GradientPaint(0f, 0f, new Color(75, 0, 130), 0f, 0f, new Color(138, 43, 226)),
			        new GradientPaint(0f, 0f, new Color(255, 105, 180), 0f, 0f, new Color(255, 182, 193)),
			        new GradientPaint(0f, 0f, new Color(0, 255, 127), 0f, 0f, new Color(173, 255, 47)),
			        new GradientPaint(0f, 0f, new Color(0, 191, 255), 0f, 0f, new Color(135, 206, 250)), 
			        new GradientPaint(0f, 0f, new Color(210, 105, 30), 0f, 0f, new Color(255, 160, 122)),
			        new GradientPaint(0f, 0f, new Color(106, 90, 205), 0f, 0f, new Color(123, 104, 238)) 
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
