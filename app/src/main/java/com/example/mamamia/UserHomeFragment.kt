package com.example.mamamia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mamamia.adapter.UserResepAdapter
import com.example.mamamia.databinding.FragmentUserHomeBinding
import com.example.mamamia.database.ResepEntity
import com.example.mamamia.network.ApiClient
import com.example.mamamia.network.ApiService
import com.squareup.picasso.Picasso
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserHomeFragment : Fragment() {

        private lateinit var binding: FragmentUserHomeBinding
//        private val viewModel: ViewModel by viewModels()
        private lateinit var resepAdapter: UserResepAdapter
        private lateinit var resepList: ArrayList<ResepEntity>
        private lateinit var executorService: ExecutorService
        private lateinit var client: ApiService


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentUserHomeBinding.inflate(inflater, container, false)
            resepList = ArrayList()
            executorService = Executors.newSingleThreadExecutor()
            val client = ApiClient.getInstance()
            with(binding) {
                resepAdapter = UserResepAdapter(resepList, client) {resep ->
                    val intent = Intent(requireActivity(), UserDetailActivity::class.java). apply {
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
//                    resepList = resepList,
//                    client = client,
//                    onItemClicked = { resep ->
//                        // Navigasi ke DetailActivity
//                        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
//                            putExtra("RESEP_ID", resep.id) // Kirim ID resep ke DetailActivity
//                        }
//                        startActivity(intent)
//                    },
//                    onBookmarkClicked = { resep ->
//                        // Logika untuk menangani klik bookmark
//                        val newStatus = !resep.isBookmarked
//                        resep.isBookmarked = newStatus
//                        resep.id?.let { viewModel.toggleBookmark(it, newStatus) }
//                        resepAdapter.notifyItemChanged(resepList.indexOf(resep))
//                    }
                rvResep.layoutManager = LinearLayoutManager(requireContext())
                rvResep.adapter = resepAdapter
                }
                fetchResepList()

                return binding.root
        }
    private fun fetchResepList() {
        val client = ApiClient.getInstance()
        val response = client.getAllResep()

        response.enqueue(object : Callback<List<ResepEntity>> {
            override fun onResponse(call: Call<List<ResepEntity>>, response: Response<List<ResepEntity>>) {
                if (response.isSuccessful && response.body() != null) {
                    // Tambahkan data ke dalam list dan perbarui adapter
                    resepList.clear()
                    resepList.addAll(response.body()!!)
                    resepAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Gagal memuat resep",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<ResepEntity>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol FloatingActionButton untuk pencarian
        binding.search.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

//        // Observasi data dari ViewModel
//        viewModel.resepList.observe(viewLifecycleOwner) { resepEntities ->
//            resepList.clear()
//            resepList.addAll(resepEntities)
//            resepAdapter.updateData(resepEntities)
//        }
    }
}