package dev.mhung.ltmobile.petapplication.model;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {
    public static String formatVND(long amount) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
        return currencyFormatter.format(amount);
    }
}
