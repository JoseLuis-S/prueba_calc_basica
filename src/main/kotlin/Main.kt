package es.iesraprog2425.pruebaes

import es.iesraprog2425.pruebaes.app.Calculadora
import es.iesraprog2425.pruebaes.model.Operacion
import es.iesraprog2425.pruebaes.model.Operadores
import es.iesraprog2425.pruebaes.serializable.toSerializable
import es.iesraprog2425.pruebaes.service.GestorArchivos
import es.iesraprog2425.pruebaes.service.IGestorArchivos
import es.iesraprog2425.pruebaes.ui.Consola

fun Double.redondearDosDecimales(): Double = String.format("%.2f", this).replace(",", ".").toDouble()

fun main(args: Array<String>) {
    val ui = Consola()
    val calculadora = Calculadora(ui)
    val gestorArchivos: IGestorArchivos = GestorArchivos(ui)
    val archivo = gestorArchivos.obtenerArchivoLog(args)

    if (args.size == 4 && archivo != null) {
        try {
            val num1 = args[1].toDouble().redondearDosDecimales()
            val num2 = args[3].toDouble().redondearDosDecimales()
            val operador = Operadores.getOperador(args[2].firstOrNull())
            val resultado = calculadora.realizarCalculo(num1, operador, num2)

            val contenido = if (resultado::class.simpleName == "InfoCalcExcetion") {
                resultado.toString()
            } else {
                Operacion(num1, num2, operador!!, resultado).toSerializable()
            }
            gestorArchivos.escribirOperacion(archivo, contenido)
            ui.mostrar("Resultado: $resultado")
        } catch (e: Exception) {
            ui.mostrarError("Error: ${e.message}")
        }
    } else if (args.isNotEmpty() && args.size != 1 && args.size != 4) {
        ui.mostrarError("Entrada no válida")
    }

    ui.pausa()
    ui.limpiarPantalla()

    if (archivo != null) {
        do {
            Calculadora(ui).iniciar(archivo)
        } while (ui.preguntar("¿Desea hacer otra operación? (s/si/n/no): "))
    }
}
