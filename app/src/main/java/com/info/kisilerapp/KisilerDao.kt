package com.info.kisilerapp

import android.content.ContentValues
import android.content.Context
import android.widget.Toast

class KisilerDao() {
    fun kisiSil(vt: VTYardimcisi, kisi_id: Int) {
        val db = vt.writableDatabase
        db.delete("kisiler", "kisi_id=?", arrayOf(kisi_id.toString()))
        db.close()
    }


    fun kisiEkle(vt: VTYardimcisi, mContext: Context, kisi_ad: String, kisi_tel: String) {
        val db = vt.writableDatabase
        val values = ContentValues()
        values.put("kisi_ad", kisi_ad)
        values.put("kisi_tel", kisi_tel)
        val sonuc = db.insertOrThrow("kisiler", null, values)

        if (sonuc == (-1).toLong()) {
            Toast.makeText(mContext, "hata olustu eklemede", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun kisiGuncelle(vt: VTYardimcisi, kisi_id: Int, kisi_ad: String, kisi_tel: String) {
        val db = vt.writableDatabase
        val values = ContentValues()

        values.put("kisi_ad", kisi_ad)
        values.put("kisi_tel", kisi_tel)

        db.update("kisiler", values, "kisi_id=?", arrayOf(kisi_id.toString()))
        db.close()
    }

    fun tumKisiler(vt: VTYardimcisi): ArrayList<Kisiler> {
        val kisilerLİst = ArrayList<Kisiler>()

        val db = vt.writableDatabase

        val cursor = db.rawQuery("SELECT*FROM kisiler", null)

        while (cursor.moveToNext()) {
            val no = cursor.getInt(cursor.getColumnIndex("kisi_id"))
            val ad = cursor.getString(cursor.getColumnIndex("kisi_ad"))
            val tel = cursor.getString(cursor.getColumnIndex("kisi_tel"))

            val kisi = Kisiler(no, ad, tel)
            kisilerLİst.add(kisi)

        }
        return kisilerLİst
    }

    fun kisiAra(vt: VTYardimcisi,sozcuk:String): ArrayList<Kisiler> {
        val kisilerLİst = ArrayList<Kisiler>()

        val db = vt.writableDatabase

        val cursor = db.rawQuery("SELECT*FROM kisiler WHERE kisi_ad Like '%$sozcuk%'", null)

        while (cursor.moveToNext()) {
            val no = cursor.getInt(cursor.getColumnIndex("kisi_id"))
            val ad = cursor.getString(cursor.getColumnIndex("kisi_ad"))
            val tel = cursor.getString(cursor.getColumnIndex("kisi_tel"))

            val kisi = Kisiler(no, ad, tel)
            kisilerLİst.add(kisi)

        }
        return kisilerLİst
    }


}