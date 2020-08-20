package com.example.filmsviewapp.ui.single

import com.example.filmsviewapp.R
import com.example.filmsviewapp.io.rest.FilmDto
import com.example.filmsviewapp.ui.base.BaseFragment
import com.example.filmsviewapp.ui.base.loadPoster
import com.example.filmsviewapp.ui.list.DATA
import com.example.filmsviewapp.ui.list.FilmCell
import kotlinx.android.synthetic.main.cell_film.view.*
import kotlinx.android.synthetic.main.cell_film.view.ivIcon
import kotlinx.android.synthetic.main.fragment_single.*
import ua.palamarenko.cozyandroid2.tools.click


class SingleFragment : BaseFragment<SingleViewModel>() {

    override val layout = R.layout.fragment_single

    override fun onStartScreen() {
        super.onStartScreen()
        getArgumentParcelable(DATA, FilmDto::class.java)?.apply {
            ivIcon.loadPoster(icon)
            tvName.text = title
            tvYear.text = year
        }
    }


}