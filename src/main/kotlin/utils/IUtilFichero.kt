package es.iesraprog2425.pruebaes.utils

import es.iesraprog2425.pruebaes.model.IExportable

interface IUtilFichero {
    fun leerArchivo(ruta: String): List<String>
    fun agregarLinea(ruta: String, linea: String): Boolean
    fun <T: IExportable> escribirArchivo(ruta: String, elementos: List<T>): Boolean
    fun existeFichero(ruta: String): Boolean
    fun existeDirectorio(ruta: String): Boolean
}