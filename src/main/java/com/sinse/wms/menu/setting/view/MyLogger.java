package com.sinse.wms.menu.setting.view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import com.sinse.wms.common.util.GetSaveFilePath;

public class MyLogger {
	//String 기준으로 Logger 클래스 인스턴스 할당
    private Logger logger = Logger.getLogger("mylogger");
    private static MyLogger instance = new MyLogger(GetSaveFilePath.saveFilePath() + ".txt");

    private MyLogger(String path) {
    	//path 기본 경로 처리 - 다운로드
        if (path == null) {
            path = System.getProperty("user.home") + "/Downloads/default-log.txt";
        }

        try {
            logger.setUseParentHandlers(false);  // 콘솔 출력 방지
            logger.setLevel(Level.ALL);

            // FileOutputStream 직접 사용 -> .lck 안 생김
            FileOutputStream fos = new FileOutputStream(path, true);  // append true
            StreamHandler handler = new StreamHandler(fos, new SimpleFormatter()) {
                @Override
                public synchronized void publish(LogRecord record) {
                    super.publish(record);
                    flush();  // 자동 flush
                }
            };

            handler.setLevel(Level.ALL);
            logger.addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MyLogger getLogger() {
        return instance;
    }

    public void log(String msg) {
        logger.finest(msg);
        logger.finer(msg);
        logger.fine(msg);
        logger.config(msg);
        logger.info(msg);
        logger.warning(msg);
        logger.severe(msg);
    }

    public void fine(String msg) {
        logger.fine(msg);
    }

    public void warning(String msg) {
        logger.warning(msg);
    }
}
