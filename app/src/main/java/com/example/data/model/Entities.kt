package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_history")
data class TaskHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskName: String, // "daily_checkin", "spin_wheel", "scratch_card", "math_quiz"
    val pointsEarned: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "redemption_request")
data class RedemptionRequest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val paymentMethod: String, // "Vodafone Cash", "PayPal", "USDT", "Google Play"
    val paymentDetails: String, // Wallet phone or email
    val pointsAmount: Int,
    val moneyAmount: Double, // e.g., 1000 points = $1.00
    val status: String = "PENDING", // "PENDING", "COMPLETED"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_points")
data class UserPoints(
    @PrimaryKey val id: Int = 1,
    val totalPoints: Int = 0
)
