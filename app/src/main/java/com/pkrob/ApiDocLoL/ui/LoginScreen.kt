package com.pkrob.ApiDocLoL.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pkrob.ApiDocLoL.model.LoginRequest
import com.pkrob.ApiDocLoL.network.RetrofitInstance
import com.pkrob.ApiDocLoL.util.JwtUtils
import com.pkrob.ApiDocLoL.util.SharedPreferencesManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nom d'utilisateur") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        errorMessage = null
                        val response = try {
                            RetrofitInstance.api.login(LoginRequest(username, password))
                        } catch (e: Exception) {
                            errorMessage = "Erreur de connexion: ${e.message}"
                            isLoading = false
                            return@launch
                        }

                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody?.token != null) {
                                val sharedPreferences = navController.context.getSharedPreferences("clicker_prefs", Context.MODE_PRIVATE)
                                val userId = JwtUtils.getUserIdFromToken(responseBody.token)
                                SharedPreferencesManager.saveUserId(navController.context, userId ?: "")
                                sharedPreferences.edit()
                                    .putString("token", responseBody.token)
                                    .putString("username", username)
                                    .apply()
                                navController.navigate("clicker") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                errorMessage = "Identifiants incorrects"
                            }
                        } else {
                            errorMessage = "Identifiants incorrects"
                        }
                        isLoading = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Se connecter")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { navController.navigate("register") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("S'inscrire")
            }
        }
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = Color.Red, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }
    }
}
