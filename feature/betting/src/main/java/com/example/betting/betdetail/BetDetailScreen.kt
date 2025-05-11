package com.example.betting.betdetail

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.ui.extensions.toReadableDateTime
import com.example.domain.model.Bet
import com.example.network.NetworkResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetDetailScreen(
    viewModel: BetDetailViewModel,
    onBack: () -> Unit = {},
    onBetSelected: (Bet) -> Unit
) {
    val uiState = viewModel.uiState.value
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Match Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when (uiState) {
            is NetworkResult.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is NetworkResult.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${uiState.message}")
                }
            }

            is NetworkResult.Success -> {
                val event = uiState.data
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .verticalScroll(scrollState)
                ) {

                    Text(
                        text = "${event.homeTeam} vs ${event.awayTeam}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Start time: ${event.commenceTime.toReadableDateTime()}")
                    Spacer(modifier = Modifier.height(16.dp))

                    event.bookmakers.forEach { bookmaker ->
                        Text(
                            text = "Bookmaker: ${bookmaker.title}",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        bookmaker.markets.forEach { market ->
                            Text(
                                text = "Market: ${market.key}",
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            market.outcomes.forEach { outcome ->

                                val bet = Bet(
                                    eventId = event.id,
                                    eventTitle = "${event.homeTeam} vs ${event.awayTeam}",
                                    outcomeName = outcome.name,
                                    odds = outcome.price,
                                    bookmaker = bookmaker.key,
                                    market = market.key,
                                    commenceTime = event.commenceTime
                                )

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    Box(modifier = Modifier.fillMaxWidth()) {

                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Text(
                                                "Pick: ${bet.outcomeName}",
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                            Text(
                                                "Odds: ${bet.odds}",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }

                                        IconButton(
                                            onClick = {
                                                viewModel.addBetToCart(bet)
                                                onBetSelected(bet)
                                                Toast.makeText(
                                                    context,
                                                    "Bet added to cart",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            },
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Add to cart"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}