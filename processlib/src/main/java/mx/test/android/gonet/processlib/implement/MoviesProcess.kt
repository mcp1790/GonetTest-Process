package mx.test.android.gonet.processlib.implement

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.domainlib.models.TvShowRawModel
import mx.test.android.gonet.domainlib.models.child.GenreModel
import mx.test.android.gonet.processlib.implement.BaseProcess
import mx.test.android.gonet.servicelib.converters.ListMoviesConverter
import mx.test.android.gonet.servicelib.converters.MovieRawConverter
import mx.test.android.gonet.servicelib.entity.response.ListMoviesResponseEntity
import mx.test.android.gonet.servicelib.entity.response.MovieRawEntityResponse
import mx.test.android.gonet.servicelib.type.FlowEnum
import mx.test.android.gonet.servicelib.type.url
import javax.inject.Inject

@SuppressLint("CheckResult")
class MoviesProcess @Inject constructor(var context: Context) : BaseProcess(context) {

    fun moviesDetails(movieId: String): Observable<MovieRawModel> {
        return moviesProcess.moviesDetails(movieId)
    }

    fun listOfMovies(
        flow: FlowEnum,
        idRecommended: String,
        page: Int,
    ): Observable<ListMoviesModel> {
        return moviesProcess.listOfMovies(flow, idRecommended, page)
    }

    fun listOfMoviesGenres(
    ): Observable<List<GenreModel>> {
        return moviesProcess.listOfMoviesGenres()
    }

    fun movieLatest(
    ): Observable<MovieRawModel> {
        return moviesProcess.movieLatest()
    }
}