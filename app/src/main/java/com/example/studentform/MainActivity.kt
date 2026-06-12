package com.example.studentform

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentFormScreen()
        }
    }
}

val BackgroundColor = Color(0xFF0F0F1A)
val CardColor = Color(0xFF1A1A2E)
val AccentColor = Color(0xFF7C3AED)
val AccentLight = Color(0xFF9D5FF5)
val SurfaceColor = Color(0xFF16213E)
val TextPrimary = Color(0xFFE2E8F0)
val TextSecondary = Color(0xFF94A3B8)
val BorderColor = Color(0xFF2D2D5E)

@Composable
fun StudentFormScreen() {
    val context = LocalContext.current

    var nameState by remember { mutableStateOf("") }
    var surnameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }

    val directions = listOf("Android", "iOS", "Web", "Backend")

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            dateState = "%02d/%02d/%04d".format(day, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(AccentColor)
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "Student Form",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Fill in your details",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            FormCard {
                FormLabel("სახელი")
                StyledTextField(value = nameState, onValueChange = { nameState = it }, placeholder = "შეიყვანეთ სახელი")
                Spacer(modifier = Modifier.height(16.dp))
                FormLabel("გვარი")
                StyledTextField(value = surnameState, onValueChange = { surnameState = it }, placeholder = "შეიყვანეთ გვარი")
                Spacer(modifier = Modifier.height(16.dp))
                FormLabel("ელ-ფოსტა")
                StyledTextField(value = emailState, onValueChange = { emailState = it }, placeholder = "you@example.com")
            }

            Spacer(modifier = Modifier.height(16.dp))

            FormCard {
                FormLabel("თარიღი")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceColor)
                        .border(1.dp, BorderColor, RoundedCornerShape(10.dp))
                        .clickable { datePickerDialog.show() }
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (dateState.isEmpty()) "აირჩიეთ თარიღი" else dateState,
                            color = if (dateState.isEmpty()) TextSecondary else TextPrimary,
                            fontSize = 15.sp
                        )
                        Text(text = "📅", fontSize = 18.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            FormCard {
                FormLabel("ფავორიტი მიმართულება")
                Spacer(modifier = Modifier.height(8.dp))
                directions.forEach { direction ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { selectedOption = direction }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedOption == direction,
                            onClick = { selectedOption = direction },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = AccentLight,
                                unselectedColor = TextSecondary
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = direction,
                            color = if (selectedOption == direction) TextPrimary else TextSecondary,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            FormCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ვეთანხმები წესებს და პირობებს",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = isAgreed,
                        onCheckedChange = { isAgreed = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AccentColor,
                            uncheckedThumbColor = TextSecondary,
                            uncheckedTrackColor = BorderColor
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    if (nameState.isBlank() || surnameState.isBlank() || emailState.isBlank() || dateState.isBlank() || selectedOption.isEmpty() || !isAgreed) {
                        Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentColor)
            ) {
                Text(
                    text = "Submit",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun FormCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardColor)
            .border(1.dp, BorderColor, RoundedCornerShape(14.dp))
            .padding(18.dp),
        content = content
    )
}

@Composable
fun FormLabel(text: String) {
    Text(
        text = text,
        color = AccentLight,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
fun StyledTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceColor)
            .border(1.dp, BorderColor, RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        if (value.isEmpty()) {
            Text(text = placeholder, color = TextSecondary, fontSize = 15.sp)
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(color = TextPrimary, fontSize = 15.sp),
            cursorBrush = SolidColor(AccentLight),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}