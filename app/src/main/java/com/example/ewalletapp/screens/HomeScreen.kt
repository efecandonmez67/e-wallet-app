package com.example.ewalletapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ewalletapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    // Bottom Sheet (Alttan açılan menü) durumunu kontrol eden değişken
    var showTransferSheet by remember { mutableStateOf(false) }

    // Titreşim (Haptic Feedback) motorunu çağırıyoruz
    val haptic = LocalHapticFeedback.current

    Scaffold(
        bottomBar = { FintekBottomNavigationBar() },
        containerColor = BackgroundGray
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Merhaba, Efe Can 👋",
                fontSize = 18.sp,
                color = TextSecondary,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            BalanceCard()

            Spacer(modifier = Modifier.height(24.dp))

            // Hızlı İşlemler
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(
                    icon = Icons.Rounded.SwapHoriz,
                    label = "Para Transferi",
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress) // Tıklama titreşimi
                        showTransferSheet = true // Menüyü aç
                    }
                )
                ActionButton(
                    icon = Icons.Rounded.Receipt,
                    label = "Faturalar",
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        // Fatura sayfasına yönlendirme eklenebilir
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Son Hareketler", fontSize = 20.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            val mockTransactions = listOf(
                TransactionMock("Ahmet Yılmaz", "Para Gönderildi", "-₺150.00", false),
                TransactionMock("Netflix", "Abonelik", "-₺119.99", false),
                TransactionMock("Maaş Ödemesi", "Gelen Transfer", "+₺45,000.00", true),
                TransactionMock("Market Alışverişi", "Kart İşlemi", "-₺450.50", false)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mockTransactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }

        // Eğer butona basıldıysa Alttan Açılan Menüyü (Bottom Sheet) Göster
        if (showTransferSheet) {
            ModalBottomSheet(
                onDismissRequest = { showTransferSheet = false },
                containerColor = Color.White
            ) {
                TransferBottomSheetContent(
                    onOptionSelected = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        showTransferSheet = false
                    }
                )
            }
        }
    }
}

// --- YENİ: Alttan Açılan Menü İçeriği ---
@Composable
fun TransferBottomSheetContent(onOptionSelected: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .padding(bottom = 32.dp)
    ) {
        Text("Para Transferi", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(24.dp))

        BottomSheetOption(icon = Icons.Rounded.Person, title = "Kayıtlı Alıcıya Gönder", onClick = onOptionSelected)
        Spacer(modifier = Modifier.height(16.dp))
        BottomSheetOption(icon = Icons.Rounded.AccountBalance, title = "IBAN'a Gönder", onClick = onOptionSelected)
        Spacer(modifier = Modifier.height(16.dp))
        BottomSheetOption(icon = Icons.Rounded.ArrowCircleDown, title = "Para İste", onClick = onOptionSelected)
    }
}

@Composable
fun BottomSheetOption(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .background(BackgroundGray)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = title, tint = PrimaryBlue)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
    }
}

// (Önceki kodda yer alan BalanceCard, ActionButton, TransactionItem ve FintekBottomNavigationBar fonksiyonları olduğu gibi kalacak. Sadece ActionButton'a onClick parametresi ekledik, aşağıda güncelini veriyorum)

@Composable
fun ActionButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { onClick() }, // Tıklanabilir yaptık
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = PrimaryBlue, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun BalanceCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryBlue),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("Toplam Bakiye", color = Color.White.copy(alpha = 0.8f), fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("₺44,279.51", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Hesap No: 1045 8792 34", color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp)
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionMock) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(48.dp).clip(CircleShape)
                        .background(if (transaction.isIncoming) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (transaction.isIncoming) Icons.Rounded.ArrowDownward else Icons.Rounded.ArrowUpward,
                        contentDescription = null,
                        tint = if (transaction.isIncoming) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = transaction.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = transaction.subtitle, fontSize = 14.sp, color = TextSecondary)
                }
            }
            Text(
                text = transaction.amount, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                color = if (transaction.isIncoming) Color(0xFF4CAF50) else TextPrimary
            )
        }
    }
}

@Composable
fun FintekBottomNavigationBar() {
    NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
        NavigationBarItem(
            selected = true, onClick = { },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Ana Sayfa") },
            label = { Text("Ana Sayfa") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryDark, indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = false, onClick = { },
            icon = { Icon(Icons.Filled.Send, contentDescription = "Transfer") },
            label = { Text("Transfer") }
        )
        NavigationBarItem(
            selected = false, onClick = { },
            icon = { Icon(Icons.Filled.QrCodeScanner, contentDescription = "QR") },
            label = { Text("QR İşlem") }
        )
        NavigationBarItem(
            selected = false, onClick = { },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profil") },
            label = { Text("Profil") }
        )
    }
}

data class TransactionMock(val title: String, val subtitle: String, val amount: String, val isIncoming: Boolean)