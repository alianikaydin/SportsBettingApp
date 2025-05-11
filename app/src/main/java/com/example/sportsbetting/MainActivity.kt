package com.example.sportsbetting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.betting.betbulletin.BetBulletinScreen
import com.example.betting.betdetail.BetDetailScreen
import com.example.betting.betdetail.BetDetailViewModel
import com.example.cart.BetCartScreen
import com.example.core.ui.components.BetCartFab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportsBettingApp()
        }
    }
}

@Composable
fun SportsBettingApp() {
    Surface(color = MaterialTheme.colorScheme.background) {
        MainScreen()
    }
}

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    Box(modifier = Modifier.fillMaxSize()) {
        AppNavigation(navController = navController)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            BetCartFab(onClick = { navController.navigate("bet_cart") })
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    val betDetailViewModel: BetDetailViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "bet_bulletin"
    ) {
        composable("bet_bulletin") {
            BetBulletinScreen(
                onNavigateToCart = {
                    navController.navigate("bet_cart")
                },
                onEventClick = { event ->
                    betDetailViewModel.setEvent(event)
                    navController.navigate("bet_detail")
                }
            )
        }

        composable("bet_detail") {
            BetDetailScreen(
                viewModel = betDetailViewModel,
                onBack = { navController.popBackStack() },
                onBetSelected = { bet ->

                }
            )
        }

        composable("bet_cart") {
            BetCartScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}