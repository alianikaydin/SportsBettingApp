package com.example.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.Event

@Composable
fun BetCartScreen(
    onBack: () -> Unit,
) {
    val viewModel: BetCartViewModel = hiltViewModel()
    val cartState by viewModel.cartState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Your Bets", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(cartState.cartItems) { event ->
                CartItem(event = event, onRemove = { viewModel.removeFromCart(event) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Total: ${cartState.totalOdds}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Events")
        }
    }
}

@Composable
fun CartItem(event: Event, onRemove: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("${event.homeTeam} vs ${event.awayTeam}")
            }
            Button(onClick = onRemove) {
                Text("Remove")
            }
        }
    }
}