package com.kjw.retropup

import retrofit2.http.GET

interface DogApi {
    @GET("breeds/image/random")
    suspend fun getRandomDog(): DogResponse
}