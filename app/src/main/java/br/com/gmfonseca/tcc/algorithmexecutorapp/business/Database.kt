package br.com.gmfonseca.tcc.algorithmexecutorapp.business

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.dao.FloatDataDao
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.dao.IntegerDataDao
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.DataCaseConverter
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.FloatData
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.IntegerData
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.ListConverter
import androidx.room.Database as DatabaseAnnotation

@DatabaseAnnotation(
    entities = [IntegerData::class, FloatData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [DataCaseConverter::class, ListConverter::class])
abstract class Database : RoomDatabase() {

    abstract fun integerDataDao(): IntegerDataDao
    abstract fun floatDataDao(): FloatDataDao

    override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
        return config.sqliteOpenHelperFactory.create(null)
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllTables() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private var INSTANCE: Database? = null

        fun getInstance(context: Context): Database {
            val instance = INSTANCE ?: Room.databaseBuilder(
                context,
                Database::class.java,
                "database.db"
            ).createFromAsset("prepopulated_database.db").build()

            if (INSTANCE == null) {
                INSTANCE = instance
            }

            return instance
        }
    }
}