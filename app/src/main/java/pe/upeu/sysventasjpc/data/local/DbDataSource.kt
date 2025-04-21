package pe.upeu.sysventasjpc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.upeu.sysventasjpc.data.local.dao.MarcaDao
import pe.upeu.sysventasjpc.modelo.Marca

@Database(entities = [Marca::class], version = 1)
abstract class DbDataSource: RoomDatabase() {
    abstract fun marcaDao(): MarcaDao
}