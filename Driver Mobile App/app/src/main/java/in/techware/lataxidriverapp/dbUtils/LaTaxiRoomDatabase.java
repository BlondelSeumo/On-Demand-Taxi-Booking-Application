package in.techware.lataxidriverapp.dbUtils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import in.techware.lataxidriverapp.dbUtils.dao.PathDao;
import in.techware.lataxidriverapp.dbUtils.entity.PathEntity;


/**
 * Created by Jemsheer K D on 02 January, 2018.
 * Package in.techware.lataxidriver.database
 * Project AFS_Bus_App
 */

@Database(entities = {PathEntity.class,}, version = 1)
public abstract class LaTaxiRoomDatabase extends RoomDatabase {


    public abstract PathDao pathDao();

    private static LaTaxiRoomDatabase INSTANCE;


    public static LaTaxiRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LaTaxiRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LaTaxiRoomDatabase.class, "path_database")
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}
