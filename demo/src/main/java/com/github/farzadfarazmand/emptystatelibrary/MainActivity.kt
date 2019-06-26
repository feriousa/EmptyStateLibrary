package com.github.farzadfarazmand.emptystatelibrary

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emptyStateSample.setButtonClickListener(View.OnClickListener {
            Toast.makeText(
                this@MainActivity,
                "button clicked!",
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}
