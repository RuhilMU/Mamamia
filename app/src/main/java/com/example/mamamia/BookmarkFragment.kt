package com.example.mamamia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mamamia.databinding.FragmentBookmarkBinding
import com.example.mamamia.adapter.BookmarkResepAdapter
import com.example.mamamia.database.ResepDao
import com.example.mamamia.database.ResepDatabase
import com.example.mamamia.database.ResepTable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var adapter: BookmarkResepAdapter
    private lateinit var  resepDao : ResepDao
    private lateinit var executorService: ExecutorService
//    private lateinit var viewModel: ViewModel
    private lateinit var emptyStateTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        executorService = Executors.newSingleThreadExecutor()
        val db = ResepDatabase.getDatabase(requireContext())
        resepDao = db!!.resepDao()
        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // Inisialisasi ViewModel
//        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
//
//        // Setup UI Components
//        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_bookmark)
//        emptyStateTextView = view.findViewById(R.id.empty_state)
//        val client = ApiClient.getInstance()
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        resepAdapter = ResepAdapter(
//            ArrayList(),
//            onItemClicked = { resep ->
//                val intent = Intent(requireContext(), DetailActivity::class.java)
//                intent.putExtra("RESEP_ID", resep.id)
//                startActivity(intent)
//            },
//            client = client,
//            onBookmarkClicked = { resep ->
//                val newStatus = !resep.isBookmarked
//                viewModel.toggleBookmark(resep.id, newStatus)
//            },
//            isAdmin = false,
//            onDeleteClicked = null,
//            onEditClicked = null
//        )
//        recyclerView.adapter = resepAdapter
//
//        // Observasi data bookmark dari ViewModel
//        viewModel.bookmarkedList.observe(viewLifecycleOwner) { bookmarks ->
//            resepAdapter.updateData(bookmarks)
//            updateEmptyState(bookmarks.isEmpty())
//        }

        // Tombol FloatingActionButton
        binding.search.setOnClickListener {
            // Navigasi ke halaman pencarian
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
    }

//     Menampilkan atau menyembunyikan pesan empty state.
//    private fun updateEmptyState(isEmpty: Boolean) {
//        emptyStateTextView.visibility = if (isEmpty) View.VISIBLE else View.GONE
//    }

    private fun setupRecyclerView() {
        binding.rvBookmark.layoutManager = LinearLayoutManager(requireContext())
        adapter = BookmarkResepAdapter(
            deleteBookmark = { resep ->
                deleteBookmarked(resep)
            },
            ResepDetail = { resep ->
                // Navigate to detail activity
                val intent = Intent(requireContext(), BookmarkDetailActivity::class.java).apply {
                    putExtra("id", resep.id)
                    putExtra("nama", resep.nama)
                    putExtra("penulis", resep.penulis)
                    putExtra("duration", "${resep.duration}")
                    putExtra("servings", resep.servings)
                    putExtra("ingredients", resep.ingredients.joinToString(separator = "\n") { it })
                    putExtra("steps", resep.steps.joinToString(separator = "\n") { it })
                    putExtra("gambar", resep.gambar)

                }
                startActivity(intent)
            }
        )
        binding.rvBookmark.adapter = adapter

        resepDao.getAllResep().observe(viewLifecycleOwner) { resepList ->
            adapter.submitList(resepList) // Update Data
        }
    }

    private fun deleteBookmarked(resep: ResepTable) {
        executorService.execute {
            resepDao.delete(resep)
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), "Resep dihapus dari bookmark", Toast.LENGTH_SHORT).show()
            }
        }
    }
}