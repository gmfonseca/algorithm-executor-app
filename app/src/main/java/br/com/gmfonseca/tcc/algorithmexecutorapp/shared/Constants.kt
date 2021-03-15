package br.com.gmfonseca.tcc.algorithmexecutorapp.shared

object Constants {
    private const val SERVICE_URL = "192.168.0.100"
    const val GRPC_SERVER = "$SERVICE_URL:9090"
    const val REST_SERVER = "http://$SERVICE_URL:8080/"

    object IntegerData {
        const val TABLE = "integer_data"
        const val CONTENT = "integer_data_content"
        const val CASE = "integer_data_case"
        const val COUNT = "integer_data_count"
    }

    object FloatData {
        const val TABLE = "float_data"
        const val CONTENT = "float_data_content"
        const val CASE = "float_data_case"
        const val COUNT = "float_data_count"
    }
}