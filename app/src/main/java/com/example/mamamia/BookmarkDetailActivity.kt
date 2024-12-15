package com.example.mamamia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mamamia.databinding.ActivityBookmarkDetailBinding
import com.squareup.picasso.Picasso

class BookmarkDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gambar = intent.getStringExtra("gambar")
        if (!gambar.isNullOrEmpty()) {
            Picasso.get()
                .load(gambar)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(binding.ivResep)
        } else {
            binding.ivResep.setImageResource(R.drawable.logo)
        }

        with(binding){
            val nama = intent.getStringExtra("nama")
            val penulis = intent.getStringExtra("penulis")
            val ingredients = intent.getStringExtra("ingredients")
            val steps = intent.getStringExtra("steps")
            val duration = intent.getStringExtra("duration")
            val servings = intent.getStringExtra("servings")

            tvResep.text = nama
            tvPenulis.text = penulis
            tvBahanContent.text = ingredients
            tvLangkahContent.text = steps
            tvDuration.text = duration
            tvServings.text = servings

            ivBack.setOnClickListener {
                finish()
            }
//            ivHome.setOnClickListener {
//                finish()
//            }
        }
    }
}