package com.github.asuslennikov.taskman

import android.app.Application
import com.github.asuslennikov.mvvm.presentation.ViewModelProvider
import com.github.asuslennikov.taskman.data.di.DaggerDataComponent
import com.github.asuslennikov.taskman.di.ApplicationModule
import com.github.asuslennikov.taskman.di.DaggerApplicationComponent
import com.github.asuslennikov.taskman.domain.di.DaggerDomainComponent
import com.github.asuslennikov.taskman.domain.di.DomainProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class TaskApplication : Application(), ComponentRegistry {

    enum class InitializationStatus {
        NOT_READY,
        COMPLETE,
        FAILURE
    }

    private lateinit var viewModelProvider: ViewModelProvider
    private val initializationSubject: Subject<InitializationStatus> =
        BehaviorSubject.createDefault(InitializationStatus.NOT_READY)
    private var initializationDisposable: Disposable? = null

    override fun onCreate() {
        super.onCreate()
        initializationDisposable = Observable.fromCallable { createComponentRegistry() }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ registry ->
                afterComponentRegistryInitialized(registry)
            }, { _ ->
                initializationSubject.onNext(InitializationStatus.FAILURE)
            })
    }

    private fun createComponentRegistry(): ComponentRegistry {
        val appModule = ApplicationModule(this)

        val dataProvider = DaggerDataComponent.builder()
            .dataDependencies(appModule)
            .build()

        val domainProvider: DomainProvider = DaggerDomainComponent.builder()
            .domainDependencies(dataProvider)
            .build()

        val appComponent = DaggerApplicationComponent.builder()
            .applicationModule(appModule)
            .dataProvider(dataProvider)
            .domainProvider(domainProvider)
            .build()
        // pre-fill dependency graph
        appComponent.getViewModelProvider()

        return appComponent
    }

    private fun afterComponentRegistryInitialized(componentRegistry: ComponentRegistry) {
        viewModelProvider = componentRegistry.getViewModelProvider()
        initializationSubject.onNext(InitializationStatus.COMPLETE)
    }

    override fun getViewModelProvider(): ViewModelProvider {
        return viewModelProvider
    }

    fun getInitializationStatus(): Observable<InitializationStatus> = initializationSubject
}