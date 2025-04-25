/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class DatetimeUtil {

    public static String getCurrentDateAndTime() {
        DateTimeFormatter dtm = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtm.format(now);
    }

    public static LocalDateTime getCurrentDateAndTimeLocal() {
        LocalDateTime now = LocalDateTime.now();
        return now;
    }

    public static String getCurrentDate() {
        DateTimeFormatter dtm = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return dtm.format(now);
    }

    public static String getCurrentTime() {
        DateTimeFormatter dtm = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtm.format(now);
    }

    public static String convertDatetimeFormat(String date) {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            d = sdf.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        sdf.applyPattern("dd/MM/yyyy HH:mm:ss");
        return sdf.format(d);
    }
}
