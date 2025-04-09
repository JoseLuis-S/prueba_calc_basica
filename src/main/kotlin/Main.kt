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

fun main() {
    val ui = Consola()
    val calculadora = Calculadora(ui)

    do {
        val argEntrada = ui.pedirInfo("Introduce ruta o ruta y operadores (separado por espacios): ").split(" ")

        if (argEntrada.isEmpty() || argEntrada[0].isBlank()) {
            val ruta = File("log")
            if (ruta.exists()) {
                val ficheros = ruta.listFiles()
                // Made by ChatGPT
                val ficheroReciente = ficheros
                    ?.filter { it.isFile && it.canRead() }
                    ?.sortedByDescending { it.lastModified() }
                    ?.firstOrNull()

                if (ficheroReciente != null) {
                    ficheroReciente.forEachLine { ui.mostrar(it) }
                } else {
                    ui.mostrar("No existen ficheros de Log válidos")
                }
            } else {
                ruta.mkdir()
                println("Ruta ${ruta.absolutePath} creada")
            }
        } else if (argEntrada.size == 1) {
            val ruta = File(argEntrada[0])
            if (ruta.exists()) {
                val ficheros = ruta.listFiles()
                // Made by ChatGPT
                val ficheroReciente = ficheros
                    ?.filter { it.isFile && it.canRead() }
                    ?.sortedByDescending { it.lastModified() }
                    ?.firstOrNull()

                if (ficheroReciente != null) {
                    ficheroReciente.forEachLine { ui.mostrar(it) }
                } else {
                    ui.mostrar("No existen ficheros de Log válidos")
                }
            } else {
                ruta.mkdir()
                ui.mostrar("Ruta ${ruta.absolutePath} creada")
            }
        } else if (argEntrada.size == 4) {
            val ruta = File(argEntrada[0])
            if (!ruta.exists()) ruta.mkdir()
            try {
                val num1 = argEntrada[1].toDouble().redondearDosDecimales()
                val num2 = argEntrada[3].toDouble().redondearDosDecimales()
                val operador = Operadores.getOperador(argEntrada[2].firstOrNull())
                val resultado = calculadora.realizarCalculo(num1, operador, num2)
                val fechaActual = LocalDateTime.now()
                val fechaFormateada = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(fechaActual)
                val rutaArchivo = "./${argEntrada[0]}/log$fechaFormateada.txt"
                val archivo = File(rutaArchivo)

                if (resultado::class.simpleName == "InfoCalcExcetion") {
                    archivo.writeText(resultado.toString())
                } else {
                    val operacion = Operacion(num1, num2, operador!!, resultado)
                    archivo.writeText(operacion.toSerializable())
                }

                ui.mostrar("Resultado: $resultado")
            } catch (e: Exception) {
                ui.mostrarError("Error: ${e.message}")
            }
        } else {
            ui.mostrarError("Entrada no válida")
        }

        ui.pausa()
        ui.limpiarPantalla()

    } while (!ui.preguntar("¿Desea hacer otra operación? (s/si/n/no): "))
}
