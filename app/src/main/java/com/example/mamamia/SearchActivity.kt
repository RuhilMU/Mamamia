package com.example.mamamia

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mamamia.network.ApiClient

class SearchActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var noResultsText: TextView
    private lateinit var headerResults: TextView
//    private lateinit var adapter: ResepAdapter
//    private lateinit var viewModel: ViewModel
    private lateinit var backButton: ImageView
    private var isAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val client = ApiClient.getInstance()
        isAdmin = intent.getBooleanExtra("IS_ADMIN", false)
        // Inisialisasi View
        searchView = findViewById(R.id.search_view)
        recyclerView = findViewById(R.id.rv_search_results)
        noResultsText = findViewById(R.id.tv_no_results)
        headerResults = findViewById(R.id.tv_header_results)
        backButton = findViewById(R.id.iv_back)

        // Inisialisasi ViewModel
//        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        // Inisialisasi Adapter
//        adapter = ResepAdapter(
//            ArrayList(),
//            client = client,
//            onItemClicked = { resep ->
//                val intent = Intent(this, UserDetailActivity::class.java)
//                intent.putExtra("RESEP_ID", resep.id)
//                if (isAdmin) {intent.putExtra("IS_ADMIN", isAdmin)}
//                startActivity(intent)
//            },
//            onBookmarkClicked = if (!isAdmin) { { resep ->
//                resep.id?.let { viewModel.toggleBookmark(it, !resep.isBookmarked) }
//            } } else { {} },
//            isAdmin = isAdmin,
//            onDeleteClicked = if (isAdmin) { { resep ->
//                viewModel.deleteResep(resep)
//            } } else null,
//            onEditClicked = if (isAdmin) { { resep ->
//                val intent = Intent(this, EditResepActivity::class.java)
//                intent.putExtra("RESEP_ID", resep.id)
//                startActivity(intent)
//            } } else null
//        )

        // RecyclerView setup
        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter

        // Tombol Kembali
        backButton.setOnClickListener {
            finish() // Tutup aktivitas pencarian
        }

        // Observasi hasil pencarian
//        viewModel.resepList.observe(this) { recipes ->
//            if (recipes.isEmpty()) {
//                noResultsText.visibility = TextView.VISIBLE
//                recyclerView.visibility = RecyclerView.GONE
//                headerResults.visibility = TextView.GONE
//            } else {
//                noResultsText.visibility = TextView.GONE
//                recyclerView.visibility = RecyclerView.VISIBLE
//                headerResults.visibility = TextView.VISIBLE
//                adapter.updateData(recipes)
//            }
//        }
        searchView.setOnClickListener {
            searchView.isIconified = false
        }
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
            }
        }

//        // SearchView setup
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let {
//                    searchRecipes(query)
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
//    }
//
//    private fun searchRecipes(query: String) {
//        viewModel.searchResep(query).observe(this) { filteredRecipes ->
//            if (filteredRecipes.isEmpty()) {
//                noResultsText.visibility = TextView.VISIBLE
//                recyclerView.visibility = RecyclerView.GONE
//                headerResults.visibility = TextView.GONE
//            } else {
//                noResultsText.visibility = TextView.GONE
//                headerResults.visibility = TextView.VISIBLE
//                recyclerView.visibility = RecyclerView.VISIBLE
//                adapter.updateData(filteredRecipes)
//            }
//        }
//    }
    }
}
