package br.com.gmfonseca.tcc.algorithmexecutorapp.business.service

import android.content.Context
import java.io.File

object FileWriter {

    fun writeCsv(context: Context, data: CsvOutput) {
        val extDir = context.getExternalFilesDir(null) ?: return
        val csvFile = File(extDir.absoluteFile, "${data.algorithm}.csv")

        if (!csvFile.exists()) {
            csvFile.createNewFile()
            // append header
            csvFile.appendText("Algoritmo;Caso;Ambiente;Tipo;Volume;Bateria;Tempo (ms);Vezes\n")
        }

        csvFile.appendText("$data\n")
    }

    data class CsvOutput(
        val algorithm: String,
        val case: String,
        val type: String,
        val volume: Int,
        val battery: Float,
        val spentTime: Long,
        val times: Int
    ) {
        override fun toString(): String {
            return "$algorithm;$case;$type;$volume;$battery;$spentTime;$times"
        }
    }

}