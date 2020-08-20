package com.example.filmsviewapp.io.rest

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmDto (
    @SerializedName("title") val title : String?,
    @SerializedName("release_date") val year : String?,
    @SerializedName("id") val id : String,
    @SerializedName("backdrop_path") val icon : String?
) : Parcelable

data class FilmsResponse(
    @SerializedName("results") val list : List<FilmDto>,
    @SerializedName("total_pages") val totalPages : Int
)
