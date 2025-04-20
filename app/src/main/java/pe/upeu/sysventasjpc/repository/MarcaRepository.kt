package pe.upeu.sysventasjpc.repository

import jakarta.inject.Inject
import pe.upeu.sysventasjpc.data.remote.RestMarca
import pe.upeu.sysventasjpc.modelo.Marca
import pe.upeu.sysventasjpc.utils.TokenUtils

interface MarcaRepository {
    suspend fun findAll(): List<Marca>
}
class MarcaRepositoryImp @Inject constructor(
    private val rest: RestMarca,
): MarcaRepository{
    override suspend fun findAll(): List<Marca> {
        val response =
            rest.reportarMarcas(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?:
        emptyList()
        else emptyList()
    }
}
