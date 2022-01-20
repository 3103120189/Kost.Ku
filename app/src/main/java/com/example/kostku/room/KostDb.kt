package com.example.kostku.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kostku.MainActivity

@Database(
    entities = [Kost::class],
    version = 1
)
abstract class KostDb : RoomDatabase(){

    abstract fun kostDao() : KostDao

    companion object {

        @Volatile
        private var instance: KostDb? = null
        private val LOCK = Any()

        operator fun invoke(context: MainActivity) = instance ?: synchronized(LOCK) {
            instance ?: builDatabase(context).also {
                instance = it
            }
        }

        private fun builDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            KostDb::class.java,
            "kost12345.db"
        ).build()
    }
}