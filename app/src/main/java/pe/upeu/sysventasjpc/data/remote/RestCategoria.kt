package pe.upeu.sysventasjpc.data.remote

import pe.upeu.sysventasjpc.modelo.Categoria
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RestCategoria {
    companion object {
        const val BASE_RUTA = "/categorias"
    }
    @GET("${BASE_RUTA}")
    suspend fun reportarCategorias(@Header("Authorization")
                                   token:String): Response<List<Categoria>>
}