package com.example.tasks.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tasks.service.model.PriorityModel

@Dao
interface PriorityDAO {

    @Insert
    fun save(list :  List<PriorityModel>)

    @Query("select * from priority")
    fun list() : List<PriorityModel>

    @Query("select description from priority where id = :id")
    fun getDescripiton(id : Int) : String

    @Query("delete from priority")
    fun clear()
}