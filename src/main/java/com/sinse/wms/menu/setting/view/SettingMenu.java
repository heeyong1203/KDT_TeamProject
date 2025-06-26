package com.sinse.wms.menu.setting.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.content.BaseContentPage;

public class SettingMenu extends BaseContentPage{
	final int WIDTH = Config.CONTENT_BODY_WIDTH - Config.CONTENT_BODY_BORDER_WIDTH * 2-20;
	final int HEIGHT = Config.CONTENT_BODY_HEIGHT - Config.CONTENT_BODY_BORDER_HEIGHT * 2-30;
	
	JLabel title;
	//테마 설정
	JPanel p_theme;	
	JComboBox<String> cb_theme;
	
	//언어 및 지역 설정
	JPanel p_locale;
	JComboBox<String> cb_locale;
	
	//알림 및 로그 설정
	JPanel p_log;
	JRadioButton rb_log[];
	ButtonGroup group;
	JPanel p_radio;
	JButton bt_log;
	
	String[] themes = {"기본", "Nimbus"};
	String[] languages = {"한국어"};
	String[] radioName = {"info", "warning", "fine"};
 	
	public SettingMenu() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(50, 60, 30, 0));
		setBackground(new Color(250, 250, 255));
		setOpaque(false);
		
		//생성
		// 테마 설정 패널
		p_theme = createSectionPanel("🎨 테마 선택", new Color(240, 245, 250));
		cb_theme = new JComboBox<>(themes);
		JLabel themeLabel = new JLabel("테마를 선택하세요:");
		
		// 언어 설정 패널
		p_locale = createSectionPanel("🌍 언어 설정", new Color(245, 250, 240));
		cb_locale = new JComboBox<>(languages);
		JLabel localeLabel = new JLabel("언어를 선택하세요:");
		
		// 알림/로그 설정 패널
		p_log = createSectionPanel("🔔 알림 및 로그 설정", new Color(250, 245, 240));
		p_radio = new JPanel();
		p_radio.setLayout(new BoxLayout(p_radio, BoxLayout.Y_AXIS));
		rb_log = new JRadioButton[radioName.length];
		group = new ButtonGroup();
		bt_log = new JButton("로그 출력");
		
		
		//스타일
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		Font titleFont = new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 30);
		Font labelFont = new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 14);
		
		title = new JLabel("환경설정");
		title.setFont(titleFont);
		title.setForeground(Config.PRIMARY_COLOR);
		title.setAlignmentX(LEFT_ALIGNMENT);

		themeLabel.setFont(labelFont);
		themeLabel.setPreferredSize(new Dimension(WIDTH, 30));
		themeLabel.setAlignmentX(LEFT_ALIGNMENT);
		cb_theme.setAlignmentX(LEFT_ALIGNMENT);
		
		localeLabel.setFont(labelFont);
		localeLabel.setAlignmentX(LEFT_ALIGNMENT);
		cb_locale.setAlignmentX(LEFT_ALIGNMENT);
		
		p_radio.setBackground(new Color(250, 245, 240));
		bt_log.setAlignmentX(LEFT_ALIGNMENT);
        
        //이벤트 연결
        //os에 따라 지원되는 테마가 다르므로 지원되는 테마만 보여주기
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")) {		//윈도우일 때
        	cb_theme.addItem("Motif");
        	cb_theme.addItem("Windows");
        	cb_theme.addItem("Windows Classic");
        	
        } else if(os.contains("lin")) {		//리눅스일 때
        	cb_theme.addItem("Motif");
        } //그밖에... 맥
        
        //테마 콤보 박스 선택 이벤트
        cb_theme.addActionListener((e)->{
        	String selectedTheme = (String) cb_theme.getSelectedItem();
        	System.out.println(selectedTheme);
        	try {
				switch(selectedTheme) {
					case "기본":
		                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		                break;
		            case "Nimbus":
		                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		                break;
		            case "Motif":
		                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		                break;
		            case "Windows":
		                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		                break;
		            case "Windows Classic":
		                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		                break;
		            default:
		                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		        }
				
				//현재 프레임 전체에 다시 적용
				SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(cb_theme));
				JOptionPane.showMessageDialog(this, "테마를 변경하였습니다.");
				
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "테마 변경 중 오류발생: "+ex.getMessage());
			}
        });
        
        //언어 콤보박스 선택 이벤트
        cb_locale.addActionListener((e)->{
        	String selectedLanguage = (String) cb_locale.getSelectedItem();
        	if(selectedLanguage.equals("한국어")) {
        		JOptionPane.showMessageDialog(this, "현재는 한국어만 지원됩니다.");
        	}
        });
        
        //로그 출력
        for(int i=0; i<radioName.length; i++) {
			rb_log[i] = new JRadioButton(radioName[i]);
			rb_log[i].setAlignmentX(LEFT_ALIGNMENT);
			group.add(rb_log[i]);
			p_radio.add(rb_log[i]);
		}
		rb_log[0].setSelected(true);
		
		//출력 버튼 클릭 시
		bt_log.addActionListener((e)->{
			String selectedLevel = null;
			
			for(int i=0; i<rb_log.length; i++) {
				if(rb_log[i].isSelected()) {
					selectedLevel = radioName[i];
					break;
				}
			}
			
			//messge
			String logMessage = selectedLevel.toUpperCase() + "로그 출력 버튼을 통해 보여지는 로그입니다.";
			
			//로그 출력
		    switch (selectedLevel) {
		        case "info":
		            MyLogger.getLogger().log(logMessage);  // 여러 level 포함
		            break;
		        case "warning":
		            MyLogger.getLogger().warning(logMessage);
		            break;
		        case "fine":
		            MyLogger.getLogger().fine(logMessage);
		            break;
		        default:
		            System.out.println("알 수 없는 로그 레벨");
		    }
		    
		    //로그 핸들러 닫기
		    Logger logger = Logger.getLogger("mylogger");
		    for (Handler handler : logger.getHandlers()) {
		        handler.flush(); // 남은 로그 기록
		        handler.close(); // 파일 해제
		    }
		});
		
		// 부착
		add(title);
		add(Box.createVerticalStrut(15));
		
		p_theme.add(themeLabel);
		p_theme.add(Box.createVerticalStrut(5));
		p_theme.add(cb_theme);

		p_locale.add(localeLabel);
		p_locale.add(Box.createVerticalStrut(5));
		p_locale.add(cb_locale);

		p_log.add(p_radio);
		p_log.add(Box.createVerticalStrut(10));
		p_log.add(bt_log);
        
        add(Box.createVerticalStrut(15));
        add(p_theme);
        add(Box.createVerticalStrut(25));
        add(p_locale);
        add(Box.createVerticalStrut(25));
        add(p_log);
	}
	
	private JPanel createSectionPanel(String title, Color bgColor) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder(title));
		panel.setBackground(bgColor);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setMaximumSize(new Dimension(WIDTH, 150));
		return panel;
	}
}
