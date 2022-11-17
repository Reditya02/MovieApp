package com.example.movieapp.ui.movielist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.helper.Const
import com.example.movieapp.data.remote.model.MovieResponse
import com.example.movieapp.databinding.CardMovieBinding


class MovieListAdapter(
    private val listMovie:List<MovieResponse>,
    private val onClick: (MovieResponse) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = CardMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        view.cardMovie.layoutParams.height = parent.measuredHeight / 3
//        val params = view.root.layoutParams as GridLayoutManager.LayoutParams
//        params.height = parent.measuredHeight / 3
//        view.root.layoutParams = params
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movie = listMovie[position]
        Log.d("Reditya inside adapter", movie.toString())
        holder.binding.apply {
            tvTitle.text = movie.title
            Glide.with(cardMovie.context)
                .load("${Const.POSTER_URL}${movie.posterPath}")
                .into(ivMovieImage)
            cardMovie.setOnClickListener {
                onClick(movie)
            }
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    class Holder(val binding: CardMovieBinding) : RecyclerView.ViewHolder(binding.root)


}