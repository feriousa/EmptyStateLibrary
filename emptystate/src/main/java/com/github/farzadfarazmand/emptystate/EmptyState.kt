package com.github.farzadfarazmand.emptystate

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.annotation.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.empty_state.view.*

/**
 * Show different state of views, like noInternetConnection, noListData, notLogin, permissionNotGranted and ....
 * @author Farzad Farazmand
 * https://github.com/farzadfarazmand/EmptyStateLibrary
 */
class EmptyState : ConstraintLayout {

    private var iconSize = IconSize.NORMAL
    private var icon = 0
    private var text = ""
    private var textSize = resources.getDimensionPixelSize(R.dimen.emps_default_text_size)
    private var textColor = ContextCompat.getColor(context, R.color.emps_default_text_color)
    private var showButton = false
    private var buttonText = ""
    private var buttonTextColor = Color.WHITE
    private var buttonTextSize = resources.getDimensionPixelSize(R.dimen.emps_default_button_text_size)
    private var buttonBackgroundColor = ContextCompat.getColor(context, R.color.emps_default_button_color)
    private var buttonCornerSize = resources.getDimensionPixelSize(R.dimen.emps_default_button_corner_size)
    private var buttonClickListener: OnClickListener? = null

    constructor(context: Context?) : super(context) {
        initialView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let { handleAttribute(it) }
        initialView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let { handleAttribute(it) }
        initialView()
    }

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        View.inflate(context, R.layout.empty_state, this)
    }

    private fun handleAttribute(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyState)

        //icon
        icon = typedArray.getResourceId(R.styleable.EmptyState_emps_icon, 0)
        iconSize = IconSize.values()[typedArray.getInt(R.styleable.EmptyState_emps_iconSize, 1)]
        //description
        typedArray.getString(R.styleable.EmptyState_emps_text)?.let { text = it }
        textSize = typedArray.getDimensionPixelSize(
            R.styleable.EmptyState_emps_textSize,
            resources.getDimensionPixelSize(R.dimen.emps_default_text_size)
        )
        textColor = typedArray.getColor(
            R.styleable.EmptyState_emps_textColor,
            ContextCompat.getColor(context, R.color.emps_default_text_color)
        )
        //button
        showButton = typedArray.getBoolean(R.styleable.EmptyState_emps_showButton, false)
        typedArray.getString(R.styleable.EmptyState_emps_buttonText)?.let { buttonText = it }
        buttonTextColor = typedArray.getColor(
            R.styleable.EmptyState_emps_buttonTextColor,
            Color.WHITE
        )
        buttonTextSize = typedArray.getDimensionPixelSize(
            R.styleable.EmptyState_emps_buttonTextSize,
            resources.getDimensionPixelSize(R.dimen.emps_default_button_text_size)
        )
        buttonBackgroundColor = typedArray.getColor(
            R.styleable.EmptyState_emps_buttonBackgroundColor,
            ContextCompat.getColor(context, R.color.emps_default_button_color)
        )
        buttonCornerSize = typedArray.getDimensionPixelSize(
            R.styleable.EmptyState_emps_buttonCorner,
            resources.getDimensionPixelSize(R.dimen.emps_default_button_corner_size)
        )
        typedArray.recycle()
    }

    private fun initialView() {
        //icon
        emptyStateIcon.setImageResource(icon)
        setIconSize(iconSize)
        //text
        emptyStateText.text = text
        emptyStateText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        emptyStateText.setTextColor(textColor)
        //button
        if (showButton)
            emptyStateButton.visibility = View.VISIBLE
        else
            emptyStateButton.visibility = View.GONE
        emptyStateButton.text = buttonText
        emptyStateButton.setTextColor(buttonTextColor)
        emptyStateButton.cornerRadius = buttonCornerSize
        emptyStateButton.setBackgroundColor(buttonBackgroundColor)

        emptyStateButton.setOnClickListener {
            if (it.visibility == View.VISIBLE)
                buttonClickListener?.onClick(it)
        }
    }

    /**
     * set a drawable resource as icon
     * @param resourceId get drawable resource, R.drawable.something
     * @return instance of emptyState
     */
    fun setIcon(@DrawableRes resourceId: Int): EmptyState {
        emptyStateIcon.setImageResource(resourceId)
        return this
    }

    /**
     * set icon size type
     * @param iconSize get SMALL, NORMALL or BIG
     * @return instance of emptyState
     */
    fun setIconSize(iconSize: IconSize): EmptyState {
        val size = when (iconSize) {
            IconSize.BIG -> {
                resources.getDimensionPixelSize(R.dimen.emps_default_icon_big_size)
            }
            IconSize.SMALL -> {
                resources.getDimensionPixelSize(R.dimen.emps_default_icon_small_size)
            }
            else -> {
                resources.getDimensionPixelSize(R.dimen.emps_default_icon_normal_size)
            }
        }
        emptyStateIcon.layoutParams.height = size
        emptyStateIcon.layoutParams.width = size
        return this
    }

    /**
     * set text resource id
     * @param resourceId get a string resource id, R.string.something
     * @return instance of emptyState
     */
    fun setText(@StringRes resourceId: Int): EmptyState {
        text = context.getString(resourceId)
        emptyStateText.text = text
        return this
    }

    /**
     * set plain text
     * @param text get a string, "something"
     * @return instance of emptyState
     */
    fun setText(text: String): EmptyState {
        this.text = text
        emptyStateText.text = text
        return this
    }

    /**
     * set text color
     * @param colorRes get color resource id, R.color.someColor
     * @return instance of emptyState
     */
    fun setTextColorResource(@ColorRes colorRes: Int): EmptyState {
        textColor = ContextCompat.getColor(context, colorRes)
        emptyStateText.setTextColor(textColor)
        return this
    }

    /**
     * set text color
     * @param color get color
     * @return instance of emptyState
     */
    fun setTextColor(@ColorInt color: Int): EmptyState {
        textColor = color
        emptyStateText.setTextColor(color)
        return this
    }

    /**
     * set text size
     * @param dimenRes get dimension, R.dimen.someSize
     * @return instance of emptyState
     */
    fun setTextSize(@DimenRes dimenRes: Int): EmptyState {
        textSize = resources.getDimensionPixelSize(dimenRes)
        emptyStateText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        return this
    }

    /**
     * set button text resource id
     * @param resourceId get a string resource id, R.string.something
     * @return instance of emptyState
     */
    fun setButtonText(@StringRes resourceId: Int): EmptyState {
        buttonText = context.getString(resourceId)
        emptyStateText.text = text
        //because it has text and can't be GONE
        showButton = true
        emptyStateButton.visibility = View.VISIBLE
        return this
    }

    /**
     * set plain button text
     * @param text get a string, "something"
     * @return instance of emptyState
     */
    fun setButtonText(text: String): EmptyState {
        buttonText = text
        emptyStateText.text = text
        //because it has text and can't be GONE
        showButton = true
        emptyStateButton.visibility = View.VISIBLE
        return this
    }

    /**
     * set button background color
     * @param colorRes get color resource id, R.color.someColor
     * @return instance of emptyState
     */
    fun setButtonBackgroundColorResource(@ColorRes colorRes: Int): EmptyState {
        buttonBackgroundColor = ContextCompat.getColor(context, colorRes)
        emptyStateButton.setBackgroundColor(buttonBackgroundColor)
        return this
    }

    /**
     * set button background color
     * @param color get color
     * @return instance of emptyState
     */
    fun setButtonBackgroundColor(@ColorInt color: Int): EmptyState {
        buttonBackgroundColor = color
        emptyStateButton.setBackgroundColor(color)
        return this
    }

    /**
     * set button text size
     * @param dimenRes get dimension, R.dimen.someSize
     * @return instance of emptyState
     */
    fun setButtonTextSize(@DimenRes dimenRes: Int): EmptyState {
        buttonTextSize = resources.getDimensionPixelSize(dimenRes)
        emptyStateButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonTextSize.toFloat())
        return this
    }

    /**
     * set button corner size
     * @param dimenRes get dimension, R.dimen.someSize
     * @return instance of emptyState
     */
    fun setButtonCornerSize(@DimenRes dimenRes: Int): EmptyState {
        buttonCornerSize = resources.getDimensionPixelSize(dimenRes)
        emptyStateButton.cornerRadius = buttonCornerSize
        return this
    }

    /**
     * set button click listener
     * @param clickListener instance of OnClickListener
     * @return instance of emptyState
     */
    fun setButtonClickListener(clickListener: OnClickListener): EmptyState {
        buttonClickListener = clickListener
        return this
    }

    /**
     * showing EmptyState
     * @param animRes get animation resource, default is fade_in
     * @param interpolator get animation interpolator, default is AccelerateDecelerateInterpolator
     */
    fun show(@AnimRes animRes: Int = R.anim.abc_fade_in, interpolator: Interpolator = AccelerateDecelerateInterpolator()) {
        val fadeIn = AnimationUtils.loadAnimation(context, animRes)
        fadeIn.interpolator = interpolator
        startAnimation(fadeIn)
        visibility = View.VISIBLE
    }

    /**
     * hiding EmptyState
     * @param animRes get animations resource, default is fade_out
     * @param interpolator get animation interpolator, default is AccelerateDecelerateInterpolator
     */
    fun hide(@AnimRes animRes: Int = R.anim.abc_fade_out, interpolator: Interpolator = AccelerateDecelerateInterpolator()) {
        val fadeOut = AnimationUtils.loadAnimation(context, animRes)
        fadeOut.interpolator = interpolator
        startAnimation(fadeOut)
        visibility = View.INVISIBLE
    }

}