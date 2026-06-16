package ua.ukma.service;

public class PasswordValidator {

    // Чи підходяща довжина
    public boolean isValidLength(String password) {
        if (password == null) return false;
        return password.length() >= 8 && password.length() <= 25;
    }

    // Кількість спецсимволів у паролі
    public int countSpecialCharacters(String password) {
        if (password == null) return 0;
        int count = 0;
        String specialChars = "!@#$%^&*()-_=+";
        for (char ch : password.toCharArray()) {
            if (specialChars.contains(String.valueOf(ch))) {
                count++;
            }
        }
        return count;
    }
}
