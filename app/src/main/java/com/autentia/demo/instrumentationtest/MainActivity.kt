package com.autentia.demo.instrumentationtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_login.setOnClickListener {
            if (input_field_username.text.isNullOrEmpty()) {
                field_username.error = getString(R.string.empty_field_error)
            } else {
                field_username.error = ""
            }

            if (input_field_password.text.isNullOrEmpty()) {
                field_password.error = getString(R.string.empty_field_error)
            } else {
                field_password.error = ""
            }
        }

    }


}
