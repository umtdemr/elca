package entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item")
public class ItemEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    @ColumnInfo(name = "average_usage")
    private double averageUsage;

    @ColumnInfo(name = "max_watt")
    private double maxWatt;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public ItemEntity(int id, String title, double averageUsage, double maxWatt) {
        this.id = id;
        this.title = title;
        this.averageUsage= averageUsage;
        this.maxWatt = maxWatt;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public double getAverageUsage() {
        return averageUsage;
    }
    public void setAverageUsage(double averageUsage) {
        this.averageUsage = averageUsage;
    }

    public double getMaxWatt() {
        return maxWatt;
    }
    public void setMaxWatt(double maxWatt) {
        this.maxWatt = maxWatt;
    }
}
