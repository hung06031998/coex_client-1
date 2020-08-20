package com.upit.coex.user.repository.model.local;


import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by chien.lx on 3/9/2020.
 */
@Database(entities = {Guest.class}, version = 1, exportSchema = false)
public abstract class DatabaseGuest  extends RoomDatabase {
    public abstract DaoQuerry daoQuerry() ;
}
