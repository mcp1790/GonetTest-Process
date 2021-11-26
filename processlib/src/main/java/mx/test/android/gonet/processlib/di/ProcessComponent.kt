package mx.test.android.gonet.processlib.di

import dagger.Component
import mx.test.android.gonet.processlib.implement.BaseProcess
import mx.test.android.gonet.servicelib.di.ServiceModule

@Component(modules = [ServiceModule::class])
interface ProcessComponent {
    fun inject(process: BaseProcess)
}