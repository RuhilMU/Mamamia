package com.example.mamamia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mamamia.R
import com.example.mamamia.database.ResepTable
import com.example.mamamia.databinding.ItemBookmarkBinding
import java.util.ArrayList
import com.example.mamamia.databinding.ItemResepBinding
import com.example.mamamia.network.ApiService
import com.squareup.picasso.Picasso

class BookmarkResepAdapter(
    private val deleteBookmark: (ResepTable) -> Unit,
    private val ResepDetail: (ResepTable) -> Unit
//    private val onBookmarkClicked: (ResepEntity) -> Unit,
//    private val isAdmin: Boolean = false,
//    private val onDeleteClicked: ((ResepEntity) -> Unit)? = null,
//    private val onEditClicked: ((ResepEntity) -> Unit)? = null
) : ListAdapter<ResepTable, BookmarkResepAdapter.ResepViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<ResepTable>() {
        override fun areItemsTheSame(oldItem: ResepTable, newItem: ResepTable): Boolean {
            return oldItem.id == newItem.id // Cek ID
        }

        override fun areContentsTheSame(oldItem: ResepTable, newItem: ResepTable): Boolean {
            return oldItem == newItem // Cek content
        }
    }

    inner class ResepViewHolder(private val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val ivResep: ImageView = itemView.findViewById(R.id.iv_resep)
        fun bind(resep: ResepTable) {
            with(binding) {
                tvNamaResep.text = resep.nama
                tvDeskripsiSingkat.text = resep.deskripsi
                tvPenulis.text = resep.penulis

                bookmark.setOnClickListener {
                    deleteBookmark(resep)
                }
                itemView.setOnClickListener {
                    ResepDetail(resep)
                }
                Picasso.get()
                    .load(resep.gambar)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(binding.ivResep)
//                bookmark.setOnClickListener {
//                    setImageResource(
//            if (data.isBookmarked) R.drawable.bookmark
//            else R.drawable.ic_bookmark
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
        val binding =
            ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResepViewHolder, position: Int) {
        holder.bind(getItem(position))
        val resep = getItem(position)
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

//    override fun getItemCount(): Int = resepList.size
}
