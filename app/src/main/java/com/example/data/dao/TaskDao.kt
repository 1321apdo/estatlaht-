package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.RedemptionRequest
import com.example.data.model.TaskHistory
import com.example.data.model.UserPoints
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_history ORDER BY timestamp DESC")
    fun getAllTaskHistory(): Flow<List<TaskHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskHistory(history: TaskHistory)

    @Query("SELECT * FROM redemption_request ORDER BY timestamp DESC")
    fun getAllRedemptionRequests(): Flow<List<RedemptionRequest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRedemptionRequest(request: RedemptionRequest)

    @Query("SELECT * FROM user_points WHERE id = 1 LIMIT 1")
    fun getUserPointsFlow(): Flow<UserPoints?>

    @Query("SELECT * FROM user_points WHERE id = 1 LIMIT 1")
    suspend fun getUserPoints(): UserPoints?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPoints(userPoints: UserPoints)
}
