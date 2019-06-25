package com.github.farzadfarazmand.emptystatelibrary

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun testClick(view: View) {
        Toast.makeText(this, "button clicked!", Toast.LENGTH_SHORT).show()
    }
}
