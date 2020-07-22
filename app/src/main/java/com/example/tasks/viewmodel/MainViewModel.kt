package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.ValidationListener
import com.example.tasks.service.repository.SecurityPreferences

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private  val mSharedPreferences = SecurityPreferences(application)

    private val mUserName = MutableLiveData<String>()
    var userName : LiveData<String> = mUserName

    private val mLoggot = MutableLiveData<Boolean>()
    var loggot : LiveData<Boolean> = mLoggot

    fun loadUserName(){
        mUserName.value = mSharedPreferences.get(TaskConstants.SHARED.PERSON_NAME)
    }

    fun logout() {
        mSharedPreferences.remove(TaskConstants.SHARED.PERSON_KEY)
        mSharedPreferences.remove(TaskConstants.SHARED.TOKEN_KEY)
        mSharedPreferences.remove(TaskConstants.SHARED.PERSON_NAME)

        mLoggot.value = true
    }
}