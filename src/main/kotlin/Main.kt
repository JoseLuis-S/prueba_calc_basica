package es.iesraprog2425.pruebaes

import es.iesraprog2425.pruebaes.app.Calculadora
import es.iesraprog2425.pruebaes.model.Operacion
import es.iesraprog2425.pruebaes.model.Operadores
import es.iesraprog2425.pruebaes.serializable.toSerializable
import es.iesraprog2425.pruebaes.ui.Consola
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Double.redondearDosDecimales(): Double = String.format("%.2f", this).replace(",", ".").toDouble()

fun formatearFecha(fecha: String): DateTimeFormatter {
    return DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm/SS")
}

fun main() {
    val ui = Consola()
    val calculadora = Calculadora(ui)

    val argEntrada = ui.pedirInfo("Introduce ruta o ruta y operadores (separado por espacios): ").split(" ")

    if (argEntrada.isEmpty()) {
        val ruta = File("./log")
        if (ruta.exists()) {
            val ficherosReciente = ruta.listFiles()
            if (ficherosReciente.isNotEmpty()) {
                ficherosReciente[0].forEachLine { ui.mostrar(it) }
            }
        } else {
            ruta.mkdir()
            println("Ruta ${ruta.absolutePath} creada")
        }
    } else if (argEntrada.size == 1) {
        val ruta = File("./${argEntrada[0]}")
        if (ruta.exists()) {
            val ficherosReciente = ruta.listFiles()
            if (ficherosReciente.isNotEmpty()) {
                ficherosReciente[0].forEachLine { ui.mostrar(it) }
            }
        } else {
            ruta.mkdir()
            ui.mostrar("Ruta ${ruta.absolutePath} creada")
        }
    } else if (argEntrada.size == 4) {
        val ruta = File("./${argEntrada[0]}")
        if (!ruta.exists()) ruta.mkdir()
        try {
            val num1 = argEntrada[1].toDouble().redondearDosDecimales()
            val num2 = argEntrada[3].toDouble().redondearDosDecimales()
            val operador = Operadores.getOperador(argEntrada[2].firstOrNull())
            val resultado = calculadora.realizarCalculo(num1, operador, num2)
            val fechaActual = LocalDateTime.now()
            val rutaArchivo = "./${argEntrada[0]}/log${formatearFecha(fechaActual.toString())}.txt"
            if (resultado::class.simpleName == "InfoCalcExcetion") {
                File(rutaArchivo).writeText(resultado.toString())
            } else {
                val operacion = Operacion(num1, num2, operador!!, resultado)
                File(rutaArchivo).writeText(operacion.toSerializable())
            }
        } catch (e: Exception) {
            ui.mostrarError("$e")
        }
    } else {
        ui.mostrarError("Entrada no valida")
    }
}