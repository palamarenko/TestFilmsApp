package com.example.filmsviewapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmsviewapp.R
import ua.palamarenko.cozyandroid2.base_fragment.navigation.tasks.simpleInit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        simpleInit(R.navigation.navigator)
    }
}