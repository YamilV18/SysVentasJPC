package pe.upeu.sysventasjpc.repository

import android.util.Log
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import pe.upeu.sysventasjpc.data.local.dao.MarcaDao
import pe.upeu.sysventasjpc.data.remote.RestMarca
import pe.upeu.sysventasjpc.modelo.Marca
import pe.upeu.sysventasjpc.utils.TokenUtils
import pe.upeu.sysventasjpc.utils.isNetworkAvailable

interface MarcaRepository {
    suspend fun findAll(): List<Marca>
    suspend fun findAllR(): Flow<List<Marca>>
}
class MarcaRepositoryImp @Inject constructor(
    private val rest: RestMarca,
    private val dao: MarcaDao,
): MarcaRepository{
    override suspend fun findAll(): List<Marca> {
        val response =
            rest.reportarMarcas(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?:
        emptyList()
        else emptyList()
    }

    override suspend fun findAllR(): Flow<List<Marca>> {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                if (isNetworkAvailable(TokenUtils.CONTEXTO_APPX)) {
                    val data = rest.reportarMarcas(TokenUtils.TOKEN_CONTENT).body()!!
                    dao.insertAll(data)
                }
            }
        } catch (e: Exception) {
            Log.e("ERROR", "Error: ${e.message}")
        }
        return dao.getAll()
    }
}
