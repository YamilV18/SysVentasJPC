package pe.upeu.sysventasjpc.repository

import jakarta.inject.Inject
import pe.upeu.sysventasjpc.data.remote.RestCategoria
import pe.upeu.sysventasjpc.modelo.Categoria
import pe.upeu.sysventasjpc.utils.TokenUtils

interface CategoriaRepository {
    suspend fun findAll(): List<Categoria>
}
class CategoriaRepositoryImp @Inject constructor(
    private val rest: RestCategoria,
): CategoriaRepository{
    override suspend fun findAll(): List<Categoria> {
        val response =
            rest.reportarCategorias(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?:
        emptyList()
        else emptyList()
    }
}