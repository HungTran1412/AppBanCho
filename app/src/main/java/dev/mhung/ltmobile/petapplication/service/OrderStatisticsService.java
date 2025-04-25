package dev.mhung.ltmobile.petapplication.service;

import dev.mhung.ltmobile.petapplication.response.OrderResponse;

public class OrderStatisticsService {
    // Tính tổng tiền trung bình mỗi đơn
    public static double calculateAverageOrderAmount(long tongTien, int soDon) {
        if (soDon == 0) {
            return 0;
        }
        return tongTien / (double) soDon;
    }

    // Tính tỷ lệ thay đổi đơn hàng
    public static double calculateOrderChangeRate(int currentOrders, int previousOrders) {
        if (previousOrders == 0) {
            return 100; // Nếu không có đơn trước đó, coi như tăng 100%
        }
        return ((currentOrders - previousOrders) / (double) previousOrders) * 100;
    }

    // Tính tỷ lệ thay đổi doanh thu
    public static double calculateRevenueChangeRate(long currentRevenue, long previousRevenue) {
        if (previousRevenue == 0) {
            return 100; // Nếu không có doanh thu trước đó, coi như tăng 100%
        }
        return ((currentRevenue - previousRevenue) / (double) previousRevenue) * 100;
    }

    // Tạo kết quả thống kê dạng chữ hoặc số
    public static String getAverageOrderAmount(long tongTien, int soDon) {
        double avgOrderAmount = calculateAverageOrderAmount(tongTien, soDon);
        return "Trung bình mỗi đơn: " + avgOrderAmount;
    }

    public static String getOrderChangeRate(int currentOrders, int previousOrders) {
        double orderChangeRate = calculateOrderChangeRate(currentOrders, previousOrders);
        return "Tỷ lệ thay đổi đơn hàng: " + orderChangeRate + "%";
    }

    public static String getRevenueChangeRate(long currentRevenue, long previousRevenue) {
        double revenueChangeRate = calculateRevenueChangeRate(currentRevenue, previousRevenue);
        return "Tỷ lệ thay đổi doanh thu: " + revenueChangeRate + "%";
    }

    // Thống kê thông tin ngày, tháng, năm
    public static String getDailyOrders(int dailyOrders) {
        return "Số đơn trong ngày: " + dailyOrders;
    }

    public static String getMonthlyOrders(int monthlyOrders) {
        return "Số đơn trong tháng: " + monthlyOrders;
    }

    public static String getYearlyOrders(int yearlyOrders) {
        return "Số đơn trong năm: " + yearlyOrders;
    }

    public static String getDailyAmount(int dailyOrders) {
        return "Số đơn trong ngày: " + dailyOrders;
    }

    public static String getMonthlyAmount(int monthlyOrders) {
        return "Số đơn trong tháng: " + monthlyOrders;
    }

    public static String getYearlyAmount(int yearlyOrders) {
        return "Số đơn trong năm: " + yearlyOrders;
    }

    // Phương thức thực hiện thống kê và trả về kết quả dạng String
    public static String getStatistics(OrderResponse currentData, OrderResponse previousData,
                                       int dailyOrders, int monthlyOrders, int yearlyOrders) {
        long currentRevenue = currentData.getTongTien();
        int currentOrders = currentData.getSoDon();

        long previousRevenue = previousData != null ? previousData.getTongTien() : 0;
        int previousOrders = previousData != null ? previousData.getSoDon() : 0;

        String avgOrderAmount = getAverageOrderAmount(currentRevenue, currentOrders);
        String orderChangeRate = getOrderChangeRate(currentOrders, previousOrders);
        String revenueChangeRate = getRevenueChangeRate(currentRevenue, previousRevenue);

        // Thêm thống kê số đơn trong ngày, tháng, năm
        String dailyOrderStat = getDailyOrders(dailyOrders);
        String monthlyOrderStat = getMonthlyOrders(monthlyOrders);
        String yearlyOrderStat = getYearlyOrders(yearlyOrders);

        // Trả về chuỗi thống kê
        return avgOrderAmount + "\n" + orderChangeRate + "\n" + revenueChangeRate + "\n"
                + dailyOrderStat + "\n" + monthlyOrderStat + "\n" + yearlyOrderStat;
    }
}
