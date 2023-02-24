package com.example.bankintest.data_base

import android.content.Context
import androidx.room.*
import com.example.bankintest.models.Category
import com.example.bankintest.models.Parent

/*** Reference technique
 * @author Etienne
 * @since 24/02/2023
 * @see <a href="TODO">Spec</a>
 * @see <a href="TODO">CT</a>
 */

@Database(entities = [Category::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bankin_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

class Converter {
    @TypeConverter
    fun parentToString(parent: Parent?): String? {
        return parent?.let { "${parent.id},${parent.resourceUrl},${parent.resourcesType}" }
    }

    @TypeConverter
    fun stringToParent(parString: String?): Parent?{
        return parString?.split(",")?.let {
            Parent(
                id = it[0].toLong(),
                resourcesType = it[2],
                resourceUrl = it[1]
            )
        }
    }
}