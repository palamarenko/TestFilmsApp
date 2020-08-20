package com.example.filmsviewapp.ui.base

import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_film.view.*

fun ImageView.loadPoster(path : String?){
    if(path!=null && path.isNotEmpty())
    Picasso.get().load("https://image.tmdb.org/t/p/w500$path").into(this)
}