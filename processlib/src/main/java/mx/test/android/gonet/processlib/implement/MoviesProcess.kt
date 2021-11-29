package mx.test.android.gonet.processlib.implement

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.domainlib.models.TvShowRawModel
import mx.test.android.gonet.domainlib.models.child.GenreModel
import mx.test.android.gonet.processlib.utils.isNetworkAvailable
import mx.test.android.gonet.servicelib.type.FlowEnum
import javax.inject.Inject

@SuppressLint("CheckResult")
class MoviesProcess @Inject constructor(var context: Context) : BaseProcess(context) {

    fun moviesDetails(movieId: String): Observable<MovieRawModel> {
        return if (isNetworkAvailable(context)) {
            var movieModel = MovieRawModel()
            Observable.unsafeCreate { subscriber ->
                moviesProcess.moviesDetails(movieId)
                    .flatMap {
                        movieModel = it
                        moviesRawStorage.saveRx(it)
                    }.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        subscriber.onNext(movieModel)
                    }, {
                        subscriber.onError(it)
                    })
            }
        } else
            moviesRawStorage.getRx(movieId.toInt())
    }

    fun listOfMovies(
        flow: FlowEnum,
        idRecommended: String,
        page: Int,
    ): Observable<ListMoviesModel> {
        return if (isNetworkAvailable(context)) {
            var listMoviesTemp = ListMoviesModel()
            return Observable.create { subscriber ->
                moviesProcess.listOfMovies(flow, idRecommended, page)
                    .flatMap { listMoviesModel ->
                        listMoviesTemp = listMoviesModel

                        moviesProcess.listOfMoviesGenres()
                    }.flatMap { listGenreModels ->
                        listMoviesTemp.results.forEach { movieModel ->
                            movieModel.genres.forEach { genreModel ->
                                genreModel.name =
                                    listGenreModels.first { it.id == genreModel.id }.name
                            }
                        }

                        moviesListStorage.saveRx(listMoviesTemp)
                    }.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        subscriber.onNext(listMoviesTemp)
                    }, { error ->
                        subscriber.onError(error)
                    })
            }
        } else
            moviesListStorage.getRx(page)
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