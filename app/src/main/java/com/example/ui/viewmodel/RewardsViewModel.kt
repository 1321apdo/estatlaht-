package com.example.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.HighValueAdSenseTask
import com.example.data.model.HighValueTaskManager
import com.example.data.model.RedemptionRequest
import com.example.data.model.Survey
import com.example.data.model.SurveyDataProvider
import com.example.data.model.SurveyQuestion
import com.example.data.model.TaskHistory
import com.example.data.model.UserPoints
import com.example.data.repository.RewardsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.random.Random

data class EconomicsStats(
    val userPoints: Int,
    val userCashValueUsd: Double,
    val estimatedAdMobImpressions: Int,
    val estimatedAdMobRevenueUsd: Double,
    val developerNetProfitUsd: Double,
    val profitMarginPercent: Int
)

class RewardsViewModel(private val repository: RewardsRepository) : ViewModel() {

    // Expose flows from Repository as StateFlows
    val userPoints: StateFlow<UserPoints?> = repository.userPointsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserPoints(id = 1, totalPoints = 0))

    val taskHistory: StateFlow<List<TaskHistory>> = repository.allTaskHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val redemptionHistory: StateFlow<List<RedemptionRequest>> = repository.allRedemptionRequests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // AdMob publisher and app IDs configured for monetization
    val adsensePublisherId = "ca-pub-8214981197698574"
    val admobAppId = "ca-app-pub-8214981197698574~7842643561"
    val admobBannerId = "ca-app-pub-8214981197698574/4237977455"
    val admobInterstitialId = "ca-app-pub-8214981197698574/8833910245"
    val admobRewardedId = "ca-app-pub-8214981197698574/6912384751"

    // --- Strategic AdMob Earnings (الربح الاستراتيجي) ---
    fun completeStrategicAdReward(adType: String, points: Int, onSuccess: (Int) -> Unit) {
        viewModelScope.launch {
            repository.completeTask("ربح استراتيجي أدموب ($adType)", points)
            onSuccess(points)
        }
    }

    // --- Personal Free Key ---
    val personalFreeKey = "KEY-FREE-APDO-8214"

    // --- High-Value AdSense Tasks & Instant Notifications ---
    val highValueTasks: List<HighValueAdSenseTask> = HighValueTaskManager.sampleTasks

    fun completeHighValueTask(
        task: HighValueAdSenseTask,
        onSuccess: (Int) -> Unit
    ) {
        viewModelScope.launch {
            repository.completeTask("ad_high_value_${task.id}", task.rewardPoints)
            onSuccess(task.rewardPoints)
        }
    }

    // --- Survey System State ---
    val surveysList: List<Survey> = SurveyDataProvider.sampleSurveys
    val dailyPoll: SurveyQuestion = SurveyDataProvider.dailyPollQuestion
    var selectedSurveyCategory by mutableStateOf("الكل")
    var activeSurveyTaking by mutableStateOf<Survey?>(null)
    var currentQuestionIndex by mutableStateOf(0)
    var selectedOptionIndex by mutableStateOf<Int?>(null)
    var isQuestionAnswered by mutableStateOf(false)
    var isProcessingAdBridge by mutableStateOf(false)
    var dailyPollVotedOption by mutableStateOf<Int?>(null)

    // --- Visa Project State ---
    var visaCardholderName by mutableStateOf("APDO USER")
    var visaCardNumber by mutableStateOf("4152 •••• •••• 8214")
    var visaExpiryDate by mutableStateOf("12/29")
    var isVisaCardActivated by mutableStateOf(true)

    // --- Math Quiz State ---
    var quizQuestion by mutableStateOf("")
    var quizOptions by mutableStateOf<List<Int>>(emptyList())
    var correctAnswer by mutableStateOf(0)
    var isQuizAnswered by mutableStateOf(false)
    var wasQuizAnswerCorrect by mutableStateOf(false)

    // --- Spin Wheel State ---
    var isSpinning by mutableStateOf(false)
    var targetRotationAngle by mutableStateOf(0f)
    var spinResultPoints by mutableStateOf(0)

    // --- Scratch Card State ---
    var isScratchCardRevealed by mutableStateOf(false)
    var scratchCardRewardPoints by mutableStateOf(0)

    init {
        generateNewQuiz()
        resetScratchCard()
    }

    // --- Survey Operations ---
    fun startSurvey(survey: Survey) {
        activeSurveyTaking = survey
        currentQuestionIndex = 0
        selectedOptionIndex = null
        isQuestionAnswered = false
        isProcessingAdBridge = false
    }

    fun answerSurveyQuestion(optionIndex: Int) {
        if (isQuestionAnswered) return
        selectedOptionIndex = optionIndex
        isQuestionAnswered = true
    }

    fun proceedToNextQuestion(onSurveyFinished: (Int) -> Unit) {
        val survey = activeSurveyTaking ?: return
        if (currentQuestionIndex < survey.questions.size - 1) {
            currentQuestionIndex++
            selectedOptionIndex = null
            isQuestionAnswered = false
        } else {
            // Survey completed! Show AdMob bridge to simulate interstitial ad, then award points
            isProcessingAdBridge = true
            viewModelScope.launch {
                delay(2000) // Simulate rewarded/interstitial ad view
                val reward = survey.rewardPoints
                repository.completeTask("survey_${survey.id}", reward)
                isProcessingAdBridge = false
                activeSurveyTaking = null
                onSurveyFinished(reward)
            }
        }
    }

    fun closeSurvey() {
        activeSurveyTaking = null
        currentQuestionIndex = 0
        selectedOptionIndex = null
        isQuestionAnswered = false
        isProcessingAdBridge = false
    }

    fun voteDailyPoll(optionIndex: Int, onSuccess: (Int) -> Unit, onError: (String) -> Unit) {
        if (dailyPollVotedOption != null) {
            onError("لقد قمت بالتصويت في استطلاع اليوم بالفعل!")
            return
        }
        val history = taskHistory.value
        if (isTaskCompletedToday(history, "daily_poll")) {
            onError("لقد قمت بالمشاركة في استطلاع الرأي اليومي بالفعل! عد غداً.")
            return
        }

        dailyPollVotedOption = optionIndex
        viewModelScope.launch {
            val points = 3 // 3 points for quick daily poll (minimal points for visitor)
            repository.completeTask("daily_poll", points)
            onSuccess(points)
        }
    }

    // --- Economics Calculation ---
    // User exchange rate: 10,000 points = $1.00 USD (Low user points payout)
    // AdMob monetization: Multi-ad exposure (Banner + Interstitial + App open) earns Developer ~$28.00 eCPM
    // Net profit margin for developer: 90% - 95%
    fun getEconomicsStats(points: Int): EconomicsStats {
        val userValue = points / 10000.0 // 10000 points = $1.00 USD (very economical for developer)
        val estimatedImpressions = (points * 4).coerceAtLeast(15) // High ad frequency per point gathered
        val adMobRevenue = (estimatedImpressions / 1000.0) * 28.0 // $28 eCPM from real ads
        val netProfit = (adMobRevenue - userValue).coerceAtLeast(0.0)
        val margin = if (adMobRevenue > 0) ((netProfit / adMobRevenue) * 100).toInt().coerceIn(88, 96) else 92
        return EconomicsStats(
            userPoints = points,
            userCashValueUsd = userValue,
            estimatedAdMobImpressions = estimatedImpressions,
            estimatedAdMobRevenueUsd = adMobRevenue,
            developerNetProfitUsd = netProfit,
            profitMarginPercent = margin
        )
    }

    // --- Daily Check-in ---
    fun performDailyCheckIn(onSuccess: (Int) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val history = taskHistory.value
            if (isTaskCompletedToday(history, "daily_checkin")) {
                onError("لقد قمت بتسجيل الدخول اليومي بالفعل! عد غداً.")
            } else {
                val reward = 5 // Low visitor reward: 5 points
                repository.completeTask("daily_checkin", reward)
                onSuccess(reward)
            }
        }
    }

    // --- Spin the Wheel ---
    fun spinWheel(onSpinFinished: (Int) -> Unit) {
        if (isSpinning) return
        isSpinning = true
        
        // Low points sectors (1, 2, 3, 5, 8, 2, 4, 6)
        val sectors = listOf(1, 2, 3, 5, 8, 2, 4, 6)
        val randomIndex = Random.nextInt(sectors.size)
        spinResultPoints = sectors[randomIndex]

        val sectorAngle = 360f / sectors.size
        val sectorCenterAngle = (randomIndex * sectorAngle) + (sectorAngle / 2f)
        val baseSpins = 360f * 6
        targetRotationAngle = baseSpins + (360f - sectorCenterAngle)

        viewModelScope.launch {
            kotlinx.coroutines.delay(4000)
            repository.completeTask("spin_wheel", spinResultPoints)
            isSpinning = false
            onSpinFinished(spinResultPoints)
        }
    }

    // --- Scratch Card ---
    fun resetScratchCard() {
        isScratchCardRevealed = false
        scratchCardRewardPoints = Random.nextInt(2, 6) // Low scratch reward: 2 to 5 points
    }

    fun revealScratchCardReward(onSuccess: (Int) -> Unit) {
        if (isScratchCardRevealed) return
        isScratchCardRevealed = true
        viewModelScope.launch {
            repository.completeTask("scratch_card", scratchCardRewardPoints)
            onSuccess(scratchCardRewardPoints)
        }
    }

    // --- Math Quiz ---
    fun generateNewQuiz() {
        val num1 = Random.nextInt(5, 30)
        val num2 = Random.nextInt(2, 15)
        val isAddition = Random.nextBoolean()
        
        if (isAddition) {
            quizQuestion = "$num1 + $num2"
            correctAnswer = num1 + num2
        } else {
            quizQuestion = "$num1 × $num2"
            correctAnswer = num1 * num2
        }

        val optionsSet = mutableSetOf(correctAnswer)
        while (optionsSet.size < 4) {
            val wrongOffset = Random.nextInt(-10, 15)
            val wrongAns = correctAnswer + wrongOffset
            if (wrongAns != correctAnswer && wrongAns > 0) {
                optionsSet.add(wrongAns)
            }
        }
        quizOptions = optionsSet.toList().shuffled()
        isQuizAnswered = false
        wasQuizAnswerCorrect = false
    }

    fun answerQuiz(selectedOption: Int, onResult: (Boolean, Int) -> Unit) {
        if (isQuizAnswered) return
        isQuizAnswered = true
        wasQuizAnswerCorrect = (selectedOption == correctAnswer)
        val points = if (wasQuizAnswerCorrect) 5 else 0 // 5 points for correct answer
        
        viewModelScope.launch {
            if (wasQuizAnswerCorrect) {
                repository.completeTask("math_quiz", points)
            }
            onResult(wasQuizAnswerCorrect, points)
        }
    }

    // --- Redemption/Withdraw ---
    fun submitRedemptionRequest(
        paymentMethod: String,
        paymentDetails: String,
        pointsAmount: Int,
        moneyAmount: Double,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (paymentDetails.isBlank()) {
            onError("الرجاء إدخال تفاصيل الدفع بشكل صحيح!")
            return
        }

        val currentPoints = userPoints.value?.totalPoints ?: 0
        if (currentPoints < pointsAmount) {
            onError("رصيد نقاطك غير كافٍ لإجراء هذا السحب!")
            return
        }

        viewModelScope.launch {
            val result = repository.requestRedemption(
                paymentMethod = paymentMethod,
                paymentDetails = paymentDetails,
                pointsAmount = pointsAmount,
                moneyAmount = moneyAmount
            )
            if (result) {
                onSuccess()
            } else {
                onError("حدث خطأ أثناء معالجة طلب السحب.")
            }
        }
    }

    // --- Free Key & Promo Redeem ---
    fun claimFreeKey(
        inputCode: String,
        onSuccess: (Int, String) -> Unit,
        onError: (String) -> Unit
    ) {
        val cleanCode = inputCode.trim().uppercase()
        if (cleanCode.isBlank()) {
            onError("الرجاء إدخال رمز المفتاح المجاني!")
            return
        }

        viewModelScope.launch {
            val history = taskHistory.value
            val taskKey = "free_key_$cleanCode"
            
            // Check if already used
            val alreadyClaimed = history.any { it.taskName == taskKey }
            if (alreadyClaimed) {
                onError("لقد قمت باستخدام هذا المفتاح المجاني من قبل!")
                return@launch
            }

            // Determine points reward based on code (Low point rewards for visitor economy)
            val rewardPoints = when {
                cleanCode == "FREE500" -> 50
                cleanCode == "APDO1321" -> 100
                cleanCode == "FREEKEY2026" -> 30
                cleanCode == "ADSENSE" -> 25
                cleanCode == personalFreeKey -> 50
                cleanCode.startsWith("KEY-") || cleanCode.startsWith("FREE-") || cleanCode.startsWith("VIP-") -> 20
                cleanCode.length >= 4 -> 15
                else -> 0
            }

            if (rewardPoints > 0) {
                repository.completeTask(taskKey, rewardPoints)
                onSuccess(rewardPoints, cleanCode)
            } else {
                onError("رمز المفتاح غير صحيح. حاول تجربة المفتاح المجاني الخاص بك!")
            }
        }
    }

    // --- Strategic Online AdMob Monetization Rewards ---
    fun claimStrategicAdMobReward(points: Int = 100, taskTitle: String = "مشاهدة إعلان أدموب الاستراتيجي", onSuccess: (Int) -> Unit) {
        viewModelScope.launch {
            val taskId = "admob_strategic_${System.currentTimeMillis()}"
            repository.completeTask(taskId, points)
            onSuccess(points)
        }
    }

    fun boostVisaProcessing(onSuccess: (Int) -> Unit) {
        viewModelScope.launch {
            val bonusPoints = 50
            repository.completeTask("admob_visa_boost_${System.currentTimeMillis()}", bonusPoints)
            onSuccess(bonusPoints)
        }
    }

    // Utility to verify daily limits
    fun isTaskCompletedToday(history: List<TaskHistory>, taskName: String): Boolean {
        val todayStart = getStartOfDayTimestamp()
        return history.any { it.taskName == taskName && it.timestamp >= todayStart }
    }

    private fun getStartOfDayTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}

class RewardsViewModelFactory(private val repository: RewardsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RewardsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RewardsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
