package br.com.gmfonseca.tcc.algorithmexecutorapp.business.model

import br.com.gmfonseca.tcc.algorithmexecutorapp.toProto
import br.com.gmfonseca.tcc.proto.AlgorithmExecutor
import java.util.*


class AnComparableObject(
    private val uuid: UUID,
    private val intNumber: Int,
    private val floatNumber: Float
) : Comparable<AnComparableObject> {
    override fun compareTo(other: AnComparableObject): Int {
        return uuid.compareTo(other.uuid)
    }

    fun toAnObject(): AlgorithmExecutor.AnObject = AlgorithmExecutor.AnObject.newBuilder()
        .setFloatNumber(floatNumber)
        .setIntNumber(intNumber)
        .setUuid(uuid.toProto())
        .build()
}