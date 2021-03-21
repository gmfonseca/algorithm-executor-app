package br.com.gmfonseca.tcc.algorithmexecutorapp.shared

object Constants {
    private const val SERVICE_URL = "192.168.0.102"
    const val GRPC_SERVER = "$SERVICE_URL:9090"
    const val REST_SERVER = "http://$SERVICE_URL:8080/"
}