package pe.upeu.sysventasjpc.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.upeu.sysventasjpc.repository.UsuarioRepository
import pe.upeu.sysventasjpc.repository.UsuarioRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun
            userRepository(userRepos: UsuarioRepositoryImp): UsuarioRepository
}
