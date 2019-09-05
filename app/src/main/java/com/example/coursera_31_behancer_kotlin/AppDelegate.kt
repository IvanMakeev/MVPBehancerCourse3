package com.example.coursera_31_behancer_kotlin

import android.app.Application
import android.arch.persistence.room.Room
import com.example.coursera_31_behancer_kotlin.data.Storage
import com.example.coursera_31_behancer_kotlin.data.database.BehanceDatabase


/**
 * Created by Vladislav Falzan.
 */

class AppDelegate : Application() {

    lateinit var storage: Storage

    override fun onCreate() {
        super.onCreate()

        val database = Room.databaseBuilder(this, BehanceDatabase::class.java, "behance_database")
            .fallbackToDestructiveMigration()
            .build()

        storage = Storage(database.behanceDao)
    }
}
