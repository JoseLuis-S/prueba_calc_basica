package es.iesraprog2425.pruebaes.serializable

import es.iesraprog2425.pruebaes.model.Operacion

fun Operacion.toSerializable (separador: String = ";"): String {
    return "numero1=${this.num1};operador=${this.operador.simbolos[0]};numero2=${this.num2};resultado=${this.resultado}\n"
}