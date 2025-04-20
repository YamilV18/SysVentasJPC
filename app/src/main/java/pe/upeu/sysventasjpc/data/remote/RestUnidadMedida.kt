package pe.upeu.sysventasjpc.data.remote

import pe.upeu.sysventasjpc.modelo.UnidadMedida
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RestUnidadMedida {
    companion object {
        const val BASE_RUTA = "/unidadmedidas"
    }
    @GET("${BASE_RUTA}")
    suspend fun reportarUnidadMedida(@Header("Authorization")
                                     token:String): Response<List<UnidadMedida>>
}