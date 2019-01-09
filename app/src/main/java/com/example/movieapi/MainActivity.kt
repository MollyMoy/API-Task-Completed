package com.example.movieapi

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_movie_layout.*
import kotlinx.android.synthetic.main.item_movie_layout.view.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val IMAGE_URL = "https://image.tmdb.org/t/p/w500"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this
        listView.setOnItemClickListener { _, _, position, _ ->
        // setting onclick listener to textView

            val Movie = title[position]
            //getting the movie object from the row that was clicked

            val detailIntent = MovieOverviewActivity.newIntent(context, Movie)
            //intent created to navigate to the MovieOverviewActivity

            startActivity(detailIntent)
            //launches the MovieOverviewActivity
        }



//initialized the variable from the movies resource list

// do not need to initialize recycler call as Kotlin automatically does this
        val moviesAdapter = MoviesAdapter()
        movies_list.adapter = moviesAdapter

//creating an instance of retrofit
        val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

//Now we have retrofit instance, using our retrofit instance we can create MovieApi that will get us our instance
//Is this getting the MovieApi instance?
        val MovieApi = retrofit.create(MovieApi::class.java)

        MovieApi.getMovies()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    moviesAdapter.setMovies(it.results)
                },
                        {
                            Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                })
//two values added in subscribe, one for success and the other for errory
//this will show a successful message or failure message
    }


    inner class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

        private val movies = ArrayList<Movie>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
            return MoviesViewHolder(layoutInflater.inflate(R.layout.item_movie_layout, parent, false))


        }

        override fun getItemCount(): Int {
            return movies.size
        }

        override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
            holder.bindModel(movies[position])
        }
        fun setMovies(data: List<Movie>) {
            movies.addAll(data)
            notifyDataSetChanged()
    }
        inner class MoviesViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){



            val heading : TextView = itemView.movie_title
            var moviePoster : ImageView = itemView.movie_poster_path


            fun bindModel(movie: Movie) {
                heading.text = movie.title


                Glide.with(itemView)
                        .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                        .into(moviePoster)


            }



        }


    }


}

