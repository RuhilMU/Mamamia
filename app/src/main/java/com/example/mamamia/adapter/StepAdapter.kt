//package com.example.mamamia.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.EditText
//import android.widget.ImageView
//import androidx.core.widget.addTextChangedListener
//import androidx.recyclerview.widget.RecyclerView
//import com.example.mamamia.R
//
//class StepAdapter(
//    private val steps: MutableList<String>,
//    private val onDelete: (Int) -> Unit
//) : RecyclerView.Adapter<StepAdapter.StepViewHolder>() {
//
//    inner class StepViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val etStep: EditText = view.findViewById(R.id.et_step)
//        val ivDelete: ImageView = view.findViewById(R.id.iv_delete)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_step, parent, false)
//        return StepViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
//        holder.etStep.setText(steps[position])
//
//        // Update langkah ketika teks berubah
//        holder.etStep.addTextChangedListener {
//            steps[position] = it.toString()
//        }
//
//        // Hapus langkah ketika ikon delete diklik
//        holder.ivDelete.setOnClickListener {
//            onDelete(position) // Panggil callback untuk menghapus langkah
//        }
//    }
//
//    override fun getItemCount(): Int = steps.size
//
//    fun addStep() {
//        steps.add("")
//        notifyItemInserted(steps.size - 1)
//    }
//
//    fun removeStep(position: Int) {
//        if (position in steps.indices) {
//            steps.removeAt(position)
//            notifyItemRemoved(position)
//            notifyItemRangeChanged(position, steps.size) // Perbarui daftar
//        }
//    }
//
//    fun updateSteps(newSteps: List<String>) {
//        steps.clear()
//        steps.addAll(newSteps)
//        notifyDataSetChanged()
//    }
//}
