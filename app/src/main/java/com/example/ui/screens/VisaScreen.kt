package com.example.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.RewardsViewModel
import com.example.util.AdMobManager
import java.util.Locale

// Visa & Payout Option Data Class
data class VisaRedeemItem(
    val id: Int,
    val category: String, // "Virtual Visa", "Visa Cash", "Other"
    val title: String,
    val points: Int,
    val cashValue: Double,
    val currency: String, // "USD", "EGP"
    val description: String,
    val isVirtualCard: Boolean = false
)

@Composable
fun VisaRedeemScreen(viewModel: RewardsViewModel, currentPoints: Int) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("Virtual Visa") }
    var cardholderNameInput by remember { mutableStateOf(viewModel.visaCardholderName) }
    var cardNumberInput by remember { mutableStateOf("") }
    
    val allOptions = remember {
        listOf(
            VisaRedeemItem(
                id = 1,
                category = "Virtual Visa",
                title = "بطاقة فيزا افتراضية $5 دولار",
                points = 50000,
                cashValue = 5.0,
                currency = "USD",
                description = "بطاقة فيزا رقمية مسبقة الدفع صالحة للتسوق أونلاين وتفعيل الاشتراكات الدولية",
                isVirtualCard = true
            ),
            VisaRedeemItem(
                id = 2,
                category = "Virtual Visa",
                title = "بطاقة فيزا افتراضية $10 دولار",
                points = 100000,
                cashValue = 10.0,
                currency = "USD",
                description = "بطاقة فيزا برقم 16 رقم وتاريخ انتهاء وCVV للتسوق الدولي وشحن الألعاب",
                isVirtualCard = true
            ),
            VisaRedeemItem(
                id = 3,
                category = "Virtual Visa",
                title = "بطاقة فيزا VIP بقيمة $25 دولار",
                points = 250000,
                cashValue = 25.0,
                currency = "USD",
                description = "بطاقة فيزا بلاتينية افتراضية للمشتريات الكبرى وتفعيل الخدمات العالمية",
                isVirtualCard = true
            ),
            VisaRedeemItem(
                id = 4,
                category = "Visa Cash",
                title = "كاش بطاقة فيزا / ميزة 100 جنيه",
                points = 25000,
                cashValue = 100.0,
                currency = "EGP",
                description = "تحويل مباشر لحساب كارت فيزا أو كارت ميزة البنكي المصري"
            ),
            VisaRedeemItem(
                id = 5,
                category = "Visa Cash",
                title = "كاش بطاقة فيزا / ميزة 250 جنيه",
                points = 62500,
                cashValue = 250.0,
                currency = "EGP",
                description = "إيداع كاش فوري على رقم بطاقة فيزا البنكية"
            ),
            VisaRedeemItem(
                id = 6,
                category = "Visa Cash",
                title = "كاش بطاقة فيزا / ميزة 500 جنيه",
                points = 125000,
                cashValue = 500.0,
                currency = "EGP",
                description = "تحويل بنكي مباشر عبر شبكة فيزا للمدفوعات السريعة"
            ),
            VisaRedeemItem(
                id = 7,
                category = "Other",
                title = "فودافون كاش 100 جنيه",
                points = 25000,
                cashValue = 100.0,
                currency = "EGP",
                description = "تحويل كاش فوري لمحفظة فودافون كاش"
            ),
            VisaRedeemItem(
                id = 8,
                category = "Other",
                title = "رصيد بايبال $5 دولار",
                points = 50000,
                cashValue = 5.0,
                currency = "USD",
                description = "إرسال رصيد مباشر لحسابك في بايبال"
            )
        )
    }

    var selectedOption by remember { mutableStateOf(allOptions[0]) }

    val economics = remember(currentPoints) {
        viewModel.getEconomicsStats(currentPoints)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "مشروع فيزا والسحب المعتمد",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Interactive 3D/Card Style Virtual Visa Card Preview
            item {
                VirtualVisaCardWidget(
                    currentPoints = currentPoints,
                    cardholderName = viewModel.visaCardholderName,
                    cardNumber = viewModel.visaCardNumber,
                    expiryDate = viewModel.visaExpiryDate
                )
            }

            // Developer AdMob Profit & Economics Dashboard Card
            item {
                AdMobEconomicsCard(
                    viewModel = viewModel,
                    stats = economics
                )
            }

            // Category Selection Tabs
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val categories = listOf(
                        "Virtual Visa" to "فيزا افتراضية 💳",
                        "Visa Cash" to "كاش فيزا / ميزة 🏦",
                        "Other" to "محافظ وبايبال 📱"
                    )
                    categories.forEach { (catKey, catLabel) ->
                        val isSel = selectedCategory == catKey
                        Button(
                            onClick = {
                                selectedCategory = catKey
                                selectedOption = allOptions.firstOrNull { it.category == catKey } ?: allOptions[0]
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSel) AccentGold else SlateMedium,
                                contentColor = if (isSel) SlateDark else Color.LightGray
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = catLabel,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            // Options List for current category
            val filtered = allOptions.filter { it.category == selectedCategory }
            items(filtered) { option ->
                val isSelected = selectedOption.id == option.id
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) SlateLight else SlateMedium
                    ),
                    shape = RoundedCornerShape(14.dp),
                    border = if (isSelected) BorderStroke(1.5.dp, AccentGold) else BorderStroke(1.dp, SlateLight.copy(alpha = 0.5f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedOption = option }
                        .testTag("redeem_option_${option.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { selectedOption = option },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = AccentGold,
                                unselectedColor = Color.LightGray
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = option.title,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = option.description,
                                color = Color.LightGray,
                                fontSize = 11.sp,
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "تتطلب: ${String.format("%,d", option.points)} نقطة",
                                color = AccentGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SlateDark)
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (option.currency == "USD") "$${option.cashValue.toInt()}" else "${option.cashValue.toInt()} ج.م",
                                color = GlowGreen,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            // Input Details Form
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = SlateMedium),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, SlateLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "بيانات استلام أرباح الفيزا:",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        // Cardholder Name Input
                        OutlinedTextField(
                            value = cardholderNameInput,
                            onValueChange = {
                                cardholderNameInput = it
                                viewModel.visaCardholderName = it
                            },
                            label = { Text("اسم صاحب البطاقة (بالحروف الإنجليزية)", color = Color.Gray, fontSize = 12.sp) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("cardholder_name_input"),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = AccentGold,
                                unfocusedBorderColor = SlateLight,
                                focusedContainerColor = SlateDark,
                                unfocusedContainerColor = SlateDark
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Card Number / Details Input
                        OutlinedTextField(
                            value = cardNumberInput,
                            onValueChange = { cardNumberInput = it },
                            label = {
                                Text(
                                    text = if (selectedOption.isVirtualCard) "البريد الإلكتروني لاستلام كود وبيانات الفيزا" else "رقم بطاقة الفيزا (16 رقم) أو رقم المحفظة",
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            },
                            placeholder = {
                                Text(
                                    text = if (selectedOption.isVirtualCard) "user@example.com" else "4152 xxxx xxxx xxxx",
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("card_number_input"),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = if (selectedOption.isVirtualCard) KeyboardType.Email else KeyboardType.Phone
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = AccentGold,
                                unfocusedBorderColor = SlateLight,
                                focusedContainerColor = SlateDark,
                                unfocusedContainerColor = SlateDark
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Submit Button
                        Button(
                            onClick = {
                                if (cardNumberInput.isBlank()) {
                                    Toast.makeText(context, "يرجى إدخال بيانات الاستلام بشكل صحيح!", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                viewModel.submitRedemptionRequest(
                                    paymentMethod = selectedOption.title,
                                    paymentDetails = "$cardNumberInput ($cardholderNameInput)",
                                    pointsAmount = selectedOption.points,
                                    moneyAmount = selectedOption.cashValue,
                                    onSuccess = {
                                        cardNumberInput = ""
                                        Toast.makeText(context, "تم تقديم طلب سحب ${selectedOption.title} بنجاح! جاري معالجة البطاقة.", Toast.LENGTH_LONG).show()
                                    },
                                    onError = { err ->
                                        Toast.makeText(context, err, Toast.LENGTH_LONG).show()
                                    }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("submit_visa_redeem_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AccentGold,
                                contentColor = SlateDark
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CreditCard,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "تأكيد طلب السحب عبر مشروع فيزا",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // AdMob Strategic Acceleration Button
                        val activity = context as? Activity
                        OutlinedButton(
                            onClick = {
                                if (activity != null) {
                                    AdMobManager.showRewarded(
                                        activity = activity,
                                        onRewardEarned = { _ ->
                                            viewModel.boostVisaProcessing { bonus ->
                                                Toast.makeText(context, "⚡ تم منحك أولوية المعالجة السريعة وكسب $bonus نقطة إضافية!", Toast.LENGTH_LONG).show()
                                            }
                                        },
                                        onComplete = {}
                                    )
                                } else {
                                    viewModel.boostVisaProcessing { bonus ->
                                        Toast.makeText(context, "⚡ تم منحك أولوية المعالجة السريعة وكسب $bonus نقطة إضافية!", Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            border = BorderStroke(1.dp, GlowGreen),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = GlowGreen),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Bolt, contentDescription = null, tint = GlowGreen, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "تسريع معالجة طلبك عبر إعلان أدموب (+50 نقطة)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// 3D/Card Styled Virtual Visa Card Widget
@Composable
fun VirtualVisaCardWidget(
    currentPoints: Int,
    cardholderName: String,
    cardNumber: String,
    expiryDate: String
) {
    val usdValue = currentPoints / 10000.0 // 10,000 points = $1 USD
    val egpValue = usdValue * 50.0

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(12.dp, RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F2027),
                        Color(0xFF203A43),
                        Color(0xFF2C5364)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Row: Visa Brand and Chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Golden EMV Chip representation
                    Box(
                        modifier = Modifier
                            .size(36.dp, 28.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(AccentGold)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Default.Contactless,
                        contentDescription = "Contactless",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(22.dp)
                    )
                }

                // VISA Label
                Text(
                    text = "VISA",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 2.sp
                )
            }

            // Card Number Row
            Text(
                text = cardNumber,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 3.sp
            )

            // Bottom Row: Holder, Expiry & Balance
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "CARDHOLDER",
                        color = Color.LightGray,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = cardholderName.uppercase(),
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "EXPIRES",
                        color = Color.LightGray,
                        fontSize = 9.sp
                    )
                    Text(
                        text = expiryDate,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Balance display badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = AccentGold.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, AccentGold.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "$${String.format(Locale.US, "%.2f", usdValue)} | ${egpValue.toInt()} ج.م",
                        color = AccentGold,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

// Dedicated AdMob Profit & Economics Dashboard Card
@Composable
fun AdMobEconomicsCard(
    viewModel: RewardsViewModel,
    stats: com.example.ui.viewmodel.EconomicsStats
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SlateMedium),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, GlowGreen.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
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
                            .background(GlowGreen.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = null,
                            tint = GlowGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "معادلة أرباح أدموب ومشروع فيزا",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = GlowGreen.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "+${stats.profitMarginPercent}% هامش ربح المطور",
                        color = GlowGreen,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "نظام تحويل النقاط مصمم بحيث تكون تكلفة نقاط المستخدم أقل بكثير من مكاسب إعلانات أدموب (eCPM $28)، مما يضمن للمطور أعلى مكسب شهري وهامش ربح فائق يتجاوز 90% وموثوقية سحب كاملة للفيزا بدون أي عجز.",
                color = Color.LightGray,
                fontSize = 11.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Stats 3-column breakdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("عائد أدموب المتوقع", color = Color.Gray, fontSize = 10.sp)
                    Text("$${String.format(Locale.US, "%.2f", stats.estimatedAdMobRevenueUsd)}", color = GlowGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("تكلفة نقاط السحب", color = Color.Gray, fontSize = 10.sp)
                    Text("$${String.format(Locale.US, "%.2f", stats.userCashValueUsd)}", color = AccentGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("صافي ربح المطور", color = Color.Gray, fontSize = 10.sp)
                    Text("+$${String.format(Locale.US, "%.2f", stats.developerNetProfitUsd)}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "حساب أدموب: ${viewModel.adsensePublisherId}",
                    color = Color.Gray,
                    fontSize = 10.sp
                )
                Text(
                    text = "الحالة: متصل ومفعل ✓",
                    color = GlowGreen,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
