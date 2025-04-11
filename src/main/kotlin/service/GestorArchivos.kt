package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.ui.IEntradaSalida
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GestorArchivos(private val ui: IEntradaSalida) : IGestorArchivos {

    private val fechaActual = LocalDateTime.now()
    private val fechaFormateada = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(fechaActual)

    override fun obtenerArchivoLog(args: Array<String>): File? {
        val ruta = if (args.isEmpty() || args.size == 1) File(if (args.isEmpty()) "log" else args[0])
        else File(args[0])

        if (!ruta.exists()) {
            ruta.mkdir()
            ui.mostrar("Ruta ${ruta.absolutePath} creada")
        }

        val ficheros = ruta.listFiles()
        val ficheroReciente = ficheros
            ?.filter { it.isFile && it.canRead() }
            ?.sortedByDescending { it.lastModified() }
            ?.firstOrNull()

        return when {
            args.size == 4 -> File("./${args[0]}/log$fechaFormateada.txt").apply { createNewFile() }
            ficheroReciente != null -> {
                ficheroReciente.forEachLine { ui.mostrar(it) }
                ficheroReciente
            }
            else -> {
                ui.mostrar("No existen ficheros de Log válidos")
                File("${ruta.absolutePath}/log$fechaFormateada.txt").apply { createNewFile() }
            }
        }
    }

    override fun escribirOperacion(archivo: File, contenido: String) {
        archivo.appendText(contenido)
    }
}