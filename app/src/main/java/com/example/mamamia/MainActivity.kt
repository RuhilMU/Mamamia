package com.example.mamamia

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mamamia.admin.AdminHomeActivity
import com.example.mamamia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Akses SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("MamamiaPrefs", MODE_PRIVATE)

        // Cek status login
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val userRole = sharedPreferences.getString("userRole", null)

        // Navigasi berdasarkan status login
        if (!isLoggedIn) {
            // Jika belum login, ke LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Tutup MainActivity
            return
        } else if (userRole == "admin") {
            // Jika admin, pindah ke AdminHomeActivity
            startActivity(Intent(this, AdminHomeActivity::class.java))
            finish() // Tutup MainActivity
            return
        }

        // Jika user biasa, set up UI untuk MainActivity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi NavController dan setup Bottom Navigation
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}
