package com.example.viikkoteht1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viikkoteht1.navigation.ROUTE_CALENDAR
import com.example.viikkoteht1.navigation.ROUTE_HOME
import com.example.viikkoteht1.navigation.ROUTE_SETTINGS
import com.example.viikkoteht1.ui.theme.Viikkoteht1Theme
import com.example.viikkoteht1.view.CalendarScreen
import com.example.viikkoteht1.view.HomeScreen
import com.example.viikkoteht1.view.SettingsScreen
import com.example.viikkoteht1.viewmodel.TaskViewModel
import kotlin.getValue
import com.example.viikkoteht1.data.AppDatabase
import com.example.viikkoteht1.repository.TaskRepository

class MainActivity : ComponentActivity() {

    // Luo tietokanta lazy-patternilla (vasta kun sitä tarvitaan)
    private val database by lazy {
        AppDatabase.getDatabase(applicationContext)
    }

    // Luo Repository, joka käyttää DAO:a tietokantaoperaatioihin
    private val repository by lazy {
        TaskRepository(database.taskDao())
    }

    // Luo ViewModel ViewModelProvider.Factory:n avulla
    // Factory tarvitaan koska ViewModel ottaa parametrin (repository)
    private val viewModel: TaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            val navController = rememberNavController()

            var isDarkTheme by rememberSaveable{mutableStateOf(true)}

            Viikkoteht1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController=navController,
                        startDestination = ROUTE_HOME
                    ) {
                        composable(ROUTE_HOME) {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                viewModel=viewModel,

                                onNavigateCalendar = {
                                    navController.navigate(ROUTE_CALENDAR)
                                },
                                onNavigateSettings = {
                                    navController.navigate(ROUTE_SETTINGS)
                                }

                            )
                        }
                        composable(ROUTE_CALENDAR){
                            CalendarScreen(
                                viewModel= viewModel,
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(ROUTE_SETTINGS){
                            SettingsScreen(
                                onNavigateBack = {navController.popBackStack()}
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun TextField() {
    TODO("Not yet implemented")
}

