package br.com.gmfonseca.tcc.algorithmexecutorapp.business.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import br.com.gmfonseca.tcc.algorithmexecutorapp.shared.Constants
import br.com.gmfonseca.tcc.proto.AlgorithmExecutor

abstract class Data<T> {
    abstract val content: List<T>
    abstract val case: AlgorithmExecutor.ExecuteAlgorithmResult.DataCase
}

@Entity(tableName = Constants.IntegerData.TABLE)
data class IntegerData(
    @ColumnInfo(name = Constants.IntegerData.CONTENT) override val content: List<Int>,
    @ColumnInfo(name = Constants.IntegerData.CASE) override val case: AlgorithmExecutor.ExecuteAlgorithmResult.DataCase
) : Data<Int>()

@Entity(tableName = Constants.IntegerData.TABLE)
data class FloatData(
    @ColumnInfo(name = Constants.IntegerData.CONTENT) override val content: List<Float>,
    @ColumnInfo(name = Constants.IntegerData.CASE) override val case: AlgorithmExecutor.ExecuteAlgorithmResult.DataCase
) : Data<Float>()

@Entity(tableName = Constants.IntegerData.TABLE)
data class ObjectData(
    @ColumnInfo(name = Constants.IntegerData.CONTENT) override val content: List<Any>,
    @ColumnInfo(name = Constants.IntegerData.CASE) override val case: AlgorithmExecutor.ExecuteAlgorithmResult.DataCase
) : Data<Any>()