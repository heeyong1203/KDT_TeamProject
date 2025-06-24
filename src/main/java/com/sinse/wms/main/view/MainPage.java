package com.sinse.wms.main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sinse.wms.common.util.CalendarCard;
import com.sinse.wms.common.util.ToDoCard;
import com.sinse.wms.common.util.WeatherFetcher;
import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.main.model.WeatherData;
import com.sinse.wms.product.repository.StockDAO;

public class MainPage extends BaseContentPage {

    public MainPage(Color color) {
        setBackground(color);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        topPanel.setOpaque(false);

        topPanel.add(createUserCard());
        topPanel.add(createWeatherCard());
        topPanel.add(createCalendarCard());

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.setOpaque(false);

        bottomPanel.add(createTodoCard());
        bottomPanel.add(createStatusCard());

        JPanel content = new JPanel(new GridLayout(2, 1, 20, 20));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        content.setOpaque(false);
        content.add(topPanel);
        content.add(bottomPanel);

        add(content, BorderLayout.CENTER);
    }

    public  JPanel createUserCard() {
        JPanel panel = createCardPanel();
        panel.setLayout(new BorderLayout());

        JLabel icon;
        try {
            URL imageUrl = getClass().getResource("/images/default_avatar_icon.png");
            if (imageUrl == null) throw new IOException("이미지 리소스를 찾을 수 없습니다.");

            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(scaledImage);

            icon = new JLabel(resizedIcon);
        } catch (Exception e) {
            icon = new JLabel("No Image", SwingConstants.CENTER);
            icon.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            icon.setForeground(Color.GRAY);
        }

        icon.setHorizontalAlignment(SwingConstants.CENTER);

        //이후 로그인창에서 들어오면.. 정보를 가지고 들어와야함.
        
        JLabel name = new JLabel("님\n안녕하세요.", SwingConstants.CENTER);
        name.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        name.setForeground(new Color(50, 50, 150));

        panel.add(icon, BorderLayout.CENTER);
        panel.add(name, BorderLayout.SOUTH);
        return panel;
    }



    private JPanel createWeatherCard() {
        JPanel panel = createCardPanel();
        panel.setLayout(new BorderLayout());

        try {
            WeatherData data = WeatherFetcher.fetch("Seoul"); // 도시 이름 지정

            // 아이콘 로딩
            ImageIcon icon = new ImageIcon(new URL("https://openweathermap.org/img/wn/" + data.icon + "@2x.png"));
            JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);

            JLabel tempLabel = new JLabel((int) data.temperature + " °C", SwingConstants.CENTER);
            tempLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

            JLabel sunriseLabel = new JLabel("🌅 " + WeatherFetcher.formatTime(data.sunrise), SwingConstants.CENTER);
            JLabel sunsetLabel = new JLabel("🌇 " + WeatherFetcher.formatTime(data.sunset), SwingConstants.CENTER);

            JPanel timePanel = new JPanel(new GridLayout(1, 2));
            timePanel.setOpaque(false);
            timePanel.add(sunriseLabel);
            timePanel.add(sunsetLabel);

            panel.add(iconLabel, BorderLayout.NORTH);
            panel.add(tempLabel, BorderLayout.CENTER);
            panel.add(timePanel, BorderLayout.SOUTH);

        } catch (Exception e) {
            JLabel error = new JLabel("날씨 정보를 불러올 수 없습니다.", SwingConstants.CENTER);
            panel.add(error, BorderLayout.CENTER);
        }

        return panel;
    }


    private JPanel createCalendarCard() {
    	return new CalendarCard(); // 새로 만든 클래스
    }

    private JPanel createTodoCard() {
        return new ToDoCard(); // 새 할일 카드 컴포넌트
    }


    private JPanel createStatusCard() {
        JPanel panel = createCardPanel();
        panel.setLayout(new GridLayout(3, 2));

        StockDAO dao = new StockDAO();
        int outboundQty = dao.getExpectedOutboundQuantity();
        int inboundQty = dao.getExpectedInboundQuantity();
        int profit = dao.getTodayProfit();

        String[] labels = {"출고 예정", "입고 예정", "금일 총 이익"};
        String[] values = {"+" + outboundQty, "+" + inboundQty, "+" + profit + " $"};
        String[] icons = {"📦", "🏠", "💰"};

        for (int i = 0; i < labels.length; i++) {
            JLabel icon = new JLabel(icons[i], SwingConstants.CENTER);
            icon.setFont(new Font("SansSerif", Font.PLAIN, 24));
            JLabel value = new JLabel(labels[i] + "  " + values[i], SwingConstants.LEFT);
            value.setFont(new Font("SansSerif", Font.BOLD, 14));
            panel.add(icon);
            panel.add(value);
        }

        return panel;
    }


    private JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        panel.setPreferredSize(new Dimension(200, 150));
        return panel;
    }
}
