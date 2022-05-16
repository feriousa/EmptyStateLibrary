package com.github.farzadfarazmand.emptystate

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.annotation.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.github.farzadfarazmand.emptystate.databinding.EmptyStateBinding

/**
 * Show different state of views, like noInternetConnection, noListData, notLogin, permissionNotGranted and ....
 * @author Farzad Farazmand
 * https://github.com/farzadfarazmand/EmptyStateLibrary
 */
class EmptyState : ConstraintLayout {

    private val binding: EmptyStateBinding

    //icon
    private var iconSize = IconSize.NORMAL
    private var icon = 0
    private var isFullScreen = false

    //title
    private var title = ""
    private var titleSize = resources.getDimensionPixelSize(R.dimen.emps_default_title_size)
    private var titleColor = ContextCompat.getColor(context, R.color.emps_default_title_color)

    //description
    private var description = ""
    private var descriptionSize =
        resources.getDimensionPixelSize(R.dimen.emps_default_description_size)
    private var descriptionColor =
        ContextCompat.getColor(context, R.color.emps_default_description_color)

    //button
    private var buttonText = ""
    private var showButton = false
    private var buttonTextColor = Color.WHITE
    private var buttonTextSize =
        resources.getDimensionPixelSize(R.dimen.emps_default_button_text_size)
    private var buttonBackgroundColor =
        ContextCompat.getColor(context, R.color.emps_default_button_color)
    private var buttonCornerSize =
        resources.getDimensionPixelSize(R.dimen.emps_default_button_corner_size)
    private var buttonClickListener: OnClickListener? = null

    private var typeface: Typeface? = null

    constructor(context: Context) : super(context) {
        initialView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let { handleAttribute(it) }
        initialView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        attrs?.let { handleAttribute(it) }
        initialView()
    }

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        binding = EmptyStateBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun handleAttribute(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyState)

        //icon
        icon = typedArray.getResourceId(R.styleable.EmptyState_emps_icon, 0)
        iconSize = IconSize.values()[typedArray.getInt(R.styleable.EmptyState_emps_iconSize, 1)]
        isFullScreen = typedArray.getBoolean(R.styleable.EmptyState_emps_fullscreen, false)

        //font
        if (typedArray.hasValue(R.styleable.EmptyState_emps_font)) {
            val fontId = typedArray.getResourceId(R.styleable.EmptyState_emps_font, -1)
            typeface = ResourcesCompat.getFont(context, fontId)
        }

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
        binding.apply {
            //icon
            setIcon(icon)

            //font
            typeface?.let { setTypeface(it) }

            //title
            if (!TextUtils.isEmpty(title))
                emptyStateTitle.text = title
            else
                emptyStateTitle.visibility = View.GONE
            emptyStateTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
            emptyStateTitle.setTextColor(titleColor)
            //description
            if (!TextUtils.isEmpty(description))
                emptyStateDescription.text = description
            else
                emptyStateDescription.visibility = View.GONE
            emptyStateDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, descriptionSize.toFloat())
            emptyStateDescription.setTextColor(descriptionColor)
            //button
            if (showButton)
                emptyStateButton.visibility = View.VISIBLE
            else
                emptyStateButton.visibility = View.GONE
            emptyStateButton.text = buttonText
            emptyStateButton.setTextColor(buttonTextColor)
            emptyStateButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonTextSize.toFloat())
            emptyStateButton.cornerRadius = buttonCornerSize
            DrawableCompat.setTint(emptyStateButton.background, buttonBackgroundColor)

            emptyStateButton.setOnClickListener {
                if (it.visibility == View.VISIBLE)
                    buttonClickListener?.onClick(it)
            }
        }
    }

    /**
     * set a drawable resource as icon
     * @param resourceId get drawable resource, R.drawable.something
     * @return instance of emptyState
     */
    fun setIcon(@DrawableRes resourceId: Int): EmptyState {
        binding.apply {
            if (isFullScreen) {
                emptyStateBg.setImageResource(resourceId)
                emptyStateIcon.visibility = View.INVISIBLE
                emptyStateBg.visibility = View.VISIBLE
            } else {
                emptyStateIcon.setImageResource(resourceId)
                emptyStateBg.visibility = View.INVISIBLE
                emptyStateIcon.visibility = View.VISIBLE
            }
        }
        return this
    }

    /**
     * set fullscreen state, must be called before set icon!
     *
     */
    fun isFullScreen(isFullscreen: Boolean): EmptyState {
        this.isFullScreen = isFullscreen
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
        binding.apply {
            emptyStateIcon.layoutParams.height = size
            emptyStateIcon.layoutParams.width = size
        }
        return this
    }

    /**
     * set font
     * @param typeface
     * @return instance of emptyState
     */
    fun setTypeface(typeface: Typeface): EmptyState {
        this.typeface = typeface
        binding.apply {
            emptyStateTitle.typeface = typeface
            emptyStateDescription.typeface = typeface
            emptyStateButton.typeface = typeface
        }
        return this
    }


    /**
     * set font
     * @param fontRes resource
     * @return instance of emptyState
     */
    fun setTypeface(@FontRes fontRes: Int): EmptyState {
        typeface = ResourcesCompat.getFont(context, fontRes)
        return typeface?.let { setTypeface(it) } ?: this
    }

    /**
     * set title resource id
     * @param resourceId get a string resource id, R.string.something
     * @return instance of emptyState
     */
    fun setTitle(@StringRes resourceId: Int): EmptyState {
        title = context.getString(resourceId)
        binding.apply {
            emptyStateTitle.text = title
            emptyStateTitle.visibility = View.VISIBLE
        }
        return this
    }

    /**
     * set plain title
     * @param text get a string, "something"
     * @return instance of emptyState
     */
    fun setTitle(text: String): EmptyState {
        this.title = text
        binding.apply {
            emptyStateTitle.text = text
            emptyStateTitle.visibility = View.VISIBLE
        }
        return this
    }

    /**
     * set title color
     * @param colorRes get color resource id, R.color.someColor
     * @return instance of emptyState
     */
    fun setTitleColorResource(@ColorRes colorRes: Int): EmptyState {
        titleColor = ContextCompat.getColor(context, colorRes)
        binding.emptyStateTitle.setTextColor(titleColor)
        return this
    }

    /**
     * set description color
     * @param color get color
     * @return instance of emptyState
     */
    fun setTitleColor(@ColorInt color: Int): EmptyState {
        titleColor = color
        binding.emptyStateTitle.setTextColor(color)
        return this
    }

    /**
     * set title size
     * @param dimenRes get dimension, R.dimen.someSize
     * @return instance of emptyState
     */
    fun setTitleSize(@DimenRes dimenRes: Int): EmptyState {
        titleSize = resources.getDimensionPixelSize(dimenRes)
        binding.emptyStateTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
        return this
    }

    /**
     * set description resource id
     * @param resourceId get a string resource id, R.string.something
     * @return instance of emptyState
     */
    fun setDescription(@StringRes resourceId: Int): EmptyState {
        description = context.getString(resourceId)
        binding.emptyStateDescription.text = description
        binding.emptyStateDescription.visibility = View.VISIBLE
        return this
    }

    /**
     * set plain description
     * @param text get a string, "something"
     * @return instance of emptyState
     */
    fun setDescription(text: String): EmptyState {
        this.description = text
        binding.emptyStateDescription.text = text
        binding.emptyStateDescription.visibility = View.VISIBLE
        return this
    }

    /**
     * set description color
     * @param colorRes get color resource id, R.color.someColor
     * @return instance of emptyState
     */
    fun setDescriptionColorResource(@ColorRes colorRes: Int): EmptyState {
        descriptionColor = ContextCompat.getColor(context, colorRes)
        binding.emptyStateDescription.setTextColor(descriptionColor)
        return this
    }

    /**
     * set description color
     * @param color get color
     * @return instance of emptyState
     */
    fun setDescriptionColor(@ColorInt color: Int): EmptyState {
        descriptionColor = color
        binding.emptyStateDescription.setTextColor(color)
        return this
    }

    /**
     * set description size
     * @param dimenRes get dimension, R.dimen.someSize
     * @return instance of emptyState
     */
    fun setDescriptionSize(@DimenRes dimenRes: Int): EmptyState {
        descriptionSize = resources.getDimensionPixelSize(dimenRes)
        binding.emptyStateDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, descriptionSize.toFloat())
        return this
    }

    /**
     * set button description resource id
     * @param resourceId get a string resource id, R.string.something
     * @return instance of emptyState
     */
    fun setButtonText(@StringRes resourceId: Int): EmptyState {
        buttonText = context.getString(resourceId)
        binding.emptyStateDescription.text = description
        //because it has description and can't be GONE
        showButton = true
        binding.emptyStateButton.visibility = View.VISIBLE
        return this
    }

    /**
     * set plain button description
     * @param text get a string, "something"
     * @return instance of emptyState
     */
    fun setButtonText(text: String): EmptyState {
        buttonText = text
        binding.emptyStateDescription.text = text
        //because it has description and can't be GONE
        showButton = true
        binding.emptyStateButton.visibility = View.VISIBLE
        return this
    }

    /**
     * set button background color
     * @param colorRes get color resource id, R.color.someColor
     * @return instance of emptyState
     */
    fun setButtonBackgroundColorResource(@ColorRes colorRes: Int): EmptyState {
        buttonBackgroundColor = ContextCompat.getColor(context, colorRes)
        DrawableCompat.setTint(binding.emptyStateButton.background, buttonBackgroundColor)
        return this
    }

    /**
     * set button background color
     * @param color get color
     * @return instance of emptyState
     */
    fun setButtonBackgroundColor(@ColorInt color: Int): EmptyState {
        buttonBackgroundColor = color
        DrawableCompat.setTint(binding.emptyStateButton.background, buttonBackgroundColor)
        return this
    }

    /**
     * set button description size
     * @param dimenRes get dimension, R.dimen.someSize
     * @return instance of emptyState
     */
    fun setButtonTextSize(@DimenRes dimenRes: Int): EmptyState {
        buttonTextSize = resources.getDimensionPixelSize(dimenRes)
        binding.emptyStateButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonTextSize.toFloat())
        return this
    }

    /**
     * set button corner size
     * @param dimenRes get dimension, R.dimen.someSize
     * @return instance of emptyState
     */
    fun setButtonCornerSize(@DimenRes dimenRes: Int): EmptyState {
        buttonCornerSize = resources.getDimensionPixelSize(dimenRes)
        binding.emptyStateButton.cornerRadius = buttonCornerSize
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
    fun show(
        @AnimRes animRes: Int = android.R.anim.fade_in,
        interpolator: Interpolator = AccelerateDecelerateInterpolator()
    ) {
        val animation = AnimationUtils.loadAnimation(context, animRes)
        animation.interpolator = interpolator
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
                visibility = View.VISIBLE
            }
        })
        startAnimation(animation)

    }

    /**
     * hiding EmptyState
     * @param animRes get animations resource, default is fade_out
     * @param interpolator get animation interpolator, default is AccelerateDecelerateInterpolator
     */
    fun hide(
        @AnimRes animRes: Int = android.R.anim.fade_out,
        interpolator: Interpolator = AccelerateDecelerateInterpolator()
    ) {
        val animation = AnimationUtils.loadAnimation(context, animRes)
        animation.interpolator = interpolator
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                visibility = View.INVISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        startAnimation(animation)
    }

}