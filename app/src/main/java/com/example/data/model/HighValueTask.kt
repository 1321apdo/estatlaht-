package com.example.data.model

data class HighValueAdSenseTask(
    val id: String,
    val title: String,
    val advertiser: String,
    val eCpmCategory: String, // e.g. "مالية وبنوك", "تقنية واستثمار"
    val description: String,
    val rewardPoints: Int,
    val durationMinutes: Int,
    val isHotDeal: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

object HighValueTaskManager {
    val sampleTasks = listOf(
        HighValueAdSenseTask(
            id = "task_fintech_premium",
            title = "استطلاع حلول المدفوعات وبطاقات فيزا الذهبية",
            advertiser = "Google AdSense Financial Partners",
            eCpmCategory = "خدمات مصرفية وفيزا",
            description = "مهمة إعلانية عالية القيمة تمنح أعلى عائد نقاط مع استعراض مزايا بطاقات فيزا.",
            rewardPoints = 25,
            durationMinutes = 2,
            isHotDeal = true
        ),
        HighValueAdSenseTask(
            id = "task_tech_cloud",
            title = "تقييم حلول السحابة والذكاء الاصطناعي 2026",
            advertiser = "Google Cloud & AdSense High eCPM",
            eCpmCategory = "ذكاء اصطناعي وسحابيات",
            description = "أجب عن 3 أسئلة برمجية وسحابية للحصول على مكافأة فورية معتمدة.",
            rewardPoints = 20,
            durationMinutes = 1,
            isHotDeal = true
        ),
        HighValueAdSenseTask(
            id = "task_crypto_invest",
            title = "استطلاع تطبيقات الاستثمار المالي الرقمي",
            advertiser = "Premium AdSense Tier 1",
            eCpmCategory = "استثمار وتداول رقمي",
            description = "شارك تجربتك في التداول الآمن للحصول على رصيد نقاط مضاعف لسحب بطاقة فيزا.",
            rewardPoints = 30,
            durationMinutes = 3,
            isHotDeal = true
        )
    )
}
