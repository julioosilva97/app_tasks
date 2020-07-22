package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.listener.ValidationListener
import com.example.tasks.service.model.TaskModel
import com.example.tasks.service.repository.TaskRepository

class AllTasksViewModel(application: Application) : AndroidViewModel(application) {

    private val mTaskRepository = TaskRepository(application)

    private var mTaskFilter = 0

    private val mList = MutableLiveData<List<TaskModel>>()
    var tasks : LiveData<List<TaskModel>> = mList

    private val mValidation = MutableLiveData<ValidationListener>()
    var validation : LiveData<ValidationListener> = mValidation

    fun list(taskFilter:Int){

        mTaskFilter = taskFilter

        val listener = object : APIListener<List<TaskModel>>{
            override fun onSuccess(model: List<TaskModel>) {
                mList.value = model
            }

            override fun onFailere(str: String) {
                mList.value = arrayListOf()
                mValidation.value = ValidationListener(str)
            }

        }

        when (mTaskFilter) {
            TaskConstants.FILTER.ALL -> {
                mTaskRepository.all(listener)
            }
            TaskConstants.FILTER.EXPIRED -> {
                mTaskRepository.overdue(listener)
            }
            TaskConstants.FILTER.NEXT -> {
                mTaskRepository.nextWeek(listener)
            }
        }
    }

    fun complete(id: Int) {
        updateStatus(id,true)
    }

    fun undo(id: Int) {
       updateStatus(id,false)
    }

    private fun updateStatus(id: Int,complete:Boolean){
        mTaskRepository.updateStatus(id,complete,object : APIListener<Boolean>{
            override fun onSuccess(model: Boolean) {
                list(mTaskFilter)
            }

            override fun onFailere(str: String) {
                TODO("Not yet implemented")
            }

        })
    }

    fun delete(id: Int) {
        mTaskRepository.delete(id,object : APIListener<Boolean>{
            override fun onSuccess(model: Boolean) {
                list(mTaskFilter)
                mValidation.value = ValidationListener()
            }

            override fun onFailere(str: String) {
                mValidation.value = ValidationListener(str)
            }

        })
    }
}