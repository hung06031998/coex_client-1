package com.upit.coex.user.repository.model.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

/**
 * Created by chien.lx on 3/9/2020.
 */

@Dao
public interface DaoQuerry {
    @Insert
    void insertGuestUser (Guest guest);

    @Insert
    void insertMultipleMovies (List<Guest> guestList);

    @Query("SELECT * FROM Guest WHERE guestEmail = :guestEmail")
    Guest fetchOneGuestbyEmail (String guestEmail);

    @Update
    void updateMovie (Guest guest);

    @Delete
    void deleteMovie (Guest guest);
}
