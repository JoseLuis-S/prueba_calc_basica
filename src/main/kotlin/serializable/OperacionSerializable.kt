package es.iesraprog2425.pruebaes.serializable

import es.iesraprog2425.pruebaes.model.Operacion

fun Operacion.toSerializable (separador: String = ";"): String {
    return "${this.num1};${this.operador};${this.num2};${this.resultado}"
}