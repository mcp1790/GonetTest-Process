package mx.test.android.gonet.processlib

import dagger.Component
import mx.test.android.gonet.processlib.BaseProcessTest
import mx.test.android.gonet.servicelib.di.ServiceModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ServiceModule::class])
interface ProcessComponentTest {
    fun inject(service: BaseProcessTest)
}