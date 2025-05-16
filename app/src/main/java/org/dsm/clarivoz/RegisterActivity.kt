package org.dsm.clarivoz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.dsm.clarivoz.connection.FirebaseConnection
import org.dsm.clarivoz.ui.theme.ClariVozTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClariVozTheme {
                RegisterScreen(onBackClick = { navigateToMain() })
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun RegisterScreen(onBackClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val context = LocalContext.current

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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Sign Up", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                        // Guardamos los datos en Firestore y luego actualizamos el mensaje
                        saveUserToFirestore(email, password) { isSuccess ->
                            if (isSuccess) {
                                message = "Usuario registrado exitosamente"
                            } else {
                                message = "Error al guardar el usuario"
                            }
                            showDialog = true
                        }
                    } else {
                        message = "Please fill in all fields and ensure passwords match"
                        showDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }

            // Mostrar el AlertDialog con el mensaje
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Registro Resultado") },
                    text = { Text(message) },
                    confirmButton = {
                        TextButton(onClick = {
                            // Después de hacer clic en "OK", ir a MainActivity
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                            // Finalizamos la actividad de registro para que no se pueda regresar a ella
                            (context as? Activity)?.finish()
                        }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}

// Función para guardar el usuario en Firestore
fun saveUserToFirestore(email: String, password: String, callback: (Boolean) -> Unit) {
    val db = FirebaseConnection.ConnectionFirestore()

    // Crear un mapa con los datos del usuario
    val userMap = hashMapOf(
        "email" to email,
        "password" to password
    )

    // Usar Firestore para guardar los datos
    db.collection("users") // Aquí creamos la colección 'users'
        .add(userMap)  // Agregar los datos del usuario
        .addOnSuccessListener {
            // Si la operación fue exitosa, llamamos al callback
            callback(true) // Usuario guardado con éxito
        }
        .addOnFailureListener { e ->
            // Si ocurre un error al guardar, llamamos al callback con false
            e.printStackTrace() // Imprime el error para depuración
            callback(false) // Error al guardar
        }
}



@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RegisterScreenPreview() {
    ClariVozTheme {
        RegisterScreen(onBackClick = {})
    }
}