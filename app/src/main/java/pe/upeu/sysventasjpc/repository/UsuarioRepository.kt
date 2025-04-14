package pe.upeu.sysventasjpc.repository

import pe.upeu.sysventasjpc.data.remote.RestUsuario
import pe.upeu.sysventasjpc.modelo.UsuarioDto
import pe.upeu.sysventasjpc.modelo.UsuarioResp
import retrofit2.Response
import javax.inject.Inject

interface UsuarioRepository {
    suspend fun loginUsuario(user: UsuarioDto):
            Response<UsuarioResp>
}
class UsuarioRepositoryImp @Inject constructor(private val
                                               restUsuario: RestUsuario):UsuarioRepository{
    override suspend fun loginUsuario(user:UsuarioDto):
            Response<UsuarioResp>{
        return restUsuario.login(user)
    }
}