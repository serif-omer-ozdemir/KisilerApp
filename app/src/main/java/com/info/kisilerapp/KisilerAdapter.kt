package com.info.kisilerapp


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class KisilerAdapter(
    private val mContext: Context,
    private var kisilerLİste: List<Kisiler>,
    private val vt: VTYardimcisi
) :
    RecyclerView.Adapter<KisilerAdapter.Card_tasarim_tutucu>() {


    inner class Card_tasarim_tutucu(tasarim: View) : RecyclerView.ViewHolder(tasarim) {
        var textViewKisiBilgisi: TextView
        var imegeView: ImageView
        var cart: CardView

        init {
            textViewKisiBilgisi = tasarim.findViewById(R.id.textViewKisiBilgi)
            imegeView = tasarim.findViewById(R.id.imageViewMore)
            cart = tasarim.findViewById(R.id.card_tasarim)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Card_tasarim_tutucu {
        // cardview in eklendıgı  kısım

        val tsrm = LayoutInflater.from(mContext).inflate(R.layout.kisi_cart_tasarim, parent, false)

        return Card_tasarim_tutucu(tsrm)
    }

    override fun getItemCount(): Int {
        return kisilerLİste.size
    }


    override fun onBindViewHolder(holder: Card_tasarim_tutucu, position: Int) {

        val kisi = kisilerLİste[position]

        if (position % 2 == 0) {
            holder.cart.setBackgroundColor(Color.GREEN)
        }

        holder.textViewKisiBilgisi.text = "${kisi.kisi_ad}-${kisi.kisi_tel}"

        holder.imegeView.setOnClickListener {

            val popupIm = androidx.appcompat.widget.PopupMenu(mContext, holder.imegeView)

            popupIm.menuInflater.inflate(R.menu.pop_up_menu, popupIm.menu)

            popupIm.setOnMenuItemClickListener { ilgiliItem ->

                when (ilgiliItem.itemId) {
                    R.id.action_sil -> {
                        val sb = Snackbar.make(
                            holder.imegeView,
                            " ${kisi.kisi_ad} silinsin mi ?",
                            Snackbar.LENGTH_LONG
                        ).setAction("Evet") {
                            KisilerDao().kisiSil(vt, kisi.kisi_id)
                            kisilerLİste =
                                KisilerDao().tumKisiler(vt) //VERİ KUMESINDEN VERILERI TEKRAR EKLEYERERK YENILEDIM
                            notifyDataSetChanged()//VERILERI  ALDIKTAN SONRA ARAYUZUN YENILLENMESINI SAGLAR
                            Snackbar.make(
                                holder.imegeView,
                                "${kisi.kisi_ad} Silidi",
                                Snackbar.LENGTH_LONG
                            ).show()
                            notifyDataSetChanged() // renk duzenını korumak için

                        }
                        sb.setActionTextColor(Color.GREEN)
                        sb.setTextColor(Color.GREEN)
                        sb.show()
                        true
                    }

                    R.id.action_guncelle -> {
                        alertGoster(kisi)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            popupIm.show()
        }
    }

    fun alertGoster(kisi: Kisiler) { // ilgili kısı nesnesı lazım olucak

        val tsrim = LayoutInflater.from(mContext).inflate(R.layout.alert_tasarim, null)

        val ad = tsrim.findViewById(R.id.editTextAd) as EditText
        val tel = tsrim.findViewById(R.id.editTextTel) as EditText
        val nesne = AlertDialog.Builder(mContext)

        nesne.setTitle(" Kisi Güncelle ")

        ad.setText(kisi.kisi_ad)
        tel.setText(kisi.kisi_tel)


        nesne.setView(tsrim)
        nesne.setPositiveButton("Güncelle") { DialogInterface, i ->

            val alinan_veriAd = ad.text.toString().trim()
            val alinan_veriTel = tel.text.toString().trim()

            // guncel kısı ad ve tel burada oldugu için buraya yazdım
            KisilerDao().kisiGuncelle(vt, kisi.kisi_id, alinan_veriAd, alinan_veriTel)
            kisilerLİste = KisilerDao().tumKisiler(vt)
            notifyDataSetChanged()

            Toast.makeText(mContext, "${alinan_veriAd}-${alinan_veriTel}", Toast.LENGTH_SHORT)
                .show()
        }
        nesne.setNegativeButton("İptal") { DialogInterface, i ->
        }
        nesne.create().show()

    }
}