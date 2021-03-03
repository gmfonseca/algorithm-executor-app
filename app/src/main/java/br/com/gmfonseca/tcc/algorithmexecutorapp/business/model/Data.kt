package br.com.gmfonseca.tcc.algorithmexecutorapp.business.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import br.com.gmfonseca.tcc.algorithmexecutorapp.shared.Constants
import com.google.gson.Gson

abstract class Data<T> {
    abstract var content: List<T>
    abstract var count: Int
    abstract var case: Case
}

@Entity(tableName = Constants.IntegerData.TABLE)
data class IntegerData(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = Constants.IntegerData.CONTENT) override var content: List<Int> = emptyList(),
    @ColumnInfo(name = Constants.IntegerData.COUNT) override var count: Int = 0,
    @ColumnInfo(name = Constants.IntegerData.CASE) override var case: Case = Case.BEST
) : Data<Int>()

@Entity(tableName = Constants.FloatData.TABLE)
data class FloatData(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = Constants.FloatData.CONTENT) override var content: List<Float> = emptyList(),
    @ColumnInfo(name = Constants.FloatData.COUNT) override var count: Int = 0,
    @ColumnInfo(name = Constants.FloatData.CASE) override var case: Case = Case.BEST
) : Data<Float>()

enum class Case {
    WORST, BEST
}

enum class Method {
    LOCAL, REST, GRPC
}

enum class Algorithm {
    BUBBLE, HEAP, SELECTION
}

enum class DataType {
    INTEGER, FLOAT, OBJECT
}

class DataCaseConverter {
    @TypeConverter
    fun fromString(str: String): Case {
        return Case.valueOf(str)
    }

    @TypeConverter
    fun toString(case: Case): String {
        return case.name
    }
}

@Suppress("UNCHECKED_CAST")
class ListConverter {
    private val gson = Gson()

    @TypeConverter
    fun intFromString(str: String): List<Int> {
        return gson.fromJson(str, Array<Int>::class.java).toList()
    }

    @TypeConverter
    fun intToString(elements: List<Int>): String {
        return gson.toJson(elements)
    }

    @TypeConverter
    fun floatFromString(str: String): List<Float> {
        return gson.fromJson(str, Array<Float>::class.java).toList()
    }

    @TypeConverter
    fun floatToString(elements: List<Float>): String {
        return gson.toJson(elements)
    }
}