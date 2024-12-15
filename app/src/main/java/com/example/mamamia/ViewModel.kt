//package com.example.mamamia
//
//import android.app.Application
//import androidx.lifecycle.*
//import com.example.mamamia.database.ResepDatabase
//import com.example.mamamia.database.ResepEntity
//import kotlinx.coroutines.launch
//
//class ViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val db = ResepDatabase.getDatabase(application)
//    private val resepDao = db?.resepDao()
//
//    // LiveData untuk semua resep
//    val resepList: LiveData<List<ResepEntity>> = resepDao?.getAllResep() ?: MutableLiveData(emptyList())
//
//    // LiveData untuk resep yang dibookmark
//    val bookmarkedList: LiveData<List<ResepEntity>> = resepDao?.getBookmarkedResep() ?: MutableLiveData(emptyList())
//
//    // Toggle Bookmark
//    fun toggleBookmark(id: String, isBookmarked: Boolean) {
//        viewModelScope.launch {
//            resepDao?.updateBookmarkStatus(id, !isBookmarked)
//        }
//    }
//
//    fun getResepById(id: Int): LiveData<ResepEntity> {
//        return resepDao?.getResepById(id.toString()) ?: MutableLiveData()
//    }
//
//    fun searchResep(query: String): LiveData<List<ResepEntity>> {
//        return resepDao?.searchResep("%$query%") ?: MutableLiveData(emptyList())
//    }
//
//    fun getRecommendedRecipes(excludedResepId: Int): LiveData<List<ResepEntity>> {
//        val recommendedRecipes = MediatorLiveData<List<ResepEntity>>()
//
//        recommendedRecipes.addSource(resepList) { recipes ->
//            // Pastikan `id` tidak nullable dan cocok dengan tipe `Int`
//            val filteredRecipes = recipes.filter { it.id?.toIntOrNull() != excludedResepId }.take(8)
//            recommendedRecipes.value = filteredRecipes
//        }
//        return recommendedRecipes
//    }
//
//    fun deleteResep(resep: ResepEntity) {
//        viewModelScope.launch {
//            resepDao?.deleteResep(resep)
//        }
//    }
//}
//
