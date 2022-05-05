package model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String title;
    private float average_usage;
    private float max_watt;

   public Item(int id, String title, float average_usage, float max_watt) {
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

   public float getAverageUsage() {
       return average_usage;
   }
   public void setAverageUsage(float average_usage) {
       this.average_usage = average_usage;
   }

   public float getMaxWatt() {
       return max_watt;
   }
   public void setMaxWatt(float max_watt) {
      this.max_watt = max_watt;
   }
}
