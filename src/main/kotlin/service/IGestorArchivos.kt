package es.iesraprog2425.pruebaes.service

import java.io.File

interface IGestorArchivos {
    fun obtenerArchivoLog(args: Array<String>): File?
    fun escribirOperacion(archivo: File, contenido: String)
}
