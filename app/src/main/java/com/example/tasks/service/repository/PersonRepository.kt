package com.example.tasks.service.repository

import android.content.Context
import com.example.tasks.R
import com.example.tasks.service.model.HeaderModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.view.remote.PersonService
import com.example.tasks.view.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository(val context: Context) : BaseRepository(context) {

    private val mRemote = RetrofitClient.createService(PersonService::class.java)

    fun login(email: String, password: String, listener: APIListener<HeaderModel>) {

        if (!isConnectionAvailable(context)) {
            listener.onFailere(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call: Call<HeaderModel> = mRemote.login(email, password)

        call.enqueue(object : Callback<HeaderModel> {
            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                listener.onFailere(context.getString(R.string.ERROR_UNEXPECTED))
                //mensagem de erro inesperado
            }

            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
                //verifica se o response.body() é null
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    //converter o json
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)

                    listener.onFailere(validation)
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }

            }

        })
    }

    fun create(name: String, email: String, password: String, listener: APIListener<HeaderModel>) {

        if (!isConnectionAvailable(context)) {
            listener.onFailere(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call: Call<HeaderModel> = mRemote.create(name, email, password, true)

        call.enqueue(object : Callback<HeaderModel> {
            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                listener.onFailere(context.getString(R.string.ERROR_UNEXPECTED))
                //mensagem de erro inesperado
            }

            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
                //verifica se o response.body() é null
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    //converter o json
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)

                    listener.onFailere(validation)
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }

            }

        })
    }
}