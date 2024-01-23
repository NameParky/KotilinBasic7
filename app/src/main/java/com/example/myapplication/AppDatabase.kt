package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        // 싱글턴 패턴으로 인스턴스를 한번만 생성.
        fun getDatabase(context: Context): AppDatabase?{
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database")
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }
    }
}