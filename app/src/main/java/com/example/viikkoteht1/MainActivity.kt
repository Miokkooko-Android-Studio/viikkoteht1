package com.example.viikkoteht1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val viewModel: TaskViewModel = viewModel()
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
                                onTaskClick = {id ->
                                    viewModel.toggleDone(id)
                                },
                                onDelete = {id ->
                                    viewModel.removeTask(id)
                                },
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

