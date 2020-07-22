package com.example.tasks.service.repository

import android.content.Context
import com.example.tasks.R
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.TaskModel
import com.example.tasks.view.remote.RetrofitClient
import com.example.tasks.view.remote.TaskService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(val context: Context) : BaseRepository(context){

    private val mRemote = RetrofitClient.createService(TaskService::class.java)

    fun all(listener: APIListener<List<TaskModel>>) {

        if(!isConnectionAvailable(context)){
            listener.onFailere(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call: Call<List<TaskModel>> = mRemote.all()
        list(call, listener)
    }

    fun nextWeek(listener: APIListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = mRemote.nextWeek()
        list(call, listener)
    }

    fun overdue(listener: APIListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = mRemote.overdue()
        list(call, listener)
    }

    private fun list(call: Call<List<TaskModel>>, listener: APIListener<List<TaskModel>>) {

        if(!isConnectionAvailable(context)){
            listener.onFailere(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        call.enqueue(object : Callback<List<TaskModel>> {
            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFailere(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
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

    fun updateStatus(id: Int,complete:Boolean,listener: APIListener<Boolean>){

        if(!isConnectionAvailable(context)){
            listener.onFailere(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = if(complete){
            mRemote.complete(id)
        }else{
            mRemote.undo(id)
        }

        call.enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailere(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
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

    fun update(task: TaskModel, listener: APIListener<Boolean>) {

        if(!isConnectionAvailable(context)){
            listener.onFailere(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call: Call<Boolean> =
            mRemote.update(task.id, task.priorityId, task.description, task.dueDate, task.complete)

        call.enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailere(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
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

    fun create(task: TaskModel, listener: APIListener<Boolean>) {

        if(!isConnectionAvailable(context)){
            listener.onFailere(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call: Call<Boolean> =
            mRemote.create(task.priorityId, task.description, task.dueDate, task.complete)

        call.enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailere(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
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

    fun delete(id: Int, listener: APIListener<Boolean>) {

        if(!isConnectionAvailable(context)){
            listener.onFailere(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call: Call<Boolean> =
            mRemote.delete(id)

        call.enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailere(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
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

    fun load(id: Int, listener: APIListener<TaskModel>) {

        if(!isConnectionAvailable(context)){
            listener.onFailere(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call: Call<TaskModel> = mRemote.load(id)

        call.enqueue(object : Callback<TaskModel> {
            override fun onFailure(call: Call<TaskModel>, t: Throwable) {
                listener.onFailere(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(call: Call<TaskModel>, response: Response<TaskModel>) {
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