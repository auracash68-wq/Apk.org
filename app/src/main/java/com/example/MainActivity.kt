package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme

enum class VibeTheme(
  val id: String,
  val displayName: String,
  val icon: ImageVector,
  val startColor: Color,
  val endColor: Color,
  val accentColor: Color,
  val textColor: Color
) {
  COSMIC(
    id = "cosmic",
    displayName = "Cosmic Space",
    icon = Icons.Default.Star,
    startColor = Color(0xFF1E1B4B), // deep indigo
    endColor = Color(0xFF0F0E30),
    accentColor = Color(0xFF818CF8),
    textColor = Color(0xFFF3F4F6)
  ),
  SUNSET(
    id = "sunset",
    displayName = "Sunset Rose",
    icon = Icons.Default.Favorite,
    startColor = Color(0xFF4C1D95), // deep plum
    endColor = Color(0xFF2D124D),
    accentColor = Color(0xFFF472B6),
    textColor = Color(0xFFFDF2F8)
  ),
  FOREST(
    id = "forest",
    displayName = "Forest Jade",
    icon = Icons.Default.Face,
    startColor = Color(0xFF064E3B), // deep emerald
    endColor = Color(0xFF022C22),
    accentColor = Color(0xFF34D399),
    textColor = Color(0xFFECFDF5)
  )
}

enum class LanguageVibe(
  val id: String,
  val originalName: String,
  val translation: String,
  val flag: String
) {
  ENGLISH("en", "English", "Hello", "🇺🇸"),
  SPANISH("es", "Español", "¡Hola", "🇪🇸"),
  FRENCH("fr", "Français", "Bonjour", "🇫🇷"),
  JAPANESE("ja", "日本語", "こんにちは", "🇯🇵"),
  ITALIAN("it", "Italiano", "Ciao", "🇮🇹")
}

val RANDOM_NAMES = listOf(
  "Star Traveler",
  "Stellar Coder",
  "Curious Mind",
  "Bold Pioneer",
  "Artistic Soul",
  "Space Voyager",
  "Brilliant Friend",
  "Aero Adventurer"
)

val WELCOME_QUOTES = listOf(
  "A journey of a thousand miles begins with a single step! ✨",
  "Every day is a fresh opportunity to build something beautiful. 💻",
  "You are capable of doing amazing things! 🚀",
  "Creativity is intelligence having fun! 🎨",
  "Simplicity is the ultimate sophistication. 🌟",
  "Keep coding, keep exploring, keep dreaming! 🛰️"
)

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        HelloAppScreen()
      }
    }
  }
}

@Composable
fun HelloAppScreen() {
  var name by remember { mutableStateOf("") }
  var currentVibe by remember { mutableStateOf(VibeTheme.COSMIC) }
  var currentLanguage by remember { mutableStateOf(LanguageVibe.ENGLISH) }
  var quoteIndex by remember { mutableStateOf(0) }
  val focusManager = LocalFocusManager.current
  val scrollState = rememberScrollState()

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFF0C0C0E)),
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          quoteIndex = (quoteIndex + 1) % WELCOME_QUOTES.size
        },
        containerColor = currentVibe.accentColor,
        contentColor = currentVibe.endColor,
        modifier = Modifier
          .testTag("quote_fab")
          .padding(bottom = 8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Refresh,
          contentDescription = "New Quote"
        )
      }
    },
    contentWindowInsets = WindowInsets.safeDrawing
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .verticalScroll(scrollState)
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
      // Header Section
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          Text(
            text = "Greeting Studio",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = currentVibe.accentColor,
            fontFamily = FontFamily.SansSerif
          )
          Text(
            text = "Hello Test",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            fontFamily = FontFamily.SansSerif
          )
        }
        
        // Dynamic design visual status indicator
        Box(
          modifier = Modifier
            .size(12.dp)
            .background(currentVibe.accentColor, shape = CircleShape)
        )
      }

      // Live Greeting Card with dynamic color gradient
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp)
          .clip(RoundedCornerShape(24.dp))
          .background(
            Brush.linearGradient(
              colors = listOf(currentVibe.startColor, currentVibe.endColor)
            )
          )
          .testTag("greeting_card")
          .clickable {
            focusManager.clearFocus()
          }
          .padding(24.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          val displayName = name.trim().ifEmpty { "World" }
          val punctuation = if (currentLanguage == LanguageVibe.SPANISH) "!" else "!"
          val greetingText = "${currentLanguage.translation}, $displayName$punctuation"

          AnimatedContent(
            targetState = greetingText,
            transitionSpec = {
              (fadeIn() + slideInVertically { it / 2 }) togetherWith
                (fadeOut() + slideOutVertically { -it / 2 })
            },
            label = "greeting_animation"
          ) { animatedText ->
            Text(
              text = animatedText,
              fontSize = 32.sp,
              fontWeight = FontWeight.Bold,
              color = currentVibe.textColor,
              textAlign = TextAlign.Center,
              fontFamily = FontFamily.SansSerif
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = "Vibe Layout: ${currentVibe.displayName}",
            fontSize = 12.sp,
            color = currentVibe.textColor.copy(alpha = 0.7f),
            fontWeight = FontWeight.Medium
          )
        }
      }

      // Interactive Form Input Card
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF16161A)),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          Text(
            text = "Customize Greeting",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )

          OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name to Greet") },
            placeholder = { Text("World") },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("name_input"),
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = currentVibe.accentColor
              )
            },
            trailingIcon = {
              if (name.isNotEmpty()) {
                IconButton(
                  onClick = { name = "" },
                  modifier = Modifier.testTag("clear_button")
                ) {
                  Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear input",
                    tint = Color.Gray
                  )
                }
              }
            },
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = currentVibe.accentColor,
              focusedLabelColor = currentVibe.accentColor,
              unfocusedBorderColor = Color(0xFF2C2C35),
              unfocusedLabelColor = Color.Gray,
              focusedTextColor = Color.White,
              unfocusedTextColor = Color.White
            )
          )

          Button(
            onClick = {
              name = RANDOM_NAMES.random()
            },
            colors = ButtonDefaults.buttonColors(containerColor = currentVibe.accentColor),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("random_button"),
            shape = RoundedCornerShape(12.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Refresh,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Or, Pick Random Name",
              fontWeight = FontWeight.Bold,
              color = currentVibe.endColor
            )
          }
        }
      }

      // Theme/Style Selectors
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF16161A)),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          Text(
            text = "Choose Vibe Theme",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            VibeTheme.values().forEach { vibe ->
              val isSelected = vibe == currentVibe
              Box(
                modifier = Modifier
                  .weight(1f)
                  .height(56.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(
                    if (isSelected) vibe.accentColor else Color(0xFF23232C)
                  )
                  .clickable {
                    currentVibe = vibe
                  }
                  .testTag("vibe_chip_${vibe.id}")
                  .padding(8.dp),
                contentAlignment = Alignment.Center
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Icon(
                    imageVector = vibe.icon,
                    contentDescription = null,
                    tint = if (isSelected) vibe.endColor else vibe.accentColor,
                    modifier = Modifier.size(16.dp)
                  )
                  Text(
                    text = vibe.displayName.substringAfter(" "),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) vibe.endColor else Color.White
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Choose Local Language",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            LanguageVibe.values().forEach { lang ->
              val isSelected = lang == currentLanguage
              Box(
                modifier = Modifier
                  .weight(1f)
                  .height(48.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .background(
                    if (isSelected) currentVibe.accentColor.copy(alpha = 0.2f) else Color(0xFF23232C)
                  )
                  .clickable {
                    currentLanguage = lang
                  }
                  .testTag("lang_chip_${lang.id}")
                  .padding(4.dp),
                contentAlignment = Alignment.Center
              ) {
                Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
                ) {
                  Text(
                    text = lang.flag,
                    fontSize = 14.sp
                  )
                  Text(
                    text = lang.originalName,
                    fontSize = 10.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) currentVibe.accentColor else Color.Gray
                  )
                }
              }
            }
          }
        }
      }

      // Quote panel at the bottom
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF16161A)),
        shape = RoundedCornerShape(16.dp)
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .background(currentVibe.accentColor.copy(alpha = 0.15f), shape = CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = "Quote Info icon",
              tint = currentVibe.accentColor,
              modifier = Modifier.size(20.dp)
            )
          }

          AnimatedContent(
            targetState = WELCOME_QUOTES[quoteIndex],
            transitionSpec = {
              fadeIn() togetherWith fadeOut()
            },
            label = "quote_transition"
          ) { quote ->
            Text(
              text = quote,
              fontSize = 13.sp,
              color = Color.LightGray,
              fontWeight = FontWeight.Normal,
              lineHeight = 18.sp
            )
          }
        }
      }
    }
  }
}

// Keep a clean compatibility function helper for any code referencing Greeting preview
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  MyApplicationTheme { Greeting("Android") }
}
