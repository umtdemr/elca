package model;

import entities.ItemEntity;

public class ItemUsage {
    private ItemEntity item;
    private double kiloWattUsage;  // 1000 / watt
    private double dailyUsage; // kilowatt * averageUsage
    private double weeklyUsage;  // dailyUsage * 7
    private double monthlyUsage;  // dailyUsage * 7

    public ItemUsage(ItemEntity item) {
        this.item = item;
        this.kiloWattUsage = 1000 / item.getMaxWatt();
        this.dailyUsage = this.kiloWattUsage * item.getAverageUsage();
        this.weeklyUsage = this.dailyUsage * 7;
        this.monthlyUsage = this.dailyUsage * 30;
    }

    private double getKiloWattUsage() {
        return kiloWattUsage;
    }
    private double getDailyUsage() {
        return dailyUsage;
    }
    private double getWeeklyUsage() {
        return weeklyUsage;
    }
    private double getMonthlyUsage() {
        return monthlyUsage;
    }
}
