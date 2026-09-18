package com.example.data.repository

import com.example.data.dao.TaskDao
import com.example.data.model.RedemptionRequest
import com.example.data.model.TaskHistory
import com.example.data.model.UserPoints
import kotlinx.coroutines.flow.Flow

class RewardsRepository(private val taskDao: TaskDao) {

    val allTaskHistory: Flow<List<TaskHistory>> = taskDao.getAllTaskHistory()
    val allRedemptionRequests: Flow<List<RedemptionRequest>> = taskDao.getAllRedemptionRequests()
    val userPointsFlow: Flow<UserPoints?> = taskDao.getUserPointsFlow()

    suspend fun addPoints(amount: Int) {
        val current = taskDao.getUserPoints() ?: UserPoints(id = 1, totalPoints = 0)
        val updated = current.copy(totalPoints = current.totalPoints + amount)
        taskDao.insertUserPoints(updated)
    }

    suspend fun deductPoints(amount: Int): Boolean {
        val current = taskDao.getUserPoints() ?: UserPoints(id = 1, totalPoints = 0)
        if (current.totalPoints >= amount) {
            val updated = current.copy(totalPoints = current.totalPoints - amount)
            taskDao.insertUserPoints(updated)
            return true
        }
        return false
    }

    suspend fun completeTask(taskName: String, pointsEarned: Int) {
        // Log task in history
        taskDao.insertTaskHistory(TaskHistory(taskName = taskName, pointsEarned = pointsEarned))
        // Add points to user account
        addPoints(pointsEarned)
    }

    suspend fun requestRedemption(paymentMethod: String, paymentDetails: String, pointsAmount: Int, moneyAmount: Double): Boolean {
        val success = deductPoints(pointsAmount)
        if (success) {
            taskDao.insertRedemptionRequest(
                RedemptionRequest(
                    paymentMethod = paymentMethod,
                    paymentDetails = paymentDetails,
                    pointsAmount = pointsAmount,
                    moneyAmount = moneyAmount,
                    status = "PENDING"
                )
            )
            return true
        }
        return false
    }
}
