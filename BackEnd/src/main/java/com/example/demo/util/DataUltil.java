package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class DataUltil {

    private static final Logger log = LoggerFactory.getLogger(DataUltil.class);

    public static HashMap<String, Object> setData(String statusCode, Object data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("statusCode", statusCode);
        map.put("data", data);
        return map;
    }

    public static boolean isNullObject(Object obj1) {
        if (obj1 == null) {
            return true;
        }
        if (obj1 instanceof String) {
            return isNullOrEmpty(obj1.toString());
        }
        if (obj1 instanceof List) {
            return ((List) obj1).isEmpty();
        }
        return false;
    }

    public static boolean isNotNullObject(Object obj1) {
        if (obj1 != null) {
            if (obj1 instanceof String) {
                return !isNullOrEmpty(obj1.toString());
            }
            if (obj1 instanceof List) {
                return !((List) obj1).isEmpty();
            }
            return true; // Trả về true nếu đối tượng không thuộc trường hợp trên.
        }
        return false; // Trả về false nếu đối tượng là null.
    }


    public static boolean isNullOrEmpty(CharSequence cs) {
        return nullOrEmpty(cs);
    }

    public static boolean nullOrEmpty(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String safeToString(Object obj1) {
        return safeToString(obj1, "");
    }
    public static String safeToString(Object obj1, String defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }

        return obj1.toString();
    }

    public static Double parseToDouble(Object value) {
        return parseToDouble(value, null);
    }

    public static Double parseToDouble(Object value, Double defaultVal) {
        try {
            if (value == null) {
                return defaultVal;
            }
            String str = parseToString(value);
            if (nullOrEmpty(str)) {
                return defaultVal;
            }
            return Double.parseDouble(str);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return defaultVal;
        }
    }

    public static String parseToString(Object value) {
        return parseToString(value, "");
    }

    public static String parseToString(Object value, String defaultVal) {
        try {
            if (value == null) {
                return defaultVal;
            }
            return String.valueOf(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return defaultVal;
        }
    }

    public static Long parseToLong(Object value) {
        return parseToLong(value, null);
    }

    public static Long parseToLong(Object value, Long defaultVal) {
        try {
            if (value == null) {
                return defaultVal;
            }
            String str = parseToString(value);
            if (nullOrEmpty(str)) {
                return defaultVal;
            }
            return Long.parseLong(str);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return defaultVal;
        }
    }
}
