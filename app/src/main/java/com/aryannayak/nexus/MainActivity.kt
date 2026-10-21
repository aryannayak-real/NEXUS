package com.aryannayak.nexus

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    private val blue = Color.rgb(37, 99, 235)
    private val ink = Color.rgb(24, 32, 52)
    private val muted = Color.rgb(110, 120, 140)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.WHITE
        window.navigationBarColor = Color.WHITE
        window.decorView.systemUiVisibility =
            android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
            android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
            setPadding(dp(24), dp(28), dp(24), dp(24))
        }

        val brand = TextView(this).apply {
            text = getString(R.string.app_name)
            textSize = 28f
            setTextColor(blue)
            letterSpacing = 0.12f
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val greeting = TextView(this).apply {
            text = getString(R.string.home_greeting)
            textSize = 24f
            setTextColor(ink)
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(0, dp(32), 0, dp(8))
        }

        val subtitle = TextView(this).apply {
            text = getString(R.string.home_subtitle)
            textSize = 14f
            setTextColor(muted)
        }

        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(20), dp(22), dp(20), dp(22))
            setBackgroundColor(Color.rgb(243, 247, 255))
        }

        val cardTitle = TextView(this).apply {
            text = getString(R.string.welcome_title)
            textSize = 12f
            letterSpacing = 0.08f
            setTextColor(blue)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val cardText = TextView(this).apply {
            text = getString(R.string.welcome_message)
            textSize = 18f
            setTextColor(ink)
            setPadding(0, dp(10), 0, 0)
        }

        card.addView(cardTitle)
        card.addView(cardText)

        root.addView(brand)
        root.addView(greeting)
        root.addView(subtitle)

        root.addView(card, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            dp(140)
        ).apply {
            topMargin = dp(32)
        })

        setContentView(root)
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
