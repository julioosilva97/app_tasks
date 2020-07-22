package com.example.tasks.view.remote

import com.example.tasks.service.model.HeaderModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PersonService {

    @POST("Authentication/Login")
    @FormUrlEncoded  // mapeia os paramentros que a api espera
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<HeaderModel>

    @POST("Authentication/Create")
    @FormUrlEncoded  // mapeia os paramentros que a api espera
    fun create(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("receivenews") receivenews: Boolean
    ) : Call<HeaderModel>
}