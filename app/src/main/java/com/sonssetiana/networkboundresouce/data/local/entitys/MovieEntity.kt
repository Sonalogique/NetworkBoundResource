package com.sonssetiana.networkboundresouce.data.local.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sonssetiana.networkboundresouce.configs.Configs

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo("movie_id")
    val movieId: Int,
    val adult: Boolean,
    @ColumnInfo("backdrop_path")
    val backdropPath: String?,
    @ColumnInfo("genre_ids")
    val genreIds: List<Int>,
    @ColumnInfo("original_language")
    val originalLanguage: String,
    @ColumnInfo("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @ColumnInfo("poster_path")
    val posterPath: String?,
    @ColumnInfo("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @ColumnInfo("vote_average")
    val voteAverage: Double,
    @ColumnInfo("vote_count")
    val voteCount: Int
) {
    fun getPosterUrl(): String {
        return Configs.IMAGE_BASE_URL + posterPath
    }
}
