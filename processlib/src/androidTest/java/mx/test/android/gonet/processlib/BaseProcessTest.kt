package mx.test.android.gonet.processlib

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import mx.test.android.gonet.processlib.di.DaggerProcessComponent
import mx.test.android.gonet.processlib.implement.MoviesProcess
import mx.test.android.gonet.processlib.implement.TvShowsProcess
import mx.test.android.gonet.servicelib.di.ServiceModule
import mx.test.android.gonet.servicelib.implement.MoviesService
import mx.test.android.gonet.servicelib.implement.TvShowsService
import org.junit.Before
import javax.inject.Inject

open class BaseProcessTest {
    private lateinit var appContext: Context
    protected var _syncObject = Object()

    @Inject
    lateinit var moviesProcess: MoviesProcess
    @Inject
    lateinit var tvShowsProcess: TvShowsProcess

    @Before
    fun setUp() {
        appContext  = InstrumentationRegistry.getInstrumentation().targetContext
        DaggerProcessComponentTest.builder()
            .serviceModule(ServiceModule(appContext))
            .build()
            .inject(this)
    }
}