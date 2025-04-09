package es.iesraprog2425.pruebaes.utils

import es.iesraprog2425.pruebaes.model.IExportable
import java.io.File

class Fichero : IUtilFichero {
    override fun leerArchivo(ruta: String): List<String> {
        val lineas = mutableListOf<String>()
        File(ruta).bufferedReader().use { br ->
            br.forEachLine { lineas.add(it) }
        }
        return lineas
    }

    override fun agregarLinea(ruta: String, linea: String): Boolean {
        File(ruta).appendText(linea)
        return true
    }

    override fun <T : IExportable> escribirArchivo(ruta: String, elementos: List<T>): Boolean {
        val lineas = elementos.joinToString("\n") { it.serializar() }
        File(ruta).writeText(lineas)
        return true
    }

    override fun existeFichero(ruta: String): Boolean {
        return File(ruta).exists()
    }

    override fun existeDirectorio(ruta: String): Boolean {
        return File(ruta).isDirectory
    }
}