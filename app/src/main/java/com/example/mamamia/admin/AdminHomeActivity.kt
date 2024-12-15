package com.example.mamamia.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mamamia.LoginActivity
import com.example.mamamia.SearchActivity
import com.example.mamamia.adapter.AdminResepAdapter
import com.example.mamamia.database.ResepEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.mamamia.databinding.ActivityAdminHomeBinding
import com.example.mamamia.network.ApiClient

class AdminHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminHomeBinding
    private lateinit var adapter: AdminResepAdapter
    private val resepList = ArrayList<ResepEntity>()
//    private val resepViewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        fetchResepList()
        val client = ApiClient.getInstance()
//        recipeAdapter = AdminResepAdapter(
//            resepList = ArrayList(),
//            client = client,
//            isAdmin = true,
//            onItemClicked = { resep ->
//                // Navigasi ke DetailActivity
//                val intent = Intent(this, DetailActivity::class.java)
//                intent.putExtra("RESEP_ID", resep.id)
//                intent.putExtra("IS_ADMIN", true)
//                startActivity(intent)
//            },
////            onBookmarkClicked = { _ ->
////                // Tampilkan pesan untuk admin
////                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
////            },
//            onEditClicked = { resep ->
//                // Navigasi ke EditResepActivity
//                val intent = Intent(this, EditResepActivity::class.java)
//                intent.putExtra("RESEP_ID", resep.id)
//                startActivity(intent)
//            },
//            onDeleteClicked = { resep ->
//                // Panggil fungsi untuk menghapus resep
//                deleteResep(resep)
//            }
//        )
//
//        // Konfigurasi RecyclerView
//        with(binding.rvAdminRecipes) {
//            layoutManager = LinearLayoutManager(this@AdminHomeActivity)
//            adapter = recipeAdapter
//        }

        // Tombol tambah resep
        binding.btnAddResep.setOnClickListener {
            val intent = Intent(this, AddResepActivity::class.java)
            startActivity(intent)
        }

        // Tombol pencarian
//        binding.btnSearch.setOnClickListener {
//            val intent = Intent(this, SearchActivity::class.java)
//            intent.putExtra("IS_ADMIN", true)
//            startActivity(intent)
//        }

        // Tombol logout
        binding.btnLogout.setOnClickListener {
            logout()
        }

        // Observasi data dari ViewModel
//        observeData()
    }

//    private fun observeData() {
//        resepViewModel.resepList.observe(this) { resepList ->
//            recipeAdapter.updateData(resepList)
//        }
//    }

    private fun setupRecyclerView() {
        adapter = AdminResepAdapter(resepList, ApiClient.getInstance()) { resep ->
            val intent = Intent(this, AdminDetailActivity::class.java).apply {
                putExtra("id", resep.id)
                putExtra("nama", resep.nama)
                putExtra("deskripsi", resep.deskripsi)
                putExtra("penulis", resep.penulis)
                putExtra("ingredients", resep.ingredients.joinToString(separator = "\n") { "- $it" })
                putExtra("steps", resep.steps.joinToString(separator = "\n") { "- $it" })
                putExtra("duration", "${resep.duration} mins")
                putExtra("servings", resep.servings)
                putExtra("gambar", resep.gambar)

            }
            startActivity(intent)
        }
        binding.rvAdminRecipes.layoutManager = LinearLayoutManager(this)
        binding.rvAdminRecipes.adapter = adapter
    }

    private fun fetchResepList() {
        val client = ApiClient.getInstance()
        val response = client.getAllResep()

        response.enqueue(object : Callback<List<ResepEntity>> {
            override fun onResponse(call: Call<List<ResepEntity>>, response: Response<List<ResepEntity>>) {
                if (response.isSuccessful && response.body() != null) {
                    resepList.clear()
                    resepList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@AdminHomeActivity,
                        "Gagal memuat resep",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<ResepEntity>>, t: Throwable) {
                Toast.makeText(
                    this@AdminHomeActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
//    private fun deleteResep(resep: ResepEntity) {
//        AlertDialog.Builder(this)
//            .setTitle("Hapus Resep")
//            .setMessage("Apakah Anda yakin ingin menghapus resep '${resep.nama}'?")
//            .setPositiveButton("Ya") { _, _ ->
//                resepViewModel.deleteResep(resep)
//                Toast.makeText(this, "Resep '${resep.nama}' berhasil dihapus.", Toast.LENGTH_SHORT).show()
//            }
//            .setNegativeButton("Batal", null)
//            .show()
//    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("MamamiaPrefs", MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

