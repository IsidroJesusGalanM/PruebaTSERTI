package com.example.pruebatecnicaserti.ui.extensions

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun View.applyInsetsWithOriginalPadding() {
    val originalPadding = Rect(paddingLeft, paddingTop, paddingRight, paddingBottom)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(
            systemBars.left + originalPadding.left,
            systemBars.top + originalPadding.top,
            systemBars.right + originalPadding.right,
            systemBars.bottom + originalPadding.bottom
        )
        insets
    }
}