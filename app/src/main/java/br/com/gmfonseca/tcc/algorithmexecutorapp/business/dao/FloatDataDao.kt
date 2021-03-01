package br.com.gmfonseca.tcc.algorithmexecutorapp.business.dao

import androidx.room.Dao
import androidx.room.Insert
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.FloatData

@Dao
interface FloatDataDao {

    @Insert
    fun insert(data: FloatData)

}