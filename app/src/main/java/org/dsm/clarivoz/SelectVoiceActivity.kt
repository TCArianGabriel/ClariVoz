package org.dsm.clarivoz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.dsm.clarivoz.ui.theme.ClariVozTheme

class SelectVoiceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClariVozTheme {
                SelectVoiceScreen(onBackClick = { navigateToMain() })
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Opcional: cierra esta actividad
    }
}