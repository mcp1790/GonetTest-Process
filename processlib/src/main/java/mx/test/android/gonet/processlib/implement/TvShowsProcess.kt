package mx.test.android.gonet.processlib.implement

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import mx.test.android.gonet.domainlib.models.*
import mx.test.android.gonet.domainlib.models.child.GenreModel
import mx.test.android.gonet.processlib.implement.BaseProcess
import mx.test.android.gonet.processlib.utils.isNetworkAvailable
import mx.test.android.gonet.servicelib.converters.ListMoviesConverter
import mx.test.android.gonet.servicelib.converters.ListTvShowConverter
import mx.test.android.gonet.servicelib.converters.MovieRawConverter
import mx.test.android.gonet.servicelib.entity.response.ListMoviesResponseEntity
import mx.test.android.gonet.servicelib.entity.response.ListTvShowsResponseEntity
import mx.test.android.gonet.servicelib.entity.response.MovieRawEntityResponse
import mx.test.android.gonet.servicelib.type.FlowEnum
import mx.test.android.gonet.servicelib.type.url
import javax.inject.Inject

@SuppressLint("CheckResult")
class TvShowsProcess @Inject constructor(var context: Context) : BaseProcess(context) {

    fun tvShowDetails(tvShowId: String): Observable<TvShowDetailModel> {
        return if (isNetworkAvailable(context)) {
            var tvShowModel = TvShowDetailModel()
            Observable.unsafeCreate { subscriber ->
                tvShowsProcess.tvShowDetails(tvShowId)
                    .flatMap {
                        tvShowModel = it
                        tvShowDetailsStorage.saveRx(it)
                    }.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        subscriber.onNext(tvShowModel)
                    }, {
                        subscriber.onError(it)
                    })
            }
        } else
            tvShowDetailsStorage.getRx(tvShowId.toInt())
    }

    fun listOfTvShows(
        flow: FlowEnum,
        idRecommended: String,
        page: Int,
    ): Observable<ListTvShowsModel> {
        return if (isNetworkAvailable(context)){
            var listMoviesTemp = ListTvShowsModel()
            return Observable.create { subscriber ->
                tvShowsProcess.listOfTvShows(flow, idRecommended, page)
                    .flatMap { listMoviesModel ->
                        listMoviesTemp = listMoviesModel

                        tvShowsProcess.listOfTvShowGenres()
                    }.flatMap { listGenreModels ->
                        listMoviesTemp.results.forEach { movieModel ->
                            movieModel.genres.forEach { genreModel ->
                                genreModel.name = listGenreModels.first{ it.id == genreModel.id}.name
                            }
                        }
                        tvShowListStorage.saveRx(listMoviesTemp)
                    }.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        subscriber.onNext(listMoviesTemp)
                    },{ error ->
                        subscriber.onError(error)
                    })
            }
        } else
            tvShowListStorage.getRx(page)
    }

    fun listOfTvShowsGenres(
    ): Observable<List<GenreModel>> {
        return tvShowsProcess.listOfTvShowGenres()
    }

    fun tvShowLatest(
    ): Observable<TvShowRawModel> {
        return tvShowsProcess.tvShowLatest()
    }

}