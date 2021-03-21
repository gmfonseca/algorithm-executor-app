package br.com.gmfonseca.tcc.algorithmexecutorapp.business.model

enum class Case(val readableName: String) {
    WORST("Pior"), BEST("Melhor")
}

enum class Method(val readableName: String) {
    LOCAL("Local"), REST("REST"), GRPC("gRPC")
}

enum class Algorithm(val readableName: String) {
    BUBBLE("Bubble"), HEAP("Heap"), SELECTION("Selection")
}

enum class DataType(val readableName: String) {
    INTEGER("Integer"), FLOAT("Float"), OBJECT("Object")
}