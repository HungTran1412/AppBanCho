package dev.mhung.ltmobile.petapplication.model;

public class CheckEmail {
    public static boolean emailCheck(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
}
