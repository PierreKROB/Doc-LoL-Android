package com.pkrob.ApiDocLoL

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pkrob.ApiDocLoL.network.RetrofitInstance
import com.pkrob.ApiDocLoL.ui.*
import com.pkrob.ApiDocLoL.ui.clicker.ClickerScreen
import com.pkrob.ApiDocLoL.ui.theme.ApiDocLoLTheme
import com.pkrob.ApiDocLoL.viewmodel.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = RetrofitInstance.api

        val mainViewModel: MainViewModel by viewModels {
            MainViewModelFactory(apiService)
        }

        setContent {
            ApiDocLoLTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController, startDestination = "mainMenu") {
                        addMainNavigation(navController, mainViewModel)
                    }
                }
            }
        }
    }

    private fun NavGraphBuilder.addMainNavigation(navController: androidx.navigation.NavController, mainViewModel: MainViewModel) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("mainMenu") {
            MainMenu(navController, mainViewModel)
        }
        composable("championList") {
            val selectedVersion by mainViewModel.selectedVersion.collectAsState()
            selectedVersion?.let { version ->
                val championViewModel: ChampionViewModel = viewModel(factory = ChampionViewModelFactory(version))
                val champions by championViewModel.champions.collectAsState()
                ChampionList(champions = champions, version = version) { champion ->
                    navController.navigate("championDetail/${champion.id}") {
                        popUpTo("championList") { inclusive = true }
                    }
                }
            }
        }
        composable("championDetail/{championId}") { backStackEntry ->
            val championId = backStackEntry.arguments?.getString("championId")
            val selectedVersion by mainViewModel.selectedVersion.collectAsState()
            selectedVersion?.let { version ->
                val championViewModel: ChampionViewModel = viewModel(factory = ChampionViewModelFactory(version))
                championId?.let {
                    ChampionDetailScreen(viewModel = championViewModel, version = version, championId = it)
                }
            }
        }
        composable("itemList") {
            val selectedVersion by mainViewModel.selectedVersion.collectAsState()
            selectedVersion?.let { version ->
                val itemViewModel: ItemViewModel = viewModel(factory = ItemViewModelFactory(version))
                val items by itemViewModel.items.collectAsState()
                ItemList(items = items, version = version)
            }
        }
        composable("clicker") {
            val clickerViewModel: ClickerViewModel = viewModel(factory = ClickerViewModelFactory(application))
            ClickerScreen(navController = navController, viewModel = clickerViewModel)
        }
        composable("upgrade") {
            // Placeholder for Upgrade Screen
            Text("Upgrade Screen")
        }
        composable("shop") {
            // Placeholder for Shop Screen
            Text("Shop Screen")
        }
    }
}
