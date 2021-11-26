package mx.test.android.gonet.processlib

import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase
import mx.test.android.gonet.servicelib.type.FlowEnum
import org.junit.Assert
import org.junit.Test
import kotlin.jvm.Throws

class TvShowsServiceTest: BaseProcessTest() {

    @Test(timeout = 3000000)
    @Throws(InterruptedException::class)
    fun tvShowDetailsTest() {
        tvShowsProcess.tvShowDetails("20")
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                TestCase.assertTrue(true)

                synchronized(_syncObject) { _syncObject.notify() }
            },{
                Assert.fail("error.localizedMessage")

                synchronized(_syncObject) { _syncObject.notify() }
            })
    }

    @Test(timeout = 3000000)
    @Throws(InterruptedException::class)
    fun moviesListTest() {
        tvShowsProcess.listOfTvShows(
            flow = FlowEnum.TvShowsTopRated,
            idRecommended = "500",
            page = 1
        ).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                TestCase.assertTrue(true)

                synchronized(_syncObject) { _syncObject.notify() }
            },{
                Assert.fail("error.localizedMessage")

                synchronized(_syncObject) { _syncObject.notify() }
            })
    }
}