package com.example.fitnesspro.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeightDao {
    @Insert
    suspend fun insert(weight: WeightEntity)

    @Update
    suspend fun update(weight: WeightEntity)

    @Query("DELETE FROM weight_table")
    suspend fun clear()

    @Query("SELECT * FROM weight_table WHERE id = :key")
    suspend fun get(key: Long): WeightEntity?

    // going to have here for testing
    @Query("SELECT * FROM weight_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastEntry(): WeightEntity?

    @Query("SELECT * FROM weight_table ORDER BY utc_millis")
    fun getAllWeights(): LiveData<List<WeightEntity>>

    // todo: remove, clear....
}

@Database(entities = [WeightEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FitnessDatabase: RoomDatabase() {
    abstract val weightDao: WeightDao

    // singleton for the database
    // generally best practice.
    companion object {
        @Volatile
        private var INSTANCE: FitnessDatabase? = null

        fun getInstance(context: Context): FitnessDatabase {
            synchronized(this) {
                // to take advantage of smart casting
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        FitnessDatabase::class.java, "fitness_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}