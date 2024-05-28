package com.pkrob.ApiDocLoL.ui.clicker

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pkrob.ApiDocLoL.model.User
import com.pkrob.ApiDocLoL.viewmodel.ClickerViewModel
import com.pkrob.ApiDocLoL.viewmodel.ClickerViewModelFactory
import com.pkrob.ApiDocLoL.viewmodel.UserViewModel
import com.pkrob.ApiDocLoL.util.SharedPreferencesManager

@Composable
fun ClickerScreen(navController: NavController, viewModel: ClickerViewModel = viewModel(factory = ClickerViewModelFactory(navController.context.applicationContext as Application))) {
    // Remplacez clickCount par PO
    var po by remember { mutableStateOf(SharedPreferencesManager.getPO(navController.context)) }

    val sharedPreferences = navController.context.getSharedPreferences("clicker_prefs", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("token", null)
    val userId = SharedPreferencesManager.getUserId(navController.context)
    val username = sharedPreferences.getString("username", "Utilisateur")

    val userViewModel: UserViewModel = viewModel()
    val userData by userViewModel.userData.collectAsState()

    LaunchedEffect(token) {
        Log.d("ClickerScreen", "LaunchedEffect triggered with token: $token")
        if (token == null) {
            Log.d("ClickerScreen", "Token is null, navigating to login")
            navController.navigate("login") {
                popUpTo("mainMenu") { inclusive = false }
            }
        } else if (userId != null) {
            Log.d("ClickerScreen", "Token is valid and userId is available, fetching user data for userId: $userId")
            userViewModel.fetchUserData(userId)
        } else {
            Log.d("ClickerScreen", "Token is valid but userId is null")
        }
    }

    LaunchedEffect(userData) {
        userData?.let {
            po = it.PO
            SharedPreferencesManager.savePO(navController.context, po)
        }
    }

    if (token != null && userData != null) {
        Log.d("ClickerScreen", "User data is available, displaying ClickerScreen")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Bonjour, $username !")
            Text(text = "PO: $po") // Afficher les PO
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    po += 1
                    SharedPreferencesManager.savePO(navController.context, po)
                } // Incrémenter les PO au lieu des clicks
            ) {
                Text(text = "Click Me!")
            }
            Spacer(modifier = Modifier.height(32.dp))
            Menu(navController, userViewModel, userData!!.copy(PO = po, upgrades = userData!!.upgrades ?: emptyList(), artifacts = userData!!.artifacts ?: emptyList()))
        }
    } else if (token != null) {
        Log.d("ClickerScreen", "Token is valid, but user data is not available yet, showing loading")
        // Afficher un écran de chargement en attendant que les données utilisateur soient récupérées
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun Menu(navController: NavController, userViewModel: UserViewModel, user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { navController.navigate("upgrade") }
        ) {
            Text(text = "Upgrade")
        }
        Button(
            onClick = { navController.navigate("shop") }
        ) {
            Text(text = "Boutique")
        }
        Button(
            onClick = {
                userViewModel.saveUserData(user.copy(
                    PO = SharedPreferencesManager.getPO(navController.context),
                    upgrades = user.upgrades ?: emptyList(),
                    artifacts = user.artifacts ?: emptyList()
                )) // Sauvegarder les PO actuels
                val sharedPreferences = navController.context.getSharedPreferences("clicker_prefs", Context.MODE_PRIVATE)
                sharedPreferences.edit().clear().apply()
                Log.d("Menu", "User data saved, user logged out, navigating to mainMenu")
                navController.navigate("mainMenu") {
                    popUpTo("mainMenu") { inclusive = true }
                }
            }
        ) {
            Text(text = "Déconnexion")
        }
    }
}
