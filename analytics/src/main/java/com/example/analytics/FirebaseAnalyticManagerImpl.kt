package com.example.analytics

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.os.bundleOf
import com.example.domain.model.Bet
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseAnalyticManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AnalyticsManager {

    @SuppressLint("MissingPermission")
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun logMatchDetailOpened(eventId: String) {
        firebaseAnalytics.logEvent("match_detail_viewed", bundleOf(
            "event_id" to eventId
        ))
    }

    override fun logBetAddedToCart(bet: Bet) {
        firebaseAnalytics.logEvent("bet_added_to_cart", bundleOf(
            "event_id" to bet.eventId,
        ))
    }

    override fun logBetRemovedFromCart(bet: Bet) {
        firebaseAnalytics.logEvent("bet_removed_from_cart", bundleOf(
            "event_id" to bet.eventId,
        ))
    }
}