package com.github.asuslennikov.taskman.domain.di

import dagger.Component

@DomainScope
@Component(
    modules = [DomainModule::class],
    dependencies = [DomainDependencies::class]
)
internal interface DomainComponent : DomainProvider {

}