package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor {

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher;
    }

    public static boolean checkUsernameInvalidation(String username) {
        return false;
    }

    public static boolean checkPasswordInvalidation(String password) {
        return false;
    }

    public static boolean checkEmailInvalidation(String email) {
        return false;
    }

    public static boolean checkPhoneNumberInvalidation(String phoneNumber) {
        return false;
    }

    public static boolean checkNameInvalidation(String name) {
        return false;
    }

    public static boolean checkCreditInvalidation(long credit) {
        return false;
    }

    public static boolean checkPercentInvalidation(int percent) {
        return false;
    }

    public static boolean checkTypeInvalidation(String type) {
        return false;
    }


}
