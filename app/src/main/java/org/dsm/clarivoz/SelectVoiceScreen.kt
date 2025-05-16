package org.dsm.clarivoz

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.dsm.clarivoz.ui.theme.ClariVozTheme
import android.R

data class VoiceItem(
    val id: Int,
    val name: String,
    val category: String,
    val distance: String,
    val rating: Int,
    var isSelected: Boolean = false,
    val icon: Int // Resource ID for the icon
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectVoiceScreen(onBackClick: () -> Unit) {
    val voiceItems = remember {
        mutableStateListOf(
            VoiceItem(1, "Voz 1", "Category $$", "1.2 miles away", 4, icon = R.drawable.ic_media_play), // Replace with your actual icon
            VoiceItem(2, "Voz 2", "Category $$", "1.2 miles away", 5, icon = R.drawable.ic_media_play), // Replace with your actual icon
            VoiceItem(3, "Voz 3", "Category $$", "1.2 miles away", 3, icon = R.drawable.ic_media_play)  // Replace with your actual icon
        )
    }

    var selectedVoice by remember { mutableStateOf<VoiceItem?>(null) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Section - Selected Voice (Placeholder)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text(text = selectedVoice?.name ?: "Voz 1", fontSize = 24.sp)
                    Text(text = "Voz elegida", color = Color.Gray)
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            // List Title
            Text(text = "Seleccione voz a escuchar", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(8.dp))

            // Voice List
            LazyColumn {
                items(voiceItems) { voice ->
                    VoiceItemRow(voice = voice, onVoiceSelected = {
                        voiceItems.forEach { it.isSelected = it.id == voice.id }
                        selectedVoice = voice
                    })
                    Divider()
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Select Button
            Button(
                onClick = { /* Handle selection */ },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = selectedVoice != null // Disable if no voice is selected
            ) {
                Text("Seleccionar")
            }
        }
    }
}

@Composable
fun VoiceItemRow(voice: VoiceItem, onVoiceSelected: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onVoiceSelected() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = voice.icon), // Use voice.icon
            contentDescription = "Voice Icon",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(text = voice.name)
            Text(text = "${voice.category} - ${voice.distance}", color = Color.Gray, fontSize = 12.sp)
            Text(text = "Supporting line text lorem ipsum...", color = Color.Gray, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row {
            repeat(5) {
                Icon(
                    painter = painterResource(id = if (it < voice.rating) R.drawable.ic_media_play else R.drawable.ic_media_play), // Replace with your star icons
                    contentDescription = "Star",
                    tint = if (it < voice.rating) Color.Yellow else Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        if (voice.isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.ic_media_play), // Replace with your checkmark icon
                contentDescription = "Selected",
                tint = Color.Green
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectVoiceScreenPreview() {
    ClariVozTheme {
        SelectVoiceScreen(onBackClick = {})
    }
}