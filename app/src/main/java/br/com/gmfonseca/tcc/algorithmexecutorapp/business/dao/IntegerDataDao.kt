package br.com.gmfonseca.tcc.algorithmexecutorapp.business.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.IntegerData
import br.com.gmfonseca.tcc.algorithmexecutorapp.shared.Constants.IntegerData.COUNT
import br.com.gmfonseca.tcc.algorithmexecutorapp.shared.Constants.IntegerData.TABLE

@Dao
interface IntegerDataDao {

    @Insert
    fun insert(data: IntegerData)

    @Query("SELECT * FROM $TABLE WHERE $COUNT = :count")
    fun getDataByCount(count: Int): IntegerData?

}