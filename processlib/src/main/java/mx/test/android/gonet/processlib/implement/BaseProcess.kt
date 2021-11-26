package mx.test.android.gonet.processlib.implement

import android.content.Context
import mx.test.android.gonet.processlib.di.DaggerProcessComponent
import mx.test.android.gonet.servicelib.di.ServiceModule
import mx.test.android.gonet.servicelib.implement.MoviesService
import mx.test.android.gonet.servicelib.implement.TvShowsService
import javax.inject.Inject

open class BaseProcess(context: Context) {

    @Inject
    lateinit var moviesProcess: MoviesService

    @Inject
    lateinit var tvShowsProcess: TvShowsService

    init {
        DaggerProcessComponent.builder()
            .serviceModule(ServiceModule(context))
            .build()
            .inject(this)
    }
}