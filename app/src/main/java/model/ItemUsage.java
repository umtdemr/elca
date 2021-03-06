package model;

import entities.ItemEntity;

public class ItemUsage {
    private ItemEntity item;
    private double kiloWattUsage;  // watt / 1000
    private double dailyUsage; // kilowatt * averageUsage
    private double weeklyUsage;  // dailyUsage * 7
    private double monthlyUsage;  // dailyUsage * 30

    public ItemUsage(ItemEntity item) {
        this.item = item;
        this.kiloWattUsage = item.getMaxWatt() / 1000;
        this.dailyUsage = this.kiloWattUsage * item.getAverageUsage();
        this.weeklyUsage = this.dailyUsage * 7;
        this.monthlyUsage = this.dailyUsage * 30;
    }

    public double getKiloWattUsage() {
        return kiloWattUsage;
    }
    public double getDailyUsage() {
        return dailyUsage;
    }
    public double getWeeklyUsage() {
        return weeklyUsage;
    }
    public double getMonthlyUsage() {
        return monthlyUsage;
    }

    public String getItemTitle() {
        return item.getTitle();
    }
}
