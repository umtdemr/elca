package model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dao.IItemDAO;
import entities.ItemEntity;

@Database(entities = {ItemEntity.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;
    public abstract IItemDAO getItemDAO();

    private static final String databaseName = "elca.db";

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase =
                    Room.databaseBuilder(
                            context,
                            AppDatabase.class,
                            databaseName
                    )
                    .allowMainThreadQueries()
                    .build();
        }
        return  appDatabase;
    }

}
