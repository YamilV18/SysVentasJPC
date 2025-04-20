package pe.upeu.sysventasjpc.repository

import jakarta.inject.Inject
import pe.upeu.sysventasjpc.data.remote.RestUnidadMedida
import pe.upeu.sysventasjpc.modelo.UnidadMedida
import pe.upeu.sysventasjpc.utils.TokenUtils

interface UnidadMedidaRepository {
    suspend fun findAll(): List<UnidadMedida>
}
class UnidadMedidaRepositoryImp @Inject constructor(
    private val rest: RestUnidadMedida,
): UnidadMedidaRepository{
    override suspend fun findAll(): List<UnidadMedida> {
        val response =
            rest.reportarUnidadMedida(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?:
        emptyList()
        else emptyList()
    }
}
