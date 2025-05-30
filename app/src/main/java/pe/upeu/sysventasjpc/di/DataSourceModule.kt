package pe.upeu.sysventasjpc.di

import android.content.Context
import androidx.compose.ui.tooling.data.SourceContext
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pe.upeu.sysventasjpc.data.local.DbDataSource
import pe.upeu.sysventasjpc.data.local.dao.MarcaDao
import pe.upeu.sysventasjpc.data.remote.RestCategoria
import pe.upeu.sysventasjpc.data.remote.RestMarca
import pe.upeu.sysventasjpc.data.remote.RestProducto
import pe.upeu.sysventasjpc.data.remote.RestUnidadMedida
import pe.upeu.sysventasjpc.utils.TokenUtils
import pe.upeu.sysventasjpc.data.remote.RestUsuario
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    //Api Rest
    var retrofit: Retrofit?=null
    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl()= TokenUtils.API_URL
    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl:String):
            Retrofit {
        val okHttpClient= OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        if (retrofit==null){
            retrofit= Retrofit.Builder()

                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(baseUrl).build()
        }
        return retrofit!!
    }
    //DB Local
    @Singleton
    @Provides
    fun dbDataSource(@ApplicationContext context: Context): DbDataSource{
        return Room.databaseBuilder(context, DbDataSource::class.java, "almacen_db").build()
    }

    @Singleton
    @Provides
    fun marcaDao(db: DbDataSource): MarcaDao{
        return db.marcaDao()
    }

    //API REST
    @Singleton
    @Provides
    fun restUsuario(retrofit: Retrofit): RestUsuario{
        return retrofit.create(RestUsuario::class.java)
    }

    //Producto

    @Singleton
    @Provides
    fun restProducto(retrofit: Retrofit): RestProducto{
        return retrofit.create(RestProducto::class.java)
    }
    @Singleton
    @Provides
    fun restMarca(retrofit: Retrofit): RestMarca{
        return retrofit.create(RestMarca::class.java)
    }
    @Singleton
    @Provides
    fun restCategoria(retrofit: Retrofit): RestCategoria{
        return retrofit.create(RestCategoria::class.java)
    }
    @Singleton
    @Provides
    fun restUnidadMedida(retrofit: Retrofit): RestUnidadMedida{
        return retrofit.create(RestUnidadMedida::class.java)
    }

}