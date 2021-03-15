package br.com.gmfonseca.tcc.algorithmexecutorapp.business.model

import br.com.gmfonseca.tcc.algorithmexecutorapp.shared.toProto
import br.com.gmfonseca.tcc.proto.AlgorithmExecutor
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.util.*

data class AnComparableObject(
    @SerializedName("uuid") private val uuid: UUID,
    @SerializedName("intNumber") private val intNumber: Int,
    @SerializedName("floatNumber") private val floatNumber: Float
) : Comparable<AnComparableObject> {
    override fun compareTo(other: AnComparableObject): Int {
        return intNumber.compareTo(other.intNumber)
    }

    fun toAnObject(): AlgorithmExecutor.AnObject = AlgorithmExecutor.AnObject.newBuilder()
        .setFloatNumber(floatNumber)
        .setIntNumber(intNumber)
        .setUuid(uuid.toProto())
        .build()

    override fun toString(): String = Gson().toJson(this)
}
