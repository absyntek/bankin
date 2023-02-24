package com.example.bankintest.data_base

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bankintest.models.Category

/*** Reference technique
 * @author Etienne
 * @since 24/02/2023
 * @see <a href="TODO">Spec</a>
 * @see <a href="TODO">CT</a>
 */

@Dao
interface CategoryDao {

    @Query("SELECT * from category")
    fun getAllCategory(): LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)
}