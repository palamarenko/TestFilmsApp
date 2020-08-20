package com.example.filmsviewapp.ui.list

import android.view.View
import ua.palamarenko.cozyandroid2.CozyCell
import com.example.filmsviewapp.R
import com.example.filmsviewapp.io.rest.FilmDto
import com.example.filmsviewapp.ui.base.loadPoster
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_film.view.*
import ua.palamarenko.cozyandroid2.tools.click
import ua.palamarenko.cozyandroid2.tools.load

class FilmCell(override val data: FilmDto, val viewModel: ListViewModel) : CozyCell() {

    override val layout = R.layout.cell_film

    override fun bind(view: View) {
        view.ivIcon.loadPoster(data.icon)
        view.ivIcon.click {
            viewModel.navigateToSingle(data)
        }
        view.tvName.text = data.title
        view.tvYear.text = data.year
    }

}