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
        // High-Yield Surveys inspired by TGM Panel, Ipsos iSay, and Surveyeah
        Survey(
            id = "survey_tgm_middle_east",
            title = "استطلاع TGM Panel لدراسة السوق واستهلاك الوطن العربي",
            category = "تسوق وفيزا",
            description = "دراسة معتمدة للمستهلك العربي تمنحك أعلى عائد نقاط لسحب بطاقة فيزا بقيمة 3$ السريعة.",
            rewardPoints = 1500,
            estimatedMinutes = 3,
            isFeatured = true,
            questions = listOf(
                SurveyQuestion(
                    id = 1,
                    question = "ما هي الخدمات والمنتجات التي تنفق عليها النسبة الأكبر من دخلك الشهري؟",
                    options = listOf("التسوق الإلكتروني وشراء الملابس والأجهزة", "خدمات الترفيه والألعاب والاشتراكات الرقمية", "المطاعم وطلبات التوصيل الغذائية", "الفواتير والخدمات المنزلية والتعليم"),
                    votesPercentages = listOf(42, 28, 18, 12)
                ),
                SurveyQuestion(
                    id = 2,
                    question = "ما هو الدافع الأكبر الذي يجعلك تستخدم تطبيقات ومواقع الاستطلاعات؟",
                    options = listOf("الحد الأدنى المنخفض للسحب السريع (مثل معيار TGM $3)", "سهولة استلام بطاقات فيزا الافتراضية عبر الإيميل", "التحويل النقدي المباشر للمحفظة الذكية", "التسلية ومشاركة الرأي في أوقات الفراغ"),
                    votesPercentages = listOf(56, 26, 14, 4)
                ),
                SurveyQuestion(
                    id = 3,
                    question = "كيف تقيّم سرعة معالجة تحويل أرباحك الرقمية؟",
                    options = listOf("أفضل التحويل الفوري خلال 24 ساعة", "لا مانع من الانتظار 3 أيام إذا كانت الأرباح مضمونة", "أفضل السحب التلقائي عند الوصول للحد الأدنى", "حسب حجم المبلغ المطلوب"),
                    votesPercentages = listOf(64, 22, 10, 4)
                )
            )
        ),
        Survey(
            id = "survey_ipsos_global_brands",
            title = "دراسة Ipsos iSay لأبحاث السوق والعلامات التجارية العالمية",
            category = "تكنولوجيا",
            description = "استبيان موثوق 100% برعاية أكبر شركات أبحاث السوق لتقييم المنتجات التكنولوجية والدولية.",
            rewardPoints = 2000,
            estimatedMinutes = 4,
            isFeatured = true,
            questions = listOf(
                SurveyQuestion(
                    id = 1,
                    question = "أي من العلامات التجارية التكنولوجية تثق بها أكثر لبياناتك الشخصية؟",
                    options = listOf("Google / أندرويد", "Apple / آيفون", "Samsung / سامسونج", "شركات أخرى"),
                    votesPercentages = listOf(45, 33, 17, 5)
                ),
                SurveyQuestion(
                    id = 2,
                    question = "ما مدى تأثير إعلانات الفيديو الرقمية على قرارك بتجربة تطبيق أو لعبة جديدة؟",
                    options = listOf("تؤثر بشكل كبير إذا كانت تقدم مكافآت حقيقية", "أحياناً إذا كان المحتوى جذاباً ومفيداً", "نادراً ما أهتم بالإعلانات الترويجية", "أعتمد على تقييمات وتجارب المستخدمين"),
                    votesPercentages = listOf(58, 25, 11, 6)
                ),
                SurveyQuestion(
                    id = 3,
                    question = "هل قمت بالشراء من متجر دولي (مثل Amazon / AliExpress) خلال الـ 6 أشهر الماضية؟",
                    options = listOf("نعم باستخدام كارت فيزا دولي مسبق الدفع", "نعم عبر محافظ الدفع الإلكترونية", "أنوي الشراء قريباً عند توفر بطاقة مناسبة", "أفضل المتاجر المحلية المباشرة"),
                    votesPercentages = listOf(52, 24, 18, 6)
                )
            )
        ),
        Survey(
            id = "survey_surveyeah_visa_cards",
            title = "استبيان Surveyeah المباشر لشحن وإصدار بطاقات فيزا",
            category = "تسوق وفيزا",
            description = "استطلاع متخصص لتسريع إصدار بطاقات فيزا الرقمية المسبقة الدفع عبر البريد الإلكتروني.",
            rewardPoints = 2500,
            estimatedMinutes = 4,
            isFeatured = true,
            questions = listOf(
                SurveyQuestion(
                    id = 1,
                    question = "في أي المجالات تخطط لاستخدام بطاقة فيزا الافتراضية المشحونة؟",
                    options = listOf("تفعيل الاشتراكات الدولية (Google Play / Netflix / ChatGPT)", "شحن الألعاب الرقمية (PUBG / Free Fire / Roblox)", "الشراء والتسوق من المواقع العالمية والمحلية", "الاحتفاظ بها كرصيد ادخاري رقمي آمن"),
                    votesPercentages = listOf(48, 30, 16, 6)
                ),
                SurveyQuestion(
                    id = 2,
                    question = "ما هي البيانات التي تفضل استلام بطاقة الفيزا من خلالها؟",
                    options = listOf("إرسال رقم البطاقة وCVV للبريد الإلكتروني مباشرة (معيار Surveyeah)", "عرض بيانات البطاقة فوراً داخل التطبيق ونسخها", "كلاهما معاً لتوفير أقصى درجات الأمان والسرعة", "تحويل القيمة لكاش محلي"),
                    votesPercentages = listOf(60, 24, 12, 4)
                ),
                SurveyQuestion(
                    id = 3,
                    question = "هل ترغب في الحصول على إشعار عند توفر استطلاعات رأي جديدة عالية العائد؟",
                    options = listOf("نعم دائماً لأكون أول المشاركين وأربح أسرع", "فقط للاستطلاعات التي تمنح أكثر من 1,000 نقطة", "أفضل الدخول للتطبيق وتفقده بنفسي", "أسبوعياً فقط"),
                    votesPercentages = listOf(72, 18, 7, 3)
                )
            )
        ),
        Survey(
            id = "survey_tech_2026",
            title = "استطلاع الهواتف الذكية وتفضيلات المستخدمين",
            category = "تكنولوجيا",
            description = "شارك برأيك حول مواصفات الهواتف وميزات الذكاء الاصطناعي الأكثر استخداماً لديك.",
            rewardPoints = 800,
            estimatedMinutes = 2,
            isFeatured = false,
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
            id = "survey_gaming_media",
            title = "استطلاع الألعاب الإلكترونية والترفيه الرقمي",
            category = "ألعاب وترفيه",
            description = "ما هي الألعاب الأكثر شعبية والمنصات التي تقضي معها وقت فراغك؟",
            rewardPoints = 600,
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
            rewardPoints = 600,
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
