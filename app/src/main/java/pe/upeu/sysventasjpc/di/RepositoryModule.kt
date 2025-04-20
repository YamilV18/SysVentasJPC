package pe.upeu.sysventasjpc.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.upeu.sysventasjpc.repository.CategoriaRepository
import pe.upeu.sysventasjpc.repository.CategoriaRepositoryImp
import pe.upeu.sysventasjpc.repository.MarcaRepository
import pe.upeu.sysventasjpc.repository.MarcaRepositoryImp
import pe.upeu.sysventasjpc.repository.ProductoRepository
import pe.upeu.sysventasjpc.repository.ProductoRepositoryImp
import pe.upeu.sysventasjpc.repository.UnidadMedidaRepository
import pe.upeu.sysventasjpc.repository.UnidadMedidaRepositoryImp
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

    //Producto
    @Binds
    @Singleton
    abstract fun
            productoRepository(prodRepos: ProductoRepositoryImp): ProductoRepository
    @Binds
    @Singleton
    abstract fun
            marcaRepository(marcaRepos: MarcaRepositoryImp): MarcaRepository
    @Binds
    @Singleton
    abstract fun
            categoriaRepository(categoriaRepos: CategoriaRepositoryImp): CategoriaRepository
    @Binds
    @Singleton
    abstract fun
            unidadMedRepository(unidmedRepos: UnidadMedidaRepositoryImp): UnidadMedidaRepository
}

