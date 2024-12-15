package com.example.mamamia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mamamia.database.ResepDatabase

class ProfileFragment : Fragment() {

//    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val tvUserName: TextView = view.findViewById(R.id.tv_user_name)
        val tvBookmarkCount: TextView = view.findViewById(R.id.tv_bookmark_count)
        val btnLogout: Button = view.findViewById(R.id.btn_logout)

        tvUserName.text = "Budiono"

//        tvBookmarkCount.text = "${bookmarks.size} Resep Disimpan"

        btnLogout.setOnClickListener {
            // SharedPreferences
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finish()
        }
        return view
    }
}
