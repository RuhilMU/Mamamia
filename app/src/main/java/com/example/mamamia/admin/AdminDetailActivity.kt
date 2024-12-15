package com.example.mamamia.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mamamia.R
import com.example.mamamia.database.ResepDao
import com.example.mamamia.database.ResepEntity
import com.example.mamamia.databinding.ActivityAdminDetailBinding
import com.example.mamamia.network.ApiClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminDetailBinding
    private lateinit var resepDao : ResepDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
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
        with(binding) {
            val nama = intent.getStringExtra("nama")
            val deskripsi = intent.getStringExtra("deskripsi")
            val penulis = intent.getStringExtra("penulis")
            val ingredients = intent.getStringExtra("ingredients")
            val steps = intent.getStringExtra("steps")
            val duration = intent.getStringExtra("duration")
            val servings = intent.getStringExtra("servings")

            tvResep.text = nama
            tvDeskripsi.text = deskripsi
            tvPenulis.text = penulis
            tvBahanContent.text = ingredients?.replace(", ", "\n")
            tvLangkahContent.text = steps?.replace(", ", "\n")
            tvDuration.text = duration
            tvServings.text = servings
            ivBack.setOnClickListener {
                finish()
            }
            ivEdit.setOnClickListener {
                val intent = Intent(this@AdminDetailActivity, EditResepActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("nama", nama)
                intent.putExtra("deskripsi", deskripsi)
                intent.putExtra("penulis", penulis)
                intent.putExtra("ingredients", ingredients)
                intent.putExtra("steps", steps)
                intent.putExtra("duration", duration)
                intent.putExtra("servings", servings)
                intent.putExtra("gambar", gambar)
                startActivity(intent)
            }
            ivDelete.setOnClickListener {
                // Pastikan id tidak null
                val resepId = id ?: return@setOnClickListener // Jika id null, keluar dari method

                // Memanggil API untuk menghapus resep
                ApiClient.client.deleteResep(resepId).enqueue(object : Callback<ResepEntity> {
                    override fun onResponse(call: Call<ResepEntity>, response: Response<ResepEntity>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@AdminDetailActivity,
                                "Resep berhasil dihapus",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Kembali ke activity sebelumnya setelah penghapusan berhasil
                            finish()
                        } else {
                            // Jika response tidak berhasil, tampilkan error
                            Log.e("API Error", "Response not successful or body is null")
                            Toast.makeText(
                                this@AdminDetailActivity,
                                "Gagal menghapus resep",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResepEntity>, t: Throwable) {
                        // Menangani error koneksi API
                        Toast.makeText(this@AdminDetailActivity, "Koneksi error", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail)
//
//        isAdmin = intent.getBooleanExtra("IS_ADMIN", false)
//
//        // Toolbar Elements
//        val imgBack: ImageView = findViewById(R.id.iv_back)
//        val imgHome: ImageView = findViewById(R.id.iv_home)
//        val tvToolbarTitle: TextView = findViewById(R.id.tv_resep)
//
//        // Recipe Detail Elements
//        val imgResep: ImageView = findViewById(R.id.iv_resep)
//        val tvBahan: TextView = findViewById(R.id.tv_bahan_content)
//        val tvPenulis: TextView = findViewById(R.id.tv_penulis)
//        val tvDuration: TextView = findViewById(R.id.tv_duration)
//        val tvServings: TextView = findViewById(R.id.tv_servings)
//        val tvLangkah: TextView = findViewById(R.id.tv_langkah_content)
//        val imgBookmark: ImageView = findViewById(R.id.img_bookmark)
//
//        // Get recipe ID from intent
//        val resepId = intent.getIntExtra("RESEP_ID", -1)
//
//        if (resepId != -1) {
//            // Observe specific recipe by ID
//            resepLiveData = viewModel.getResepById(resepId)
//            resepLiveData.observe(this, Observer { resep ->
//                if (resep != null) {
//                    tvToolbarTitle.text = resep.nama
////                    Picasso.get().load(resep.imageUrl).into(imgResep)
//                    tvBahan.text = resep.ingredients.joinToString(separator = "\n") { "- $it" }
//                    tvLangkah.text = resep.steps.joinToString(separator = "\n") { "- $it" }
//                    tvPenulis.text = resep.penulis
//                    tvDuration.text = "${resep.duration} mins"
//                    tvServings.text = resep.servings
//
//                    // Bookmark
//                    if (isAdmin) {
//                        imgBookmark.visibility = View.GONE
//                    } else {
//                        updateBookmarkIcon(resep.isBookmarked, imgBookmark)
//                        imgBookmark.setOnClickListener {
//                            resep.id?.let { it1 -> viewModel.toggleBookmark(it1, resep.isBookmarked) }
//                        }
//                    }
//                }
//            })
//        }
//
//        val rvRekomendasi: RecyclerView = findViewById(R.id.rv_rekomendasi)
//        rvRekomendasi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        viewModel.getRecommendedRecipes(resepId).observe(this) { recommendedRecipes ->
//            // Acak daftar resep sebelum diteruskan ke adapter
//            val shuffledRecipes = recommendedRecipes.shuffled()
//            val rekomAdapter = RekomAdapter(shuffledRecipes) { resep ->
//                val intent = Intent(this, AdminDetailActivity::class.java)
//                intent.putExtra("RESEP_ID", resep.id)
//                intent.putExtra("IS_ADMIN", isAdmin)
//                startActivity(intent)
//            }
//            rvRekomendasi.adapter = rekomAdapter
//        }
//
//        // Back Button Listener
//        imgBack.setOnClickListener { finish() }
//
//        // Home Button Listener
//        imgHome.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//            startActivity(intent)
//        }
//    }
//
//    private fun updateBookmarkIcon(isBookmarked: Boolean, imgBookmark: ImageView) {
//        imgBookmark.setImageResource(
//            if (isBookmarked) R.drawable.bookmark else R.drawable.ic_bookmark
//
//        )
//    }
//}
