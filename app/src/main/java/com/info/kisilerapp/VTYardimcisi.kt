package com.info.kisilerapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class VTYardimcisi(context: Context) :
    SQLiteOpenHelper(context, "rehber.sqlite", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE kisiler (kisi_id INTEGER PRIMARY KEY AUTOINCREMENT,kisi_ad TEXT,kisi_tel TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS kisiler")//sorun varsa bu tabloyu sil
        onCreate(db)
    }
}