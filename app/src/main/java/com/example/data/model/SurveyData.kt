package com.example.data.model

data class SurveyQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val votesPercentages: List<Int>
)

data class Survey(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val rewardPoints: Int,
    val estimatedMinutes: Int,
    val questions: List<SurveyQuestion>,
    val isFeatured: Boolean = false
)

object SurveyDataProvider {
    val sampleSurveys: List<Survey> = listOf(
        Survey(
            id = "survey_tech_2026",
            title = "استطلاع الهواتف الذكية وتفضيلات المستخدمين",
            category = "تكنولوجيا",
            description = "شارك برأيك حول مواصفات الهواتف وميزات الذكاء الاصطناعي الأكثر استخداماً لديك.",
            rewardPoints = 8,
            estimatedMinutes = 2,
            isFeatured = true,
            questions = listOf(
                SurveyQuestion(
                    id = 1,
                    question = "ما هو نظام التشغيل الأساسي الذي تفضل استخدامه يومياً؟",
                    options = listOf("أندرويد (Android)", "آبل (iOS)", "كلاهما بنفس الدرجة", "أجهزة أخرى"),
                    votesPercentages = listOf(68, 24, 6, 2)
                ),
                SurveyQuestion(
                    id = 2,
                    question = "ما هي الميزة الأكثر تأثيراً في قرار شرائك لهاتفك القادم؟",
                    options = listOf("عمر البطارية وسرعة الشحن الفائق", "جودة الكاميرات والتصوير الليلي", "سلاسة المعالج وسرعة الألعاب", "السعر الاقتصادي المناسب"),
                    votesPercentages = listOf(44, 26, 18, 12)
                ),
                SurveyQuestion(
                    id = 3,
                    question = "كم ساعة تقريباً تقضيها في تصفح التطبيقات على هاتفك يومياً؟",
                    options = listOf("أقل من ساعتين", "من 2 إلى 4 ساعات", "من 4 إلى 6 ساعات", "أكثر من 6 ساعات"),
                    votesPercentages = listOf(14, 38, 32, 16)
                ),
                SurveyQuestion(
                    id = 4,
                    question = "ما رأيك في ميزات الذكاء الاصطناعي المدمجة في الهواتف الحديثة؟",
                    options = listOf("مفيدة جداً وتوفر الوقت", "جيدة ولكن تحتاج لتطوير", "ميزة تسويقية ثانوية", "لم أقم بتجربتها بعد"),
                    votesPercentages = listOf(52, 28, 12, 8)
                )
            )
        ),
        Survey(
            id = "survey_visa_ecommerce",
            title = "استطلاع التسوق الإلكتروني ومشروع كروت فيزا",
            category = "تسوق وفيزا",
            description = "استطلاع متخصص حول حلول الدفع الرقمي والشراء أونلاين وبطاقات فيزا الافتراضية.",
            rewardPoints = 10,
            estimatedMinutes = 3,
            isFeatured = true,
            questions = listOf(
                SurveyQuestion(
                    id = 1,
                    question = "كيف تفضل تسديد قيمة مشترياتك عبر الإنترنت ومواقع التسوق؟",
                    options = listOf("بطاقة فيزا الافتراضية المسبقة الدفع", "المحافظ الإلكترونية (كاش)", "الدفع نقداً عند الاستلام", "التحويل البنكي المباشر"),
                    votesPercentages = listOf(54, 28, 14, 4)
                ),
                SurveyQuestion(
                    id = 2,
                    question = "ما الميزة الأهم في بطاقة فيزا الافتراضية بالنسبة لك؟",
                    options = listOf("حماية الحساب البنكي الرئيسي وتحديد الرصيد", "إمكانية الشراء من المتاجر الدولية والمحلية", "سهولة الشحن الفوري واستلام الأرباح", "التحكم في الاشتراكات وتجديدها"),
                    votesPercentages = listOf(48, 26, 16, 10)
                ),
                SurveyQuestion(
                    id = 3,
                    question = "كم مرة تقوم بإجراء عمليات شراء أو سداد فواتير إلكترونية شهرياً؟",
                    options = listOf("أكثر من 5 مرات شهرياً", "من مرتين إلى 4 مرات", "مرة واحدة شهرياً", "نادراً عند الضرورة"),
                    votesPercentages = listOf(35, 41, 16, 8)
                ),
                SurveyQuestion(
                    id = 4,
                    question = "هل تفضل استبدال نقاط الاستطلاعات ببطاقة فيزا إلكترونية مشحونة؟",
                    options = listOf("نعم هي الخيار الأفضل والمثالي للشراء", "أفضل السحب النقدي المباشر", "كلاهما ممتاز حسب الحاجة", "أفضل بطاقات الألعاب"),
                    votesPercentages = listOf(62, 22, 11, 5)
                )
            )
        ),
        Survey(
            id = "survey_gaming_media",
            title = "استطلاع الألعاب الإلكترونية والترفيه الرقمي",
            category = "ألعاب وترفيه",
            description = "ما هي الألعاب الأكثر شعبية والمنصات التي تقضي معها وقت فراغك؟",
            rewardPoints = 6,
            estimatedMinutes = 2,
            isFeatured = false,
            questions = listOf(
                SurveyQuestion(
                    id = 1,
                    question = "ما هو تصنيف الألعاب المفضل لديك؟",
                    options = listOf("ألعاب الباتل رويال والشوتر (PUBG / Free Fire)", "ألعاب كرة القدم والرياضة (EA FC / eFootball)", "ألعاب الذكاء والألغاز والتفكير", "ألعاب المغامرات وتقمص الأدوار"),
                    votesPercentages = listOf(46, 28, 16, 10)
                ),
                SurveyQuestion(
                    id = 2,
                    question = "ما هي منصة اللعب الأساسية التي تستخدمها بانتظام؟",
                    options = listOf("الهاتف الذكي المحمول", "أجهزة الكونسول (بلايستيشن / إكس بوكس)", "أجهزة الكمبيوتر PC", "أجهزة التابلت والأجهزة اللوحية"),
                    votesPercentages = listOf(65, 18, 13, 4)
                ),
                SurveyQuestion(
                    id = 3,
                    question = "هل تقوم بشحن البطاقات الرقمية أو العملات داخل ألعابك المفضلة؟",
                    options = listOf("نعم باستخدام كروت فيزا أو محافظ كاش", "أعتمد على جوائز وتطبيقات النقاط المجانية", "أحياناً في أوقات العروض الموسمية", "لا أقوم بالشحن داخل الألعاب"),
                    votesPercentages = listOf(38, 34, 18, 10)
                )
            )
        ),
        Survey(
            id = "survey_ai_future",
            title = "استطلاع الذكاء الاصطناعي وتطبيقات المستقبل",
            category = "ذكاء اصطناعي",
            description = "كيف يغير الذكاء الاصطناعي حياتك اليومية وطريقة عملك ودراستك؟",
            rewardPoints = 6,
            estimatedMinutes = 2,
            isFeatured = false,
            questions = listOf(
                SurveyQuestion(
                    id = 1,
                    question = "ما مدى اعتمادك على روبوتات الذكاء الاصطناعي (مثل Gemini / ChatGPT)؟",
                    options = listOf("يومياً في المهام والكتابة والبحث", "عدة مرات في الأسبوع", "أحياناً للتسلية والتجربة", "لم أبدأ باستخدامها بعد"),
                    votesPercentages = listOf(48, 31, 15, 6)
                ),
                SurveyQuestion(
                    id = 2,
                    question = "ما هو المجال الذي تتوقع أن يحدث فيه الذكاء الاصطناعي التغيير الأكبر؟",
                    options = listOf("التعليم والبحث والترجمة الفورية", "الصحة والطب والرعاية الذكية", "البرمجة وصناعة المحتوى الرقمي", "التجارة والتمويل والتحليلات"),
                    votesPercentages = listOf(42, 24, 22, 12)
                ),
                SurveyQuestion(
                    id = 3,
                    question = "هل ترى أن استطلاعات الرأي الرقمية تقدم نتائج أسرع وأكثر مصداقية؟",
                    options = listOf("نعم وبشكل ملحوظ", "متقاربة مع الاستطلاعات الورقية", "تعتمد على عدد المشاركين", "غير متأكد"),
                    votesPercentages = listOf(66, 16, 12, 6)
                )
            )
        )
    )

    // Daily Quick Poll Question
    val dailyPollQuestion = SurveyQuestion(
        id = 99,
        question = "استطلاع اليوم السريع: ما هي وسيلة السحب المفضلة لديك لتحويل أرباحك؟",
        options = listOf(
            "بطاقة فيزا الافتراضية (Virtual Visa)",
            "فودافون كاش / المحافظ الذكية",
            "رصيد بايبال PayPal بالدولار",
            "بطاقات جوجل بلاي وشحن الألعاب"
        ),
        votesPercentages = listOf(51, 32, 11, 6)
    )
}
