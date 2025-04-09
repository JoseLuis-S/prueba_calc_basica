package es.iesraprog2425.pruebaes.model

enum class Operadores(val simbolos: List<Char>) {
    SUMA(listOf('+')),
    RESTA(listOf('-')),
    MULTIPLICACION(listOf('*', 'x')),
    DIVISION(listOf(':', '/'));

    companion object {
        fun getOperador(operador: Char?) = operador?.let { op -> entries.find { op in it.simbolos } }
    }
}