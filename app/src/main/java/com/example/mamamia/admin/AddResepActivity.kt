package com.example.mamamia.admin

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mamamia.R
import com.example.mamamia.database.ResepEntity
import com.example.mamamia.databinding.ActivityAddResepBinding
import com.example.mamamia.network.ApiClient
import retrofit2.Call
import retrofit2.Callback

class AddResepActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddResepBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddResepBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val imgBack: ImageView = findViewById(R.id.iv_back)

        with(binding) {
            imgBack.setOnClickListener { finish() }

            btnSubmit.setOnClickListener {
                // Ambil input dari form
                val nama = etNamaResep.text.toString()
                val deskripsi = etDeskripsi.text.toString()
                val penulis = etPenulis.text.toString()
                val ingredients = etBahan.text.toString()
                    .split(",")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                val steps = etLangkah.text.toString()
                    .split(",")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                val duration = etDurasi.text.toString()
                val servings = etPorsi.text.toString()
                val gambar = etGambar.text.toString()

                // Validasi input kosong
                if (nama.isEmpty()) {
                    showToast("Nama resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (deskripsi.isEmpty()) {
                    showToast("Deskripsi resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (penulis.isEmpty()) {
                    showToast("Penulis resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (ingredients.isEmpty()) {
                    showToast("Bahan resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (steps.isEmpty()) {
                    showToast("Langkah resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (duration.isEmpty()) {
                    showToast("Durasi resep tidak boleh kosong")
                    return@setOnClickListener
                }
                if (servings.isEmpty()) {
                    showToast("Jumlah porsi tidak boleh kosong")
                    return@setOnClickListener
                }
                if (gambar.isEmpty()) {
                    showToast("URL gambar tidak boleh kosong")
                    return@setOnClickListener
                }

                // Membuat objek resep
                val resep = ResepEntity(
                    nama = nama,
                    deskripsi = deskripsi,
                    penulis = penulis,
                    ingredients = ingredients,
                    steps = steps,
                    duration = duration,
                    servings = servings,
                    gambar = gambar
                )

                // Mengirim data ke server
                val apiService = ApiClient.getInstance()
                val response = apiService.createResep(resep)

                response.enqueue(object : Callback<ResepEntity> {
                    override fun onResponse(call: Call<ResepEntity>, response: retrofit2.Response<ResepEntity>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@AddResepActivity, "Resep berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@AddResepActivity, AdminHomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@AddResepActivity, "Gagal menambahkan resep", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResepEntity>, t: Throwable) {
                        Toast.makeText(this@AddResepActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
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
