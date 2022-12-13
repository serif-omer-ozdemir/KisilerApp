package com.info.kisilerapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.info.kisilerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var tasarim: ActivityMainBinding
    private lateinit var kisilerListesi: ArrayList<Kisiler>
    private lateinit var adapter:KisilerAdapter
    private lateinit var vt: VTYardimcisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tasarim = ActivityMainBinding.inflate(layoutInflater)
        setContentView(tasarim.root)

        tasarim.toolbar.title = "KİSİLER APP"
        setSupportActionBar(tasarim.toolbar)

        tasarim.rv.setHasFixedSize(true)
        tasarim.rv.layoutManager = LinearLayoutManager(this)//alt alta olucak

        vt = VTYardimcisi(this)

        tumKisileriAl()

        tasarim.fab.setOnClickListener {
            alertGoster()
        }
    }


    fun alertGoster() {
        val tsrim = LayoutInflater.from(this).inflate(R.layout.alert_tasarim, null)

        val ad = tsrim.findViewById(R.id.editTextAd) as EditText
        val tel = tsrim.findViewById(R.id.editTextTel) as EditText
        val nesne = AlertDialog.Builder(this)
        nesne.setTitle("Yeni Kisi Ekle ")
        nesne.setIcon(R.drawable.ekle2)
        nesne.setView(tsrim)
        nesne.setPositiveButton("Ekle") { DialogInterface, i ->

            val alinan_veriAd = ad.text.toString().trim()
            val alinan_veriTel = tel.text.toString().trim()

            KisilerDao().kisiEkle(vt, this, alinan_veriAd, alinan_veriTel)
            tumKisileriAl()//vt yenıleyeyım kı gorebıleyım
            Toast.makeText(
                applicationContext,
                "${alinan_veriAd}-${alinan_veriTel}",
                Toast.LENGTH_SHORT
            ).show()
        }
        nesne.setNegativeButton("İptal") { DialogInterface, i ->

        }

        nesne.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        aramaYap(query.toString())
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        aramaYap(newText.toString())
        return true
    }

    override fun onCreateOptionsMenu(nesne: Menu?): Boolean {

        menuInflater.inflate(R.menu.toolbar_menu, nesne)

        val item = nesne?.findItem(R.id.action_ara)
        val ara = item?.actionView as SearchView
        ara.queryHint="Ara"
        ara.setOnQueryTextListener(this)//yanı main aktıvıtıye ekle

         ara.queryHint="Ara"

        return true
    }

    fun tumKisileriAl() {
        kisilerListesi = ArrayList()
        kisilerListesi = KisilerDao().tumKisiler(vt)
        adapter = KisilerAdapter(this, kisilerListesi,vt)
        tasarim.rv.adapter = adapter
    }
    fun aramaYap(sozcuk:String) {
        kisilerListesi = ArrayList()
        kisilerListesi = KisilerDao().kisiAra(vt,sozcuk)
        adapter = KisilerAdapter(this, kisilerListesi,vt)
        tasarim.rv.adapter = adapter
    }
}