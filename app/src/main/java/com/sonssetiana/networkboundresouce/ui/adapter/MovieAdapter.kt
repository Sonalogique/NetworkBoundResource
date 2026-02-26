package com.sonssetiana.networkboundresouce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonssetiana.networkboundresouce.data.local.entitys.MovieEntity
import com.sonssetiana.networkboundresouce.databinding.ItemMovieBinding

class MovieAdapter:  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = ArrayList<MovieEntity>()

    fun setItems(newItems: List<MovieEntity>) {
        items = ArrayList(newItems)
        notifyDataSetChanged()
    }

    class MovieHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return MovieHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val viewHolder = holder as MovieHolder
        val context = viewHolder.itemView.context
        val item = items[position]
        with(viewHolder.binding) {
            Glide.with(context)
                .load(item.getPosterUrl())
                .into(imageView)
            titleView.text = item.title
        }
    }

    override fun getItemCount(): Int = items.size
}