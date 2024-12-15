package com.example.mamamia

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mamamia.adapter.RekomAdapter
import com.example.mamamia.database.ResepDao
import com.example.mamamia.database.ResepDatabase
import com.example.mamamia.database.ResepTable
import com.example.mamamia.databinding.ActivityUserDetailBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso

class UserDetailActivity : AppCompatActivity() {

//    private val viewModel: ViewModel by viewModels()
//    private lateinit var resepLiveData: LiveData<ResepEntity>
//    private var isAdmin: Boolean = false
      private lateinit var binding: ActivityUserDetailBinding
      private lateinit var executorService: ExecutorService
      private lateinit var ResepDao: ResepDao
      private lateinit var rekomAdapter: RekomAdapter
      private var isBookmarked = false
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
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
        executorService = Executors.newSingleThreadExecutor()
        val latihanRoomDatabase = ResepDatabase.getDatabase(applicationContext)
        ResepDao = latihanRoomDatabase?.resepDao()!!
        with(binding) {
            tvResep.text = intent.getStringExtra("nama")
            tvPenulis.text = intent.getStringExtra("penulis")
            tvBahanContent.text = intent.getStringExtra("ingredients")
            tvLangkahContent.text = intent.getStringExtra("steps")
            tvDuration.text = intent.getStringExtra("duration")
            tvServings.text = intent.getStringExtra("servings")

            ivBack.setOnClickListener {
                finish()
            }
            imgBookmark.setOnClickListener {
                toggleBookmarkIcon()
                addBookmark()
            }
        }
        setupRecyclerView()
        loadAllResep()
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

//        val rvRekomendasi: RecyclerView = findViewById(R.id.rv_rekomendasi)
//        rvRekomendasi.layoutManager = LinearLayoutManager(this@UserDetailActivity, LinearLayoutManager.HORIZONTAL, false)
//        viewModel.getRecommendedRecipes(resepId).observe(this) { recommendedRecipes ->
//            // Acak daftar resep sebelum diteruskan ke adapter
//            val shuffledRecipes = recommendedRecipes.shuffled()
//            val rekomAdapter = RekomAdapter(shuffledRecipes) { resep ->
//                val intent = Intent(this, UserDetailActivity::class.java)
//                intent.putExtra("RESEP_ID", resep.id)
//                intent.putExtra("IS_ADMIN", isAdmin)
//                startActivity(intent)

//            rvRekomendasi.adapter = rekomAdapter
        }
    private fun setupRecyclerView() {
        binding.rvRekomendasi.apply {
            layoutManager = LinearLayoutManager(
                this@UserDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rekomAdapter = RekomAdapter(emptyList()) { resep ->
                val intent = Intent(this@UserDetailActivity, BookmarkDetailActivity::class.java).apply {
                    putExtra("id", resep.id)
                    putExtra("nama", resep.nama)
                    putExtra("penulis", resep.penulis)
                    putExtra("ingredients", resep.ingredients.joinToString("\n") { "- $it" })
                    putExtra("steps", resep.steps.joinToString("\n") { "- $it" })
                    putExtra("duration", resep.duration)
                    putExtra("servings", resep.servings)
                    putExtra("gambar", resep.gambar)
                }
                startActivity(intent)
            }
            adapter = rekomAdapter
        }
    }

    private fun loadAllResep() {
        val resepDao = ResepDatabase.getDatabase(this)?.resepDao()
        if (resepDao != null) {
            resepDao.getAllResep().observe(this, Observer { resepList ->
                    rekomAdapter.updateData(resepList)
            })
        }
    }

    private fun insert(resep: ResepTable) {
        executorService.execute {
            ResepDao.insert(resep)
            runOnUiThread {
                ResepDao.getAllResep().observe(this@UserDetailActivity) { resepList ->
                    resepList?.forEach { latihan ->
                        Log.d("Resep disimpan", "Resep: ${resep.nama}")
                    }
                }
            }
        }
    }

    private fun toggleBookmarkIcon() {
        isBookmarked = !isBookmarked // Ubah status bookmark
        if (isBookmarked) {
            binding.imgBookmark.setImageResource(R.drawable.bookmark) // Ikon bookmarked
        } else {
            binding.imgBookmark.setImageResource(R.drawable.ic_bookmark) // Ikon default
        }
    }

    private fun addBookmark() {
        val resep = ResepTable(
            id = intent.getStringExtra("id").toString(),
            nama = intent.getStringExtra("nama").toString(),
            deskripsi = intent.getStringExtra("deskripsi").toString(),
            penulis = intent.getStringExtra("penulis").toString(),
            ingredients = listOf(intent.getStringExtra("ingredients").toString()),
            steps = listOf(intent.getStringExtra("steps").toString()),
            duration = intent.getStringExtra("duration").toString(),
            servings = intent.getStringExtra("servings").toString(),
            gambar = intent.getStringExtra("gambar").toString(),
        )

        insert(resep)
        Toast.makeText(this, "Resep ditambahkan ke favorit", Toast.LENGTH_SHORT)
            .show()
    }
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

//    private fun updateBookmarkIcon(isBookmarked: Boolean, imgBookmark: ImageView) {
//        imgBookmark.setImageResource(
//            if (isBookmarked) R.drawable.bookmark else R.drawable.ic_bookmark
//
//        )
//    }
}
