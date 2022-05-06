package model;

import java.util.List;

import entities.ItemEntity;

public class ItemUsageList {
    private double  weeklyTotalUsage, dailyTotalUsage, monthlyTotalUsage;
    private List<ItemUsage> itemUsages;

    public ItemUsageList(List<ItemEntity> items) {
       for (int i = 0; i < items.size(); i++) {
           itemUsages.add(new ItemUsage(items.get(i)));
       }
    }
    public double getDailyTotalUsage() {
        dailyTotalUsage = 0;
        for (int i = 0; i < itemUsages.size(); i++) {
            dailyTotalUsage += itemUsages.get(i).getDailyUsage();
        }
        return dailyTotalUsage;
    }
    public double getWeeklyTotalUsage() {
        weeklyTotalUsage = 0;
        for (int i = 0; i < itemUsages.size(); i++) {
            weeklyTotalUsage += itemUsages.get(i).getWeeklyUsage();
        }
        return weeklyTotalUsage;
    }
    public double getMonthlyTotalUsage() {
        monthlyTotalUsage = 0;
        for (int i = 0; i < itemUsages.size(); i++) {
            monthlyTotalUsage += itemUsages.get(i).getMonthlyUsage();
        }
        return monthlyTotalUsage;
    }

}
