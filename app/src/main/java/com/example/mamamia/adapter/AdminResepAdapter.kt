package com.example.mamamia.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mamamia.R
import com.example.mamamia.admin.EditResepActivity
import com.example.mamamia.database.ResepEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import com.example.mamamia.databinding.ItemResepBinding
import com.example.mamamia.network.ApiService
import com.squareup.picasso.Picasso

class AdminResepAdapter(
    private val resepList: ArrayList<ResepEntity>,
    private val client: ApiService,
    private val onItemClicked: (ResepEntity) -> Unit,
//    private val onBookmarkClicked: (ResepEntity) -> Unit,
//    private val isAdmin: Boolean = false,
//    private val onDeleteClicked: ((ResepEntity) -> Unit)? = null,
//    private val onEditClicked: ((ResepEntity) -> Unit)? = null
) : RecyclerView.Adapter<AdminResepAdapter.ResepViewHolder>() {

    inner class ResepViewHolder(private val binding: ItemResepBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivResep: ImageView = itemView.findViewById(R.id.iv_resep)
        fun bind(resep: ResepEntity) {
            with(binding) {
                tvNamaResep.text = resep.nama
                tvDeskripsiSingkat.text = resep.deskripsi
                tvPenulis.text = resep.penulis

                itemView.setOnClickListener {
                    onItemClicked(resep)
                }

                Picasso.get()
                    .load(resep.gambar)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(binding.ivResep)

                ivEdit.setOnClickListener {
                    val intentEdit = Intent(itemView.context, EditResepActivity::class.java)
                    intentEdit.putExtra("id", resep.id)
                    intentEdit.putExtra("nama", resep.nama)
                    intentEdit.putExtra("deskripsi", resep.deskripsi)
                    intentEdit.putExtra("penulis", resep.penulis)
                    intentEdit.putExtra("ingredients", resep.ingredients.joinToString(separator = "\n") { "- $it" })
                    intentEdit.putExtra("steps", resep.steps.joinToString(separator = "\n") { "- $it" })
                    intentEdit.putExtra("duration", resep.duration)
                    intentEdit.putExtra("servings", resep.servings)
                    itemView.context.startActivity(intentEdit)
                }
            }
        }
    }
//        val tvNamaResep: TextView = itemView.findViewById(R.id.tv_nama_resep)
//        val tvDeskripsiSingkat: TextView = itemView.findViewById(R.id.tv_deskripsi_singkat)
//        val tvPenulis: TextView = itemView.findViewById(R.id.tv_penulis)
//        val bookmarkIcon: ImageView = itemView.findViewById(R.id.bookmark)
//        val editIcon: ImageView = itemView.findViewById(R.id.iv_edit)
//        val deleteIcon: ImageView = itemView.findViewById(R.id.iv_delete)
//        val ivResep: ImageView = itemView.findViewById(R.id.iv_resep)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResepViewHolder {
        val binding = ItemResepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResepViewHolder, position: Int) {
        holder.bind(resepList[position])
        val resep = resepList[position]
        Picasso.get()
            .load(resep.gambar)
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(holder.ivResep)
    }

//        // Bind data ke ViewHolder
//        holder.tvNamaResep.text = resep.nama
//        holder.tvDeskripsiSingkat.text = resep.deskripsi
//        holder.tvPenulis.text = resep.penulis
//
//        // Muat gambar (dengan validasi null/empty)
//        val imageUrl = resep.imageUrl
//        if (imageUrl != null) {
//            if (imageUrl.isNotEmpty()) {
//                Picasso.get()
//                    .load(imageUrl)
//                    .placeholder(R.drawable.logo)
//                    .error(R.drawable.logo)
//                    .into(holder.ivResep)
//            } else {
//                holder.ivResep.setImageResource(R.drawable.logo)
//            }
//        }
//
//        // Update ikon bookmark
//        holder.bookmarkIcon.setImageResource(
//            if (resep.isBookmarked) R.drawable.bookmark
//            else R.drawable.ic_bookmark
//        )
//
//        // Tampilkan ikon edit dan hapus hanya untuk admin
//        if (isAdmin) {
//            holder.editIcon.visibility = View.VISIBLE
//            holder.deleteIcon.visibility = View.VISIBLE
//            holder.bookmarkIcon.visibility = View.GONE
//            holder.editIcon.setOnClickListener { onEditClicked?.invoke(resep) }
//            holder.deleteIcon.setOnClickListener {
//                onDeleteClicked?.invoke(resep)
//            }
//        } else {
//            holder.editIcon.visibility = View.GONE
//            holder.deleteIcon.visibility = View.GONE
//        }
//
//        // Event untuk bookmark
//        holder.bookmarkIcon.setOnClickListener {
//            onBookmarkClicked(resep)
//        }
//
//        // Event untuk item view
//        holder.itemView.setOnClickListener {
//            onItemClicked(resep)
//        }
//    }

    override fun getItemCount(): Int = resepList.size

    // Fungsi untuk memperbarui data
    fun updateData(newList: List<ResepEntity>) {
        resepList.clear()
        resepList.addAll(newList)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        resepList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, resepList.size)
    }
}

