package model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String title;
    private double average_usage;
    private double max_watt;

   public Item(int id, String title, double average_usage, double max_watt) {
       this.id = id;
       this.title = title;
       this.average_usage = average_usage;
       this.max_watt = max_watt;
   }

   public int getId() {
       return id;
   }
   public void setId(int id) {
       this.id = id;
   }

   public String getTitle() {
       return title;
   }
   public void setTitle(String title) {
       this.title = title;
   }

   public double getAverageUsage() {
       return average_usage;
   }
   public void setAverageUsage(double average_usage) {
       this.average_usage = average_usage;
   }

   public double getMaxWatt() {
       return max_watt;
   }
   public void setMaxWatt(double max_watt) {
      this.max_watt = max_watt;
   }
}
