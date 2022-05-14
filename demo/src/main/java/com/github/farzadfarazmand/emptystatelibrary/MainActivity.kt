package com.github.farzadfarazmand.emptystatelibrary

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // if want to use svg drawable as empty state icon, add below line and set vectorDrawables.useSupportLibrary to true in defaultConfig
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setContentView(R.layout.activity_main)

        emptyStateSample.setButtonClickListener(View.OnClickListener {
            Toast.makeText(
                this@MainActivity,
                "button clicked!",
                Toast.LENGTH_SHORT
            ).show()
        })
        // show emptyState with animation
        Handler().postDelayed({ emptyStateSample.show(android.R.anim.slide_in_left, OvershootInterpolator()) }, 3000)
//        Handler().postDelayed({ emptyStateSample.hide(android.R.anim.slide_out_right, OvershootInterpolator()) }, 6000)
    }
}
