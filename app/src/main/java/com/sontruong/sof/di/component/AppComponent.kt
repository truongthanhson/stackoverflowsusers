
import android.content.Context
import com.sontruong.sof.SOFApplication
import com.sontruong.sof.di.module.*
import com.sontruong.sof.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [

        // Dagger support
        AndroidSupportInjectionModule::class,

        // App
        NetworkModule::class,
        DataModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        ActivityBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<SOFApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Context): AppComponent
    }
}