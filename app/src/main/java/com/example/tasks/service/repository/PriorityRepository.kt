package com.example.tasks.service.repository

import android.content.Context
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.model.PriorityModel
import com.example.tasks.service.repository.local.TaskDatabase
import com.example.tasks.view.remote.PersonService
import com.example.tasks.view.remote.PriorityService
import com.example.tasks.view.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(context: Context) {

    private val mRemote = RetrofitClient.createService(PriorityService::class.java)
    private val mPriorityDataBase = TaskDatabase.getDatabase(context).priorityDAO()

    fun all(){
        val call : Call<List<PriorityModel>> = mRemote.list()

        call.enqueue(object : Callback<List<PriorityModel>>{
            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<List<PriorityModel>>,
                response: Response<List<PriorityModel>>
            ) {
                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    mPriorityDataBase.clear()
                    response.body()?.let { mPriorityDataBase.save(it) }
                }
            }
        })
    }

    fun list() : List<PriorityModel> {
        return mPriorityDataBase.list()
    }

    fun getDescription(id : Int) = mPriorityDataBase.getDescripiton(id)
}