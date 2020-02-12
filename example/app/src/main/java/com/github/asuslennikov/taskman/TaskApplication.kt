package com.github.asuslennikov.taskman

import android.app.Application
import com.github.asuslennikov.mvvm.presentation.ViewModelProvider
import com.github.asuslennikov.taskman.data.di.DaggerDataComponent
import com.github.asuslennikov.taskman.di.ApplicationComponent
import com.github.asuslennikov.taskman.di.ApplicationModule
import com.github.asuslennikov.taskman.di.DaggerApplicationComponent
import com.github.asuslennikov.taskman.domain.di.DaggerDomainComponent
import com.github.asuslennikov.taskman.domain.di.DomainProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate() {
        super.onCreate()
        disposable.add(
            Observable.fromCallable { createApplicationComponent() }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ component ->
                    afterCreateApplicationComponent(component)
                }, {
                    initializationSubject.onNext(InitializationStatus.FAILURE)
                })
        )
    }

    private fun createApplicationComponent(): ApplicationComponent {
        val appModule = ApplicationModule(this)

        val dataProvider = DaggerDataComponent.builder()
            .dataDependencies(appModule)
            .build()

        val domainProvider: DomainProvider = DaggerDomainComponent.builder()
            .domainDependencies(dataProvider)
            .build()

        return DaggerApplicationComponent.builder()
            .applicationModule(appModule)
            .dataProvider(dataProvider)
            .domainProvider(domainProvider)
            .build().apply {
                getViewModelProvider()
            }
    }

    private fun afterCreateApplicationComponent(component: ApplicationComponent) {
        viewModelProvider = component.getViewModelProvider()
        initializationSubject.onNext(InitializationStatus.COMPLETE)
    }

    override fun getViewModelProvider(): ViewModelProvider = viewModelProvider

    fun getInitializationStatus(): Observable<InitializationStatus> = initializationSubject
}