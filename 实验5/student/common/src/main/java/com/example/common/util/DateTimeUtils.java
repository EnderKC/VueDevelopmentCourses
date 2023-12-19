package com.example.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * 日期时间工具类
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class DateTimeUtils {

    /**
     * HH:mm:ss格式
     */
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * yyyy-MM格式
     */
    public static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    /**
     * yyMMdd格式
     */
    public static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    /**
     * yyyy-MM-dd格式
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * yyMMddHHmmss格式
     */
    public static final DateTimeFormatter SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    /**
     * yyyy-MM-dd HH:mm:ss格式
     */
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * yyyy-MM-dd HH:mm:ss SSS格式
     */
    public static final DateTimeFormatter LONG_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");

    /**
     *  返回当前的日期
     *
     * @return java.time.LocalDate
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     *  返回当前时间
     *
     * @return java.time.LocalTime
     */
    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    /**
     *  返回当前日期时间
     *
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     *  以格式"yyyy-MM-dd"返回日期
     *
     * @return java.lang.String
     */
    public static String getCurrentDateStr() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     *  以格式"yyMMdd"返回日期
     *
     * @return java.lang.String
     */
    public static String getCurrentShortDateStr() {
        return LocalDate.now().format(SHORT_DATE_FORMATTER);
    }

    /**
     *  以格式"yyyy-MM"返回日期
     *
     * @return java.lang.String
     */
    public static String getCurrentMonthStr() {
        return LocalDate.now().format(YEAR_MONTH_FORMATTER);
    }

    /**
     *  以格式"yyyy-MM-dd HH:mm:ss"返回日期时间
     *
     * @return java.lang.String
     */
    public static String getCurrentDateTimeStr() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    /**
     *  以格式"yyyy-MM-dd HH:mm:ss SSS"返回日期时间
     *
     * @return java.lang.String
     */
    public static String getCurrentLongDateTimeStr() {
        return LocalDateTime.now().format(LONG_DATETIME_FORMATTER);
    }

    /**
     *  以格式"yyMMddHHmmss"返回日期时间
     *
     * @return java.lang.String
     */
    public static String getCurrentShortDateTimeStr() {
        return LocalDateTime.now().format(SHORT_DATETIME_FORMATTER);
    }

    /**
     *  以格式"HHmmss"返回时间
     *
     * @return java.lang.String
     */
    public static String getCurrentTimeStr() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    /**
     *  取当前日期字符串
     *
     * @param pattern 日期格式
     * @return java.lang.String
     */
    public static String getCurrentDateStr(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     *  取当前日期时间字符串
     *
     * @param pattern 日期时间格式
     * @return java.lang.String
     */
    public static String getCurrentDateTimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     *  取当前时间字符串
     *
     * @param pattern 时间格式
     * @return java.lang.String
     */
    public static String getCurrentTimeStr(String pattern) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     *  把字符串格式化为日期
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return java.time.LocalDate
     */
    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     *  把字符串格式化为日期时间
     *
     * @param dateTimeStr 日期时间字符串
     * @param pattern 日期格式
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     *  把字符串格式化为时间
     *
     * @param timeStr 时间字符串
     * @param pattern 时间格式
     * @return java.time.LocalTime
     */
    public static LocalTime parseLocalTime(String timeStr, String pattern) {
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     *  把日期格式化为字符串
     *
     * @param date 日期
     * @param pattern 日期字符串格式
     * @return java.lang.String
     */
    public static String formatLocalDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     *  把日期时间格式化为字符串
     *
     * @param datetime 日期时间
     * @param pattern 日期时间字符串格式
     * @return java.lang.String
     */
    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        return datetime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     *  把时间格式化为字符串
     *
     * @param time 时间
     * @param pattern 时间字符串格式
     * @return java.lang.String
     */
    public static String formatLocalTime(LocalTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     *  把yyyy-MM-dd格式的字符串转换为日期
     *
     * @param dateStr 日期字符串
     * @return java.time.LocalDate
     */
    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     *  把yyyy-MM-dd HH:mm:ss格式的字符串转换为日期时间
     *
     * @param dateTimeStr 日期时间字符串
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    /**
     *  把yyyy-MM-dd HH:mm:ss SSS格式的字符串转换为日期时间
     *
     * @param longDateTimeStr 日期时间字符串
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime parseLongLocalDateTime(String longDateTimeStr) {
        return LocalDateTime.parse(longDateTimeStr, LONG_DATETIME_FORMATTER);
    }

    /**
     *  把HH:mm:ss格式的字符串转换为时间
     *
     * @param timeStr 时间字符串
     * @return java.time.LocalTime
     */
    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    /**
     *  把日期转换为yyyy-MM-dd格式的字符串
     *
     * @param date 要转换的日期
     * @return java.lang.String
     */
    public static String formatLocalDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     *  把日期时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param datetime 要转换的日期时间
     * @return java.lang.String
     */
    public static String formatLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DATETIME_FORMATTER);
    }

    /**
     *  把时间转换为HH:mm:ss格式的字符串
     *
     * @param time 要转换的时间
     * @return java.lang.String
     */
    public static String formatLocalTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }

    /**
     *  功能描述: 日期相隔秒
     *
     * @param startDateTime 起始日期时间
     * @param endDateTime   终止日期时间
     * @return long
     */
    public static long periodHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime).get(ChronoUnit.SECONDS);
    }

    /**
     *  日期相隔天数
     *
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @return long
     */
    public static long periodDays(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }

    /**
     *  日期相隔周数
     *
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @return long
     */
    public static long periodWeeks(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.WEEKS);
    }

    /**
     *  日期相隔月数
     *
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @return long
     */
    public static long periodMonths(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.MONTHS);
    }

    /**
     *  日期相隔年数
     *
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @return long
     */
    public static long periodYears(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.YEARS);
    }

    /**
     *  是否当天
     *
     * @param date 测试日期
     * @return boolean
     */
    public static boolean isToday(LocalDate date) {
        return getCurrentLocalDate().equals(date);
    }

    /**
     *  把日期时间转毫秒数
     *
     * @param dateTime 日期时间
     * @return java.lang.Long
     */
    public static Long toEpochMilli(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     *  判断是否为闰年
     *
     * @param localDate 当前日期
     * @return boolean
     */
    public static boolean isLeapYear(LocalDate localDate) {
        return localDate.isLeapYear();
    }

    /**
     * 判断当前时间是昼间还是夜间
     *
     * @param currentTime 当前时间
     * @param rangeOfDaytime 昼间时间段的范围，如"06:00:00-18:00:00"
     * @return short  昼间返回0，夜间返回1
     */
    public static short getDaytimeOrNight(LocalDateTime currentTime,String rangeOfDaytime)
    {
        String[] range = rangeOfDaytime.split("-");
        String strCurrentTime=formatLocalDateTime(currentTime,"HH:mm:ss");
        if(strCurrentTime.compareTo(range[0].trim())>=0 && strCurrentTime.compareTo(range[1].trim())<=0){
            return 0;
        }
        return 1;
    }
    /**
     * 判断日期时间字符串是否正确
     *
     * @param dateStr 日期时间字符串
     * @return boolean
     */
    public static boolean isValid(String dateStr)
    {
        DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            formatPattern.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
    /**
     * 判断日期字符串格式是否正确
     *
     * @param dateStr  日期字符串
     * @param pattern  日期格式
     * @return boolean
     */
    public static boolean isValid(String dateStr,String pattern)
    {
        DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern(pattern);
        try {
            formatPattern.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }


}
