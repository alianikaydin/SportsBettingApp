package com.example.core.ui.extensions

import com.example.domain.model.Bet

//this function will calculate the total odd by simply multiplying all the odds together
fun List<Bet>.calculateTotalOdds(): Double {
    return this.fold(1.0) { acc, bet -> acc * bet.odds }
}