package com.example.movieapi

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

//The interface supports multiple Inheritance

interface MovieApi {

        @GET("discover/movie?api_key=6f97dbff3dd18e3bfe486d824d46a7c4")
        fun getMovies(): Observable<MovieResponse>

        @GET("search/movie?api_key=6f97dbff3dd18e3bfe486d824d46a7c4")
        fun searchMovies(@Query("query") title: String): Observable<MovieResponse>



//MovieResponse Class is the return type

    }




