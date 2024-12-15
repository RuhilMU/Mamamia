package com.example.mamamia.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mamamia.database.ResepEntity
import com.example.mamamia.databinding.ActivityEditResepBinding
import com.example.mamamia.network.ApiClient
import retrofit2.Call
import retrofit2.Callback

class EditResepActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditResepBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditResepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = ApiClient.getInstance()

        with(binding) {
            ivBack.setOnClickListener { finish() }

            // Ambil data dari intent
            val id = intent.getStringExtra("id")
            val nama = intent.getStringExtra("nama")
            val deskripsi = intent.getStringExtra("deskripsi")
            val penulis = intent.getStringExtra("penulis")
            val ingredients = intent.getStringExtra("ingredients")
                ?.split("\n")
                ?.map { it.replace(Regex("^\\-\\s*"), "") }
                ?.joinToString(", ")
            val steps = intent.getStringExtra("steps")
                ?.split("\n")
                ?.map { it.replace(Regex("^\\-\\s*"), "") }
                ?.joinToString(", ")
            val duration = intent.getStringExtra("duration")
            val servings = intent.getStringExtra("servings")
            val gambar = intent.getStringExtra("gambar")

            // Set data awal ke form
            etNamaResep.setText(nama)
            etDeskripsi.setText(deskripsi)
            etPenulis.setText(penulis)
            etBahan.setText(ingredients)
            etLangkah.setText(steps)
            etDurasi.setText(duration)
            etPorsi.setText(servings)
            etGambar.setText(gambar)

            // Tombol Submit
            btnSubmit.setOnClickListener {
                if (id.isNullOrEmpty()) {
                    showToast("ID resep tidak ditemukan")
                    return@setOnClickListener
                }

                // Ambil data baru dari form
                val newNama = etNamaResep.text.toString()
                val newDeskripsi = etDeskripsi.text.toString()
                val newPenulis = etPenulis.text.toString()
                val newIngredients = etBahan.text.toString()
                    .split(",")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                val newSteps = etLangkah.text.toString()
                    .split(",")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                val newDuration = etDurasi.text.toString()
                val newServings = etPorsi.text.toString()
                val newGambar = etGambar.text.toString()

                // Validasi input kosong
                if (newNama.isEmpty()) {
                    showToast("Nama resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (newDeskripsi.isEmpty()) {
                    showToast("Deskripsi resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (newPenulis.isEmpty()) {
                    showToast("Penulis resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (newIngredients.isEmpty()) {
                    showToast("Bahan resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (newSteps.isEmpty()) {
                    showToast("Langkah resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (newDuration.isEmpty()) {
                    showToast("Durasi resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (newServings.isEmpty()) {
                    showToast("Jumlah porsi tidak boleh kosong")
                    return@setOnClickListener
                }
                if (newGambar.isEmpty()) {
                    showToast("URL gambar tidak boleh kosong")
                    return@setOnClickListener
                }

                // Membuat objek resep baru
                val resep = ResepEntity(
                    id = id,
                    nama = newNama,
                    deskripsi = newDeskripsi,
                    penulis = newPenulis,
                    ingredients = newIngredients,
                    steps = newSteps,
                    duration = newDuration,
                    servings = newServings,
                    gambar = newGambar
                )

                // Update resep melalui API
                val response = client.updateResep(id, resep)
                response.enqueue(object : Callback<ResepEntity> {
                    override fun onResponse(call: Call<ResepEntity>, response: retrofit2.Response<ResepEntity>) {
                        if (response.isSuccessful) {
                            showToast("Resep telah diperbarui")
                            val intent = Intent(this@EditResepActivity, AdminHomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            showToast("Gagal memperbarui resep")
                        }
                    }

                    override fun onFailure(call: Call<ResepEntity>, t: Throwable) {
                        showToast("Error: ${t.message}")
                    }
                })
            }
        }
    }

    // Fungsi untuk menampilkan Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
