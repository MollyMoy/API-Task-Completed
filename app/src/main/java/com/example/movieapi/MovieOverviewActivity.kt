package com.example.movieapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_movie_overview.*


//This will display when a user selects an item in the list

class MovieOverviewActivity : AppCompatActivity() {

    //Add the textview as a property
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_overview)
        setSupportActionBar(toolbar)

// retrieve the movie data from the intent passed from Main Acitvity by using extra
        val title = intent.extras.getString(EXTRA_TITLE)
        val url = intent.extras.getString(EXTRA_URL)

       // set the title to the movie title
        setTitle(title)

        //initialize textView in xml for movie overview
        textView = findViewById(R.id.movie_overview)

        //load the movie overview by calling the url on the textView object
        textView.loadUrl(url)

        }


        //This adds a companion object (If you need to write a function that can be called without having a class instance but needs access to the internals of a class)
        // method to return an Intent for starting the detail activity, and sets up title and url extras in the Intent

        companion object {
            const val EXTRA_TITLE = "title"
            const val EXTRA_URL = "url"

            fun newIntent(context: Context, movie: Movie): Intent {
                val detailIntent = Intent(context, MovieOverviewActivity::class.java)

                detailIntent.putExtra(EXTRA_TITLE, movie.title)
                detailIntent.putExtra(EXTRA_URL, movie.overview)

                return detailIntent
            }
        }
    }


