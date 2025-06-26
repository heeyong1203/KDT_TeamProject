package com.sinse.wms.menu.search.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.Main;

public class SearchMenu extends JFrame{
	private static SearchMenu instance = null;
	JTextField searchField;
	JButton searchButton;
	JLabel resultLabel;
	JList<String> resultList;
	JScrollPane scroll;
	JPanel resultPanel;
	
	private static Main mainFrame;
	int hoverIndex = -1;
	
	//검색어 항목
	Map<String, String> list = new HashMap<String, String>(){
		{
			put("IN_BOUND_STATUS", "입고현황");
			put("IN_BOUND_REQUEST", "입고요청");
			put("IN_BOUND_INSPECTION", "입고검수");
			put("OUT_BOUND_STATUS", "출고현황");
			put("OUT_BOUND_REQUEST", "출고요청");
			put("OUT_BOUND_INSPECTION", "출고검수");
			put("INVENTORY_STATUS", "재고현황");
			put("STATISTICS", "통계");
			put("REPORT", "보고서");
			put("USER_MANAGEMENT", "사용자관리");
			put("MY_PAGE", "마이페이지");
			put("HELP", "도움말");
			put("SETTING", "환경설정");
		}
	};
	
	public SearchMenu() {
		setTitle("검색 창");
        setSize(900, 600);
        setLocationRelativeTo(null); // 화면 중앙에 위치

        // 전체 레이아웃
        setLayout(new BorderLayout());

        // 상단 패널 (검색바 영역)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Config.PRIMARY_COLOR);
        topPanel.setPreferredSize(new Dimension(900, 100));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        // 검색 입력 필드
        searchField = new JTextField(50);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        searchField.setPreferredSize(new Dimension(700, 40));
        searchField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchField.setBorder(BorderFactory.createLineBorder(Config.PRIMARY_COLOR, 0));
        searchField.setBorder(BorderFactory.createCompoundBorder(
        		searchField.getBorder(),BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        searchField.setToolTipText("찾고자 하는 항목을 입력하세요.");
        
        
        // 검색 결과 리스트
        resultList = new JList<>();
	    resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    resultList.setFont(new Font("SansSerif", Font.BOLD, 20));
        resultList.setBackground(Color.WHITE);
        resultList.setForeground(new Color(50, 50, 50));
        resultList.setSelectionBackground(Config.PRIMARY_COLOR);
        resultList.setSelectionForeground(Color.WHITE);
        
	    scroll = new JScrollPane(resultList);
	    scroll.setBounds(10, 40, 950, 400);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));  // 경계선 설정
        scroll.setVisible(false);
        
        // 돋보기 버튼
        searchButton = new JButton("🔍");
        searchButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        searchButton.setPreferredSize(new Dimension(60, 40));
        searchButton.setFocusPainted(false);
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createEmptyBorder());

        // 검색 이벤트 등록
        searchButton.addActionListener(e -> performSearch());	//버튼으로 검색
        searchField.addActionListener(e -> performSearch());		//enter 키로 검색
        
        // 마우스 이동 이벤트 등록
        resultList.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = resultList.locationToIndex(e.getPoint());
                if (index != hoverIndex) {
                    hoverIndex = index;
                    resultList.repaint();
                }
            }
        });
        // 마우스가 리스트를 벗어났을 때
        resultList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoverIndex = -1;
                resultList.repaint();
            }
        });
        // 커스텀 렌더러 설정
        resultList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (isSelected) {
                    // 선택된 항목 스타일
                    c.setBackground(Config.PRIMARY_COLOR);
                    c.setForeground(Color.WHITE);
                } else if (index == hoverIndex) {
                    // Hover된 항목 스타일
                    c.setBackground(new Color(165, 211, 242));
                    c.setForeground(new Color(30, 30, 30));
                } else {
                    // 일반 항목
                    c.setBackground(Color.WHITE);
                    c.setForeground(new Color(50, 50, 50));
                }
                return c;
            }
        });

        // 검색바를 포함하는 내부 패널
        JPanel searchBox = new JPanel(new BorderLayout());
        searchBox.setBackground(Config.PRIMARY_COLOR);
        searchBox.setPreferredSize(new Dimension(800, 50));
        searchBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchBox.add(searchField, BorderLayout.CENTER);
        searchBox.add(searchButton, BorderLayout.EAST);

        topPanel.add(searchBox);
        add(topPanel, BorderLayout.NORTH);

        // 검색 결과 영역
        resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setPreferredSize(new Dimension(800, 400));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //라벨 상단에 배치
        resultLabel = new JLabel("검색어를 입력해주세요.", SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0; 
        gbc.insets = new Insets(10, 10, 10, 10);
        resultPanel.add(resultLabel, gbc);

        //나머지 공간은 list로 배치
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1; 
        gbc.weighty = 1;  // 세로 방향으로 확장 (남은 공간 차지)
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        scroll.setVisible(true);
        resultPanel.add(scroll, gbc);
        
        add(resultPanel, BorderLayout.CENTER);
	}
	
	//검색 메소드
	private void performSearch() {
        String query = searchField.getText().trim();
        
        if (query.isEmpty()) {
            resultLabel.setText("검색어를 입력해주세요.");
        } else {
            //검색 로직 구현
        	DefaultListModel<String> model = new DefaultListModel<>();
        	
        	for(Map.Entry<String, String> entry : list.entrySet()) {
        		String key = entry.getKey();
        		String value = entry.getValue();
        		
        		//검색어가 키 또는 값에 포함되어 있으면 결과에 추가
        		if(key.contains(query.toUpperCase()) || value.contains(query)) {
        			model.addElement(key +" : "+value);
        		}
        	}
        	
        	if(model.size()>0) {
        		resultLabel.setText("검색 결과: ");
        		resultList.setModel(model);
        		setupResultList();
        		
        	    scroll.setVisible(true);
        	    
        	    resultPanel.revalidate();  // 레이아웃 새로고침
            	resultPanel.repaint();  // 화면 새로 고침
            	
        	}else {
        		resultLabel.setText("검색 결과가 없습니다.");
        		scroll.setVisible(false);
        	}
        }
        searchField.setText(""); // 검색 후 입력창 초기화
    }
	
	//JList 클릭 이벤트 리스너
	public void setupResultList() {
	    resultList.addListSelectionListener(e -> {
	        if (!e.getValueIsAdjusting()) {
	            String selectedValue = resultList.getSelectedValue();
	            if (selectedValue != null) {
	                String key = selectedValue.split(" : ")[0];  // 키값 추출
	                openPage(key);  // 해당 페이지 열기
	            }
	        }
	    });
	}
	
	//검색한 창 열기
	private void openPage(String key) {
		if(mainFrame != null) {
			mainFrame.openMenuByKey(key);
		}else {
			System.out.println("검색결과: Main 프레임 참조가 없습니다.");
		}
		resultLabel.setText("검색어를 입력해주세요.");		//검색 결과 창 초기화
		scroll.setVisible(false);
		this.dispose(); 		// 창 닫기
		instance = null;
	}
	
	//싱글톤 처리 - 한 번만 실행
	public static SearchMenu getInstance(Main main) {
		if(instance == null) {
			instance = new SearchMenu();
			mainFrame = main;
		}
		return instance;
	}
}
