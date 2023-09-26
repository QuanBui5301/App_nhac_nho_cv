package com.example.appnhacnhocv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.example.appnhacnhocv.databinding.WorkItemBinding

class WorkAdapter : RecyclerView.Adapter<WorkAdapter.ViewHolder>() {
    lateinit var binding: WorkItemBinding
    private lateinit var context: Context
    companion object {
        var WorkList : MutableList<Work> = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = WorkItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(WorkList[position])
    }

    override fun getItemCount(): Int {
        return WorkList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Work) {
            binding.name.text = item.work
            binding.time.text = item.time
            binding.idWork.text = item.id.toString()
        }
    }
}