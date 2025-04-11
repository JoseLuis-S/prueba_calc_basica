package es.iesraprog2425.pruebaes.serializable

import es.iesraprog2425.pruebaes.model.Operacion

fun Operacion.toSerializable (separador: String = ";"): String {
    return "numero1=${this.num1}${separador}operador=${this.operador.simbolos[0]}${separador}numero2=${this.num2}${separador}resultado=${this.resultado}\n"
}