package com.example.notesapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.notesapp.core.BottomNavigationManager
import com.example.notesapp.core.BottomNavigationManager.topLevelRoutes
import com.example.notesapp.core.presentation.extension.matchesAnyRoute
import com.example.notesapp.feature.login.presentation.navigation.loginGraph
import com.example.notesapp.feature.note.presentation.navigation.noteGraph
import com.example.notesapp.feature.profile.presentation.navigation.profileGraph
import com.example.notesapp.feature.register.presentation.navigation.registerGraph
import com.example.notesapp.feature.splash.presentation.navigation.Splash
import com.example.notesapp.feature.splash.presentation.navigation.splashGraph

@Composable
fun AppNavigation(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val toShowBottomBar =
        currentDestination.matchesAnyRoute(BottomNavigationManager.toShowBottomNavList)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        bottomBar = {
            if (toShowBottomBar) {
                NavigationBar {
                    topLevelRoutes.forEach { topLevelRoute ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = topLevelRoute.icon,
                                    contentDescription = topLevelRoute.name
                                )
                            },
                            label = { Text(topLevelRoute.name) },
                            selected = currentDestination?.hierarchy?.any {
                                it.hasRoute(topLevelRoute.route::class)
                            } == true,
                            onClick = {
                                navController.navigate(topLevelRoute.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Splash,
            modifier = Modifier.padding(innerPadding)
        ) {
            splashGraph(navController)

            loginGraph(navController, snackBarHostState)
            registerGraph(navController = navController, snackBarHostState = snackBarHostState)

            noteGraph(navController,snackBarHostState)

            profileGraph(snackBarHostState = snackBarHostState, navController)
        }
    }
}
