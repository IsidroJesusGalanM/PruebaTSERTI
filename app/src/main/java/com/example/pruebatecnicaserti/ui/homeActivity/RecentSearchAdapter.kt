package com.example.pruebatecnicaserti.ui.homeActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnicaserti.databinding.RecentSerchCardBinding

class RecentSearchAdapter (
    private val recentSearchList: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder>() {

    inner class RecentSearchViewHolder(val binding: RecentSerchCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentSearchViewHolder {
        val binding = RecentSerchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentSearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recentSearchList.size
    }

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        val recentSearch = recentSearchList[position]
        holder.binding.tvRecentSearch.text = recentSearch
        holder.binding.root.setOnClickListener {
            onItemClick(recentSearch)
        }
    }





}