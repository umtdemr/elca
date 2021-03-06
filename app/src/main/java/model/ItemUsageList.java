package model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entities.ItemEntity;

public class ItemUsageList {
    private double weeklyTotalUsage, dailyTotalUsage, monthlyTotalUsage;
    private double cheapPrice = 1.25;
    private double priceyPrice = 1.89;
    private ArrayList<ItemUsage> itemUsages = new ArrayList<ItemUsage>();

    public ItemUsageList(List<ItemEntity> items) {
        for (int i = 0; i < items.size(); i++) {
            ItemUsage itemUsage = new ItemUsage(items.get(i));
            itemUsages.add(itemUsage);
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

    public double getMonthlyInvoicePrice() {
        double total = 0;
        double totalUsageKW = getMonthlyTotalUsage();
        if (totalUsageKW - 240 > 0) {
            total += (totalUsageKW - 240) * priceyPrice;
            totalUsageKW = 240;
        }
        total += totalUsageKW * cheapPrice;
        return total;
    }

    public int getListSize() {
        return itemUsages.size();
    }

    public List<ItemUsage> getListItemUsageData() {
        return itemUsages;
    }

    public ItemUsage getItemUsageData(int position) {
        return itemUsages.get(position);
    }
}
