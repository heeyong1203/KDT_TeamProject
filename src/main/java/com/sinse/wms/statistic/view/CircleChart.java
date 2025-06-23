package com.sinse.wms.statistic.view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.sinse.wms.common.Config;

public class CircleChart  extends JPanel{
	String title;
	DefaultPieDataset dataset;
	JFreeChart chart;
	ChartPanel chartPanel;
	List<Map<String, Double>> list;
	
	public CircleChart(String title, List<Map<String, Double>> list) {
		this.title = title;
		this.list = list;
		
		//생성
		dataset = new DefaultPieDataset();
		chart = createChart(dataset);
		chartPanel = new ChartPanel(chart);
		
		//스타일
		chart.getTitle().setFont(new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 10));
		chartPanel.setPreferredSize(new Dimension(380, 320));
		
		//데이터 셋
		for(Map<String, Double> map : list) {
			for (Map.Entry<String, Double> entry : map.entrySet()) {
				dataset.setValue(entry.getKey(), entry.getValue());
			}
		}
		
		//부착
		this.add(chartPanel);
	}
	
	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
		
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 10));
		plot.setNoDataMessage("No data available");
		plot.setCircular(false);
		plot.setLabelGap(0.02);
		
		return chart;
	}
	
}
