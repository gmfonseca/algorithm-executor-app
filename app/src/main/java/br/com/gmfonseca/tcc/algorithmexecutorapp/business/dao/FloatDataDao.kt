package br.com.gmfonseca.tcc.algorithmexecutorapp.business.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.FloatData
import br.com.gmfonseca.tcc.algorithmexecutorapp.shared.Constants.FloatData.COUNT
import br.com.gmfonseca.tcc.algorithmexecutorapp.shared.Constants.FloatData.TABLE

@Dao
interface FloatDataDao {

    @Insert
    fun insert(data: FloatData)

    @Query("SELECT * FROM $TABLE WHERE $COUNT = :count")
    fun getDataByCount(count: Int): FloatData?

}