package com.example.filmsviewapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.navigation.fragment.NavHostFragment
import com.example.filmsviewapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        simpleInit(R.navigation.navigator)
    }
}



fun AppCompatActivity.simpleInit(id : Int){
    val frameLayout = FrameLayout(this)
    frameLayout.id = View.generateViewId()
    this.setContentView(frameLayout)
    val finalHost = NavHostFragment.create(id)
    supportFragmentManager.beginTransaction()
        .replace(frameLayout.id, finalHost)
        .setPrimaryNavigationFragment(finalHost)
        .commit()
}