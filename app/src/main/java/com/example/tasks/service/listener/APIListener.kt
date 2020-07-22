package com.example.tasks.service.listener

import com.example.tasks.service.model.HeaderModel

interface APIListener<T> {

    fun onSuccess(model : T)

    fun onFailere(str : String)
}