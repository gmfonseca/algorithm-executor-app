package br.com.gmfonseca.tcc.algorithmexecutorapp.business.dto

import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.DataType
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class SortDataDto<T: Any>(
    @SerializedName("type") val type: DataType,
    @SerializedName("elements") val elements: List<T>
) {
    val asJson; get() = toString()

    override fun toString(): String = Gson().toJson(this)


}