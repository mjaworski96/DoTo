package org.mjaworski.backend.utils;

public class ValidationUtils {
    public static boolean isValid(String str, int minLength, int maxLength) {
        if (str == null)
            return false;
        if (str.length() < minLength)
            return  false;
        if (str.length() > maxLength) {
            return false;
        }
        return true;
    }
    public static boolean isValid(String str, int maxLength) {
        if(str == null)
            return true;
        if (str.length() > maxLength) {
            return false;
        }
        return true;
    }
}
