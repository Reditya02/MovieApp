package com.example.movieapp.ui.movielist

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.example.movieapp.R
import com.example.movieapp.data.remote.model.MovieResponse
import com.example.movieapp.databinding.FragmentMovieListBinding
import com.example.movieapp.helper.MovieListFilter
import java.util.*


class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieListViewModel by viewModels()

    private var movieFilter = MovieListFilter.POPULAR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        fabClicked()
    }

    private fun setActionBarTitle() {
        val title = movieFilter.toString().replace('_', ' ')
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    private fun getData() {


        viewModel.apply {
            getListMovie(movieFilter)

            var movieList = listOf<MovieResponse>()

            setActionBarTitle()

            listMovie.observe(viewLifecycleOwner) {
                movieList = it
            }
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it, movieList)
            }
        }
    }

    private fun fabClicked() {
        binding.apply {
            fabPopular.setOnClickListener {
                movieFilter = MovieListFilter.POPULAR
                getData()
            }

            fabTopRated.setOnClickListener {
                movieFilter = MovieListFilter.TOP_RATED
                getData()
            }

            fabNowPlaying.setOnClickListener {
                movieFilter = MovieListFilter.NOW_PLAYING
                getData()
            }

            fabUpcoming.setOnClickListener {
                movieFilter = MovieListFilter.UPCOMING
                getData()
            }
        }
    }

    private fun showRecycler(listMovie: List<MovieResponse>) {
        binding.recyclerView.apply {
            layoutManager = object : GridLayoutManager(context, 2) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                    lp.height = (height * 0.4).toInt()
                    return true
                }
            }
            adapter = MovieListAdapter(
                listMovie = listMovie,
                onClick = {
                    val toDetail = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(it)
                    findNavController().navigate(toDetail)
                }
            )
        }
    }

    private fun showLoading(isLoading: Boolean, listMovie: List<MovieResponse>) {
        binding.apply {
            if (isLoading) {
                recyclerView.visibility = View.GONE
                animLoading.apply {
                    visibility = View.VISIBLE
                    setAnimation(R.raw.loading_movie)
                    repeatCount = LottieDrawable.INFINITE
                    playAnimation()
                }
                fabFilter.hide()
            } else {
                recyclerView.visibility = View.VISIBLE
                animLoading.apply {
                    visibility = View.GONE
                    pauseAnimation()
                }
                showRecycler(listMovie)
                fabFilter.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.button_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.profile) {
            val toProfile = MovieListFragmentDirections.actionMovieListFragmentToProfileFragment()
            findNavController().navigate(toProfile)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}