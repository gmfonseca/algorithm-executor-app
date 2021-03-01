package br.com.gmfonseca.tcc.algorithmexecutorapp.business.dao

import androidx.room.Dao
import androidx.room.Insert
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.IntegerData

@Dao
interface IntegerDataDao {

    @Insert
    fun insert(data: IntegerData)

}