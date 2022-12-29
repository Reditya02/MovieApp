package com.example.movieapp.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.helper.Const
import com.example.movieapp.data.remote.model.MovieResponse
import com.example.movieapp.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: MovieResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = MovieDetailFragmentArgs.fromBundle(arguments as Bundle).movie

        binding.apply {
            Glide.with(root)
                .load("${Const.POSTER_URL}${movie.backdropPath}")
                .into(ivBackdrop)

            tvTitle.text = movie.title
        }
    }
}