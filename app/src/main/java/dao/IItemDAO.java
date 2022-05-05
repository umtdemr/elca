package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.ItemEntity;

@Dao
public interface IItemDAO {
    @Insert
    void insertItem(ItemEntity item);

    @Update
    void updateItem(ItemEntity item);

    @Delete
    void deleteItem(ItemEntity item);

    @Query("SELECT * FROM item")
    List<ItemEntity> loadAllItems();

    @Query("SELECT * FROM item WHERE id=:id")
    List<ItemEntity> loadItemById(int id);
}
