package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.Survey
import com.example.data.model.SurveyQuestion
import com.example.ui.viewmodel.RewardsViewModel

@Composable
fun SurveysScreen(viewModel: RewardsViewModel) {
    val context = LocalContext.current
    val categories = listOf("الكل", "تكنولوجيا", "تسوق وفيزا", "ألعاب وترفيه", "ذكاء اصطناعي")
    
    val filteredSurveys = remember(viewModel.selectedSurveyCategory) {
        if (viewModel.selectedSurveyCategory == "الكل") {
            viewModel.surveysList
        } else {
            viewModel.surveysList.filter { it.category == viewModel.selectedSurveyCategory }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "استطلاعات الرأي اليومية",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "شارك برأيك في الاستطلاعات واربح نقاط بطاقات فيزا",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            }
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = AccentGold.copy(alpha = 0.15f),
                border = BorderStroke(1.dp, AccentGold.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.MonetizationOn,
                        contentDescription = null,
                        tint = AccentGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "عائد أدموب عالي",
                        color = AccentGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Market Research Partner Hub (TGM Panel, Ipsos iSay, Surveyeah)
            item {
                MarketResearchPartnerHubCard(
                    onSelectFastSurvey = {
                        val tgmSurvey = viewModel.surveysList.firstOrNull { it.id == "survey_tgm_middle_east" }
                        if (tgmSurvey != null) {
                            viewModel.startSurvey(tgmSurvey)
                        }
                    }
                )
            }

            // Daily Quick Poll Card
            item {
                DailyPollSection(
                    viewModel = viewModel,
                    onVoteSuccess = { points ->
                        Toast.makeText(context, "شكراً لمشاركتك! تم كسب $points نقطة فورية.", Toast.LENGTH_LONG).show()
                    },
                    onVoteError = { err ->
                        Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
                    }
                )
            }

            // Categories Filter Bar
            item {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        text = "تصنيفات الاستطلاعات:",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { category ->
                            val isSelected = viewModel.selectedSurveyCategory == category
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.selectedSurveyCategory = category },
                                label = {
                                    Text(
                                        text = category,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 12.sp
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AccentGold,
                                    selectedLabelColor = SlateDark,
                                    containerColor = SlateMedium,
                                    labelColor = Color.LightGray
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSelected,
                                    borderColor = if (isSelected) AccentGold else SlateLight
                                )
                            )
                        }
                    }
                }
            }

            // Surveys List
            items(filteredSurveys) { survey ->
                SurveyCardItem(
                    survey = survey,
                    onStartSurvey = {
                        viewModel.startSurvey(survey)
                    }
                )
            }

            // Bottom explanation note about points vs AdMob profit
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SlateMedium.copy(alpha = 0.8f)),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, SlateLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = GlowGreen,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "نظام استطلاعات موثوق: كل استطلاع يقدم مكافآت نقاط سريعة ترتبط مباشرة ببطاقات فيزا الافتراضية وشبكة إعلانات أدموب المعتمدة.",
                            color = Color.LightGray,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }

    // Survey Questioning Dialog
    if (viewModel.activeSurveyTaking != null) {
        TakingSurveyDialog(
            viewModel = viewModel,
            survey = viewModel.activeSurveyTaking!!,
            onDismiss = { viewModel.closeSurvey() },
            onCompleted = { reward ->
                Toast.makeText(context, "تهانينا! أكملت الاستطلاع وحصلت على $reward نقطة!", Toast.LENGTH_LONG).show()
            }
        )
    }
}

@Composable
fun DailyPollSection(
    viewModel: RewardsViewModel,
    onVoteSuccess: (Int) -> Unit,
    onVoteError: (String) -> Unit
) {
    val poll = viewModel.dailyPoll
    val hasVoted = viewModel.dailyPollVotedOption != null

    Card(
        colors = CardDefaults.cardColors(containerColor = SlateMedium),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, SlateLight),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("daily_poll_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(AccentOrange.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Poll,
                            contentDescription = null,
                            tint = AccentOrange,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "استطلاع الرأي اليومي المباشر",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = "+3 نقاط",
                    color = AccentGold,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = poll.question,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Options
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                poll.options.forEachIndexed { index, optionText ->
                    val isChosen = viewModel.dailyPollVotedOption == index
                    val percentage = poll.votesPercentages.getOrElse(index) { 25 }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isChosen) AccentGold.copy(alpha = 0.15f) else SlateLight.copy(alpha = 0.6f),
                        border = if (isChosen) BorderStroke(1.5.dp, AccentGold) else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !hasVoted) {
                                viewModel.voteDailyPoll(index, onVoteSuccess, onVoteError)
                            }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = if (isChosen) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                        contentDescription = null,
                                        tint = if (isChosen) AccentGold else Color.Gray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = optionText,
                                        color = if (isChosen) AccentGold else Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                                if (hasVoted) {
                                    Text(
                                        text = "$percentage%",
                                        color = if (isChosen) AccentGold else Color.LightGray,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            if (hasVoted) {
                                Spacer(modifier = Modifier.height(6.dp))
                                LinearProgressIndicator(
                                    progress = { percentage / 100f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(2.dp)),
                                    color = if (isChosen) AccentGold else GlowGreen,
                                    trackColor = SlateDark
                                )
                            }
                        }
                    }
                }
            }

            if (!hasVoted) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "💡 انقر على خيارك المفضل لتسجيل صوتك وكسب النقاط فوراً",
                    color = Color.LightGray,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun SurveyCardItem(
    survey: Survey,
    onStartSurvey: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SlateMedium),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, SlateLight),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("survey_card_${survey.id}")
            .clickable { onStartSurvey() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = when (survey.category) {
                            "تكنولوجيا" -> Color(0xFF2196F3).copy(alpha = 0.2f)
                            "تسوق وفيزا" -> AccentGold.copy(alpha = 0.2f)
                            "ألعاب وترفيه" -> Color(0xFFE91E63).copy(alpha = 0.2f)
                            else -> GlowGreen.copy(alpha = 0.2f)
                        }
                    ) {
                        Text(
                            text = survey.category,
                            color = when (survey.category) {
                                "تكنولوجيا" -> Color(0xFF64B5F6)
                                "تسوق وفيزا" -> AccentGold
                                "ألعاب وترفيه" -> Color(0xFFFF4081)
                                else -> GlowGreen
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    if (survey.isFeatured) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AccentOrange.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "مميز ⭐",
                                color = AccentOrange,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = AccentGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "+${survey.rewardPoints} نقطة",
                        color = AccentGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = survey.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = survey.description,
                color = Color.LightGray,
                fontSize = 12.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${survey.estimatedMinutes} دقائق",
                            color = Color.Gray,
                            fontSize = 11.sp
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${survey.questions.size} أسئلة",
                            color = Color.Gray,
                            fontSize = 11.sp
                        )
                    }
                }

                Button(
                    onClick = onStartSurvey,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentGold,
                        contentColor = SlateDark
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(
                        text = "بدء الاستطلاع",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TakingSurveyDialog(
    viewModel: RewardsViewModel,
    survey: Survey,
    onDismiss: () -> Unit,
    onCompleted: (Int) -> Unit
) {
    val question = survey.questions[viewModel.currentQuestionIndex]
    val totalQuestions = survey.questions.size
    val progress = (viewModel.currentQuestionIndex + 1).toFloat() / totalQuestions

    Dialog(
        onDismissRequest = {
            if (!viewModel.isProcessingAdBridge) {
                onDismiss()
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SlateDark)
                .padding(16.dp)
        ) {
            if (viewModel.isProcessingAdBridge) {
                // AdMob Sponsored Bridge / Interstitial simulation
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = AccentGold.copy(alpha = 0.15f),
                        modifier = Modifier.size(80.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                color = AccentGold,
                                strokeWidth = 3.dp,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "جاري توثيق إجاباتك وربط رصيد فيزا...",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "يتم الآن عرض إعلان الرعاة الرسمي من شبكة أدموب المعتمدة لتقديم ${survey.rewardPoints} نقطة لحسابك.",
                        color = Color.LightGray,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Surface(
                        color = SlateMedium,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "AdMob Unit: ${viewModel.admobBannerId}",
                            color = AccentGold,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            } else {
                // Survey Questionnaire View
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Header Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "إغلاق", tint = Color.White)
                        }
                        Text(
                            text = "السؤال ${viewModel.currentQuestionIndex + 1} من $totalQuestions",
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SlateMedium
                        ) {
                            Text(
                                text = "+${survey.rewardPoints} نقطة",
                                color = AccentGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress Bar
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = AccentGold,
                        trackColor = SlateMedium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Survey Title & Question
                    Text(
                        text = survey.title,
                        color = AccentGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = question.question,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 26.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Options List
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        question.options.forEachIndexed { index, optionText ->
                            val isSelected = viewModel.selectedOptionIndex == index
                            val percentage = question.votesPercentages.getOrElse(index) { 25 }

                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = if (isSelected) AccentGold.copy(alpha = 0.2f) else SlateMedium,
                                border = BorderStroke(
                                    1.5.dp,
                                    if (isSelected) AccentGold else SlateLight
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.answerSurveyQuestion(index)
                                    }
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(
                                                imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                                contentDescription = null,
                                                tint = if (isSelected) AccentGold else Color.Gray
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Text(
                                                text = optionText,
                                                color = if (isSelected) Color.White else Color.LightGray,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                fontSize = 14.sp
                                            )
                                        }

                                        if (viewModel.isQuestionAnswered) {
                                            Text(
                                                text = "$percentage%",
                                                color = if (isSelected) AccentGold else Color.Gray,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp
                                            )
                                        }
                                    }

                                    if (viewModel.isQuestionAnswered) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        LinearProgressIndicator(
                                            progress = { percentage / 100f },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(4.dp)
                                                .clip(RoundedCornerShape(2.dp)),
                                            color = if (isSelected) AccentGold else GlowGreen,
                                            trackColor = SlateDark
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Next / Finish Button
                    Button(
                        onClick = {
                            viewModel.proceedToNextQuestion(onCompleted)
                        },
                        enabled = viewModel.isQuestionAnswered,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("survey_next_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentGold,
                            contentColor = SlateDark,
                            disabledContainerColor = SlateMedium,
                            disabledContentColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = if (viewModel.currentQuestionIndex < totalQuestions - 1) "السؤال التالي" else "توثيق الرأي واستلام المكافأة (${survey.rewardPoints} نقطة)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

// Market Research Partner Hub Card (TGM Panel, Ipsos iSay, Surveyeah)
@Composable
fun MarketResearchPartnerHubCard(
    onSelectFastSurvey: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SlateMedium),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, GlowGreen.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(GlowGreen.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = null,
                            tint = GlowGreen,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "منظومة أبحاث السوق المعتمدة",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "معايير TGM Panel • Ipsos iSay • Surveyeah",
                            color = AccentGold,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = GlowGreen.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "عائد مضاعف 2.5K+",
                        color = GlowGreen,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "استطلاعات ودراسات موثوقة بنقاط عالية ترتبط مباشرة بمشروع فيزا وسرعة السحب الفوري، تضمن للمستخدم أعلى مصداقية وللمطور أعلى عوائد أدموب رسمية.",
                color = Color.LightGray,
                fontSize = 11.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Fast Button
            Button(
                onClick = onSelectFastSurvey,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentGold,
                    contentColor = SlateDark
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "بدء استطلاع TGM لدراسة السوق العربي (+1,500 نقطة)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

