package mx.test.android.gonet.processlib.implement

import android.content.Context
import mx.test.android.gonet.processlib.di.DaggerProcessComponent
import mx.test.android.gonet.servicelib.di.ServiceModule
import mx.test.android.gonet.servicelib.implement.MoviesService
import mx.test.android.gonet.servicelib.implement.TvShowsService
import mx.test.android.gonet.storagelib.implement.*
import javax.inject.Inject

open class BaseProcess(context: Context) {

    @Inject
    lateinit var moviesProcess: MoviesService

    @Inject
    lateinit var tvShowsProcess: TvShowsService

    protected val moviesListStorage: MoviesListStorage = MoviesListStorage(context)
    protected val moviesRawStorage: MoviesRawStorage = MoviesRawStorage(context)
    protected val tvShowDetailsStorage: TVShowDetailsStorage = TVShowDetailsStorage(context)
    protected val tvShowListStorage: TVShowListStorage = TVShowListStorage(context)
    protected val tvShowRawStorage: TvShowRawStorage = TvShowRawStorage(context)

    init {
        DaggerProcessComponent.builder()
            .serviceModule(ServiceModule(context))
            .build()
            .inject(this)
    }
}