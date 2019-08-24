package com.github.farzadfarazmand.emptystate

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
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
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.android.synthetic.main.empty_state.view.*

/**
 * Show different state of views, like noInternetConnection, noListData, notLogin, permissionNotGranted and ....
 * @author Farzad Farazmand
 * https://github.com/farzadfarazmand/EmptyStateLibrary
 */
class EmptyState : ConstraintLayout {

    //icon
    private var iconSize = IconSize.NORMAL
    private var icon = 0
    //title
    private var title = ""
    private var titleSize = resources.getDimensionPixelSize(R.dimen.emps_default_title_size)
    private var titleColor = ContextCompat.getColor(context, R.color.emps_default_title_color)
    //description
    private var description = ""
    private var descriptionSize = resources.getDimensionPixelSize(R.dimen.emps_default_description_size)
    private var descriptionColor = ContextCompat.getColor(context, R.color.emps_default_description_color)
    //button
    private var buttonText = ""
    private var showButton = false
    private var buttonTextColor = Color.WHITE
    private var buttonTextSize = resources.getDimensionPixelSize(R.dimen.emps_default_button_text_size)
    private var buttonBackgroundColor = ContextCompat.getColor(context, R.color.emps_default_button_color)
    private var buttonCornerSize = resources.getDimensionPixelSize(R.dimen.emps_default_button_corner_size)
    private var buttonClickListener: OnClickListener? = null
    //get font path from attrs and set the typefaces
    private var titleTypeface: Typeface? = null
    private var descriptionTypeface: Typeface? = null
    private var buttonTypeface: Typeface? = null

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
        //title
        typedArray.getString(R.styleable.EmptyState_emps_title)?.let { title = it }
        titleSize = typedArray.getDimensionPixelSize(
            R.styleable.EmptyState_emps_titleSize,
            resources.getDimensionPixelSize(R.dimen.emps_default_title_size)
        )
        titleColor = typedArray.getColor(
            R.styleable.EmptyState_emps_titleColor,
            ContextCompat.getColor(context, R.color.emps_default_title_color)
        )
        typedArray.getString(R.styleable.EmptyState_emps_titleFontPath)?.let {
            titleTypeface = Typeface.createFromAsset(context.assets, it)
        }
        //description
        typedArray.getString(R.styleable.EmptyState_emps_description)?.let { description = it }
        descriptionSize = typedArray.getDimensionPixelSize(
            R.styleable.EmptyState_emps_descriptionSize,
            resources.getDimensionPixelSize(R.dimen.emps_default_description_size)
        )
        descriptionColor = typedArray.getColor(
            R.styleable.EmptyState_emps_descriptionColor,
            ContextCompat.getColor(context, R.color.emps_default_description_color)
        )
        typedArray.getString(R.styleable.EmptyState_emps_descriptionFontPath)?.let {
            descriptionTypeface = Typeface.createFromAsset(context.assets, it)
        }
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
        typedArray.getString(R.styleable.EmptyState_emps_buttonFontPath)?.let {
            buttonTypeface = Typeface.createFromAsset(context.assets, it)
        }
        typedArray.recycle()
    }

    private fun initialView() {
        //icon
        emptyStateIcon.setImageResource(icon)
        setIconSize(iconSize)
        //title
        if (!TextUtils.isEmpty(title))
            emptyStateTitle.text = title
        else
            emptyStateTitle.visibility = View.GONE
        titleTypeface?.let { emptyStateTitle.typeface = it }
        emptyStateTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
        emptyStateTitle.setTextColor(titleColor)
        //description
        if (!TextUtils.isEmpty(description))
            emptyStateDescription.text = description
        else
            emptyStateDescription.visibility = View.GONE
        descriptionTypeface?.let { emptyStateDescription.typeface = it }
        emptyStateDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, descriptionSize.toFloat())
        emptyStateDescription.setTextColor(descriptionColor)
        //button
        if (showButton)
            emptyStateButton.visibility = View.VISIBLE
        else
            emptyStateButton.visibility = View.GONE
        emptyStateButton.text = buttonText
        emptyStateButton.setTextColor(buttonTextColor)
        emptyStateButton.cornerRadius = buttonCornerSize
        buttonTypeface?.let { emptyStateButton.typeface = it }
        DrawableCompat.setTint(emptyStateButton.background, buttonBackgroundColor)

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
     * set title resource id
     * @param resourceId get a string resource id, R.string.something
     * @return instance of emptyState
     */
    fun setTitle(@StringRes resourceId: Int): EmptyState {
        title = context.getString(resourceId)
        emptyStateTitle.text = title
        emptyStateTitle.visibility = View.VISIBLE
        return this
    }

    /**
     * set plain title
     * @param text get a string, "something"
     * @return instance of emptyState
     */
    fun setTitle(text: String): EmptyState {
        this.title = text
        emptyStateTitle.text = text
        emptyStateTitle.visibility = View.VISIBLE
        return this
    }

    /**
     * set title color
     * @param colorRes get color resource id, R.color.someColor
     * @return instance of emptyState
     */
    fun setTitleColorResource(@ColorRes colorRes: Int): EmptyState {
        titleColor = ContextCompat.getColor(context, colorRes)
        emptyStateTitle.setTextColor(titleColor)
        return this
    }

    /**
     * set description color
     * @param color get color
     * @return instance of emptyState
     */
    fun setTitleColor(@ColorInt color: Int): EmptyState {
        titleColor = color
        emptyStateTitle.setTextColor(color)
        return this
    }

    /**
     * set title size
     * @param dimenRes get dimension, R.dimen.someSize
     * @return instance of emptyState
     */
    fun setTitleSize(@DimenRes dimenRes: Int): EmptyState {
        titleSize = resources.getDimensionPixelSize(dimenRes)
        emptyStateTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
        return this
    }

    /**
     * set title typeface
     * @param typeface
     * @return instance of emptyState
     */
    fun setTitleTypeface(typeface: Typeface): EmptyState {
        titleTypeface = typeface
        emptyStateTitle.typeface = titleTypeface
        return this
    }


    /**
     * set description resource id
     * @param resourceId get a string resource id, R.string.something
     * @return instance of emptyState
     */
    fun setDescription(@StringRes resourceId: Int): EmptyState {
        description = context.getString(resourceId)
        emptyStateDescription.text = description
        emptyStateDescription.visibility = View.VISIBLE
        return this
    }

    /**
     * set plain description
     * @param text get a string, "something"
     * @return instance of emptyState
     */
    fun setDescription(text: String): EmptyState {
        this.description = text
        emptyStateDescription.text = text
        emptyStateDescription.visibility = View.VISIBLE
        return this
    }

    /**
     * set description color
     * @param colorRes get color resource id, R.color.someColor
     * @return instance of emptyState
     */
    fun setDescriptionColorResource(@ColorRes colorRes: Int): EmptyState {
        descriptionColor = ContextCompat.getColor(context, colorRes)
        emptyStateDescription.setTextColor(descriptionColor)
        return this
    }

    /**
     * set description color
     * @param color get color
     * @return instance of emptyState
     */
    fun setDescriptionColor(@ColorInt color: Int): EmptyState {
        descriptionColor = color
        emptyStateDescription.setTextColor(color)
        return this
    }

    /**
     * set description size
     * @param dimenRes get dimension, R.dimen.someSize
     * @return instance of emptyState
     */
    fun setDescriptionSize(@DimenRes dimenRes: Int): EmptyState {
        descriptionSize = resources.getDimensionPixelSize(dimenRes)
        emptyStateDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, descriptionSize.toFloat())
        return this
    }

    /**
     * set description typeface
     * @param typeface
     * @return instance of emptyState
     */
    fun setDescriptionTypeface(typeface: Typeface): EmptyState {
        descriptionTypeface = typeface
        emptyStateDescription.typeface = descriptionTypeface
        return this
    }

    /**
     * set button description resource id
     * @param resourceId get a string resource id, R.string.something
     * @return instance of emptyState
     */
    fun setButtonText(@StringRes resourceId: Int): EmptyState {
        buttonText = context.getString(resourceId)
        emptyStateDescription.text = description
        //because it has description and can't be GONE
        showButton = true
        emptyStateButton.visibility = View.VISIBLE
        return this
    }

    /**
     * set plain button description
     * @param text get a string, "something"
     * @return instance of emptyState
     */
    fun setButtonText(text: String): EmptyState {
        buttonText = text
        emptyStateDescription.text = text
        //because it has description and can't be GONE
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
        DrawableCompat.setTint(emptyStateButton.background, buttonBackgroundColor)
        return this
    }

    /**
     * set button background color
     * @param color get color
     * @return instance of emptyState
     */
    fun setButtonBackgroundColor(@ColorInt color: Int): EmptyState {
        buttonBackgroundColor = color
        DrawableCompat.setTint(emptyStateButton.background, buttonBackgroundColor)
        return this
    }

    /**
     * set button description size
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
     * set button typeface
     * @param typeface
     * @return instance of emptyState
     */
    fun setButtonTypeface(typeface: Typeface): EmptyState {
        buttonTypeface = typeface
        emptyStateButton.typeface = buttonTypeface
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