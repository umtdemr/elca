package model;

import java.util.List;

import entities.ItemEntity;

public class ItemUsageList {
    private double monthlyTotalUsage;
    private List<ItemUsage> itemUsages;

    public ItemUsageList(List<ItemEntity> items) {
       for (int i = 0; i < items.size(); i++) {
           itemUsages.add(new ItemUsage(items.get(i)));
       }
    }

    public double getMonthlyTotalUsage() {
        monthlyTotalUsage = 0;
        for (int i = 0; i < itemUsages.size(); i++) {
            ItemUsage itemUsage = itemUsages.get(i);
        }
        return monthlyTotalUsage;
    }
}
