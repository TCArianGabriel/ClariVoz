package org.dsm.clarivoz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.dsm.clarivoz.ui.theme.ClariVozTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClariVozTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        onNavigateToLogin = { navigateToLogin() },
                        onNavigateToRegister = { navigateToRegister() },
                        onNavigateToSelectVoice = { navigateToSelectVoice() },
                        onNavigateToProcessAudio = { navigateToProcessAudio() }
                    )
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSelectVoice() {
        val intent = Intent(this, SelectVoiceActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToProcessAudio(){
        val intent = Intent(this, ProcessAudioActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToSelectVoice: () -> Unit,
    onNavigateToProcessAudio:()->Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End // Align buttons to the right
        ) {
            Button(
                onClick = { onNavigateToLogin() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("Log In", color = Color.White)
            }
            Button(
                onClick = { onNavigateToRegister() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            ) {
                Text("Sign Up", color = Color.White)
            }
        }
        // Imagen
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Reemplaza por tu recurso
            contentDescription = "Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(top = 16.dp),
            contentScale = ContentScale.Crop
        )

        // Título
        Text(
            text = "ClariVoz",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Botón seleccionar voz
        Button(
            onClick = {  onNavigateToSelectVoice()  },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBB86FC)),
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text("Seleccionar voz", color = Color.White)
        }

        // Información de la app
        Text(
            text = "Información de la app",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)
        )

        Text(
            text = """
                Bienvenido a ClariVoz una App que busca ayudar a las personas con dificultad al momento de hablar
                a entablar comunicacion mas elocuente con las demas personas.
            """.trimIndent(),
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Área de micrófono
        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .size(250.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFFECE7EF)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Reemplaza por tu recurso
                contentDescription = "Mic Icon",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { /* Acción de grabar */ },
                alpha = 0.8f
            )
        }

        // Botón procesar audio
        Button(
            onClick = {onNavigateToProcessAudio()},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBB86FC)),
            modifier = Modifier
                .padding(top = 24.dp)
        ) {
            Text("Procesar audio", color = Color.White)
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ClariVozTheme {
        Greeting(
            "Android",
            onNavigateToLogin = {},
            onNavigateToRegister = {},
            onNavigateToSelectVoice = {},
            onNavigateToProcessAudio = {}
        )
    }
}