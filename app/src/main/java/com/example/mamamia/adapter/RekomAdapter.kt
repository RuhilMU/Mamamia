package com.example.mamamia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mamamia.R
import com.example.mamamia.database.ResepTable
import com.squareup.picasso.Picasso

class RekomAdapter(
    private var rekomendasiList: List<ResepTable>,
    private val onItemClicked: (ResepTable) -> Unit
) : RecyclerView.Adapter<RekomAdapter.RekomendasiViewHolder>() {

    inner class RekomendasiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaResep: TextView = itemView.findViewById(R.id.tv_nama_resep)
        val tvPenulisResep: TextView = itemView.findViewById(R.id.tv_penulis_resep)
        val ivResep: ImageView = itemView.findViewById(R.id.iv_resep)

        fun bind(resep: ResepTable) {
            tvNamaResep.text = resep.nama
            tvPenulisResep.text = resep.penulis

            // Muat gambar jika ada URL
//            if (resep.imageUrl.isNotEmpty()) {
//                Picasso.get()
//                    .load(resep.imageUrl)
//                    .placeholder(R.drawable.logo)
//                    .error(R.drawable.logo)
//                    .into(ivResep)
//            } else {
//                ivResep.setImageResource(R.drawable.logo) // Gambar default
//            }

            itemView.setOnClickListener { onItemClicked(resep) }

            Picasso.get()
                .load(resep.gambar)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(ivResep)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RekomendasiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rekomen, parent, false)
        return RekomendasiViewHolder(view)
    }

    override fun onBindViewHolder(holder: RekomendasiViewHolder, position: Int) {
        holder.bind(rekomendasiList[position])
        val resep = rekomendasiList[position]
        Picasso.get()
            .load(resep.gambar)
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(holder.ivResep)
    }

    override fun getItemCount(): Int = rekomendasiList.size

    fun updateData(newData: List<ResepTable>) {
        rekomendasiList = newData
        notifyDataSetChanged()
    }
}
