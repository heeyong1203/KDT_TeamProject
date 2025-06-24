package com.sinse.wms.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate {
    public static void main(String[] args) throws Exception {
        String dateStr = "2025-06-24";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = formatter.parse(dateStr);
        System.out.println(date);
    }
}