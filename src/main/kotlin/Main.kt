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

fun main(args: Array<String>) {
    val fechaActual = LocalDateTime.now()
    val fechaFormateada = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(fechaActual)
    val ui = Consola()
    val calculadora = Calculadora(ui)
    val archivo: File?

    if (args.isEmpty()) {
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
            archivo = ficheroReciente
        } else {
            ruta.mkdir()
            println("Ruta ${ruta.absolutePath} creada")
            archivo = File("log/log$fechaFormateada.txt")
            archivo.createNewFile()
        }
    } else if (args.size == 1) {
        val ruta = File(args[0])
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
            archivo = ficheroReciente
        } else {
            ruta.mkdir()
            println("Ruta ${ruta.absolutePath} creada")
            archivo = File("log/log$fechaFormateada.txt")
            archivo.createNewFile()
        }
    } else if (args.size == 4) {
        val ruta = File(args[0])
        if (!ruta.exists()) ruta.mkdir()
        val rutaArchivo = "./${args[0]}/log$fechaFormateada.txt"
        archivo = File(rutaArchivo)
        try {
            val num1 = args[1].toDouble().redondearDosDecimales()
            val num2 = args[3].toDouble().redondearDosDecimales()
            val operador = Operadores.getOperador(args[2].firstOrNull())
            val resultado = calculadora.realizarCalculo(num1, operador, num2)

            if (resultado::class.simpleName == "InfoCalcExcetion") {
                archivo.appendText(resultado.toString())
            } else {
                val operacion = Operacion(num1, num2, operador!!, resultado)
                archivo.appendText(operacion.toSerializable())
            }
            ui.mostrar("Resultado: $resultado")
        } catch (e: Exception) {
            ui.mostrarError("Error: ${e.message}")
        }
    } else {
        ui.mostrarError("Entrada no válida")
        archivo = null
    }

    ui.pausa()
    ui.limpiarPantalla()

    if (archivo != null) {
        do {
            Calculadora(ui).iniciar(archivo)
        } while (ui.preguntar("¿Desea hacer otra operación? (s/si/n/no): "))
    }
}
