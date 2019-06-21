package com.github.farzadfarazmand.emptystate

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.res.TypedArray


class EmptyState : ConstraintLayout {

    private var iconSize = IconSize.NORMAL
    private var icon = 0
    private var text = ""
    private var textSize = R.dimen.emps_default_text_size
    private var textColor = R.color.emps_default_text_color
    private var buttonText = ""
    private var buttonTextColor = android.R.color.white
    private var buttonTextSize = R.dimen.emps_default_button_text_size
    private var buttonBackgroundColor = R.color.emps_default_button_color
    private var buttonCornerSize = R.dimen.emps_default_button_corner_size

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let { handleAttribute(it) }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let { handleAttribute(it) }
    }

    init {
        View.inflate(context, R.layout.empty_state, this)
    }

    private fun handleAttribute(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyState)
        typedArray.getString(R.styleable.EmptyState_emps_text)?.let { text = it }

        typedArray.recycle()
    }
}