package com.example.ui.di

import com.example.ui.fragments.CovidDataDetailFragment
import com.example.ui.fragments.CovidDataListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CovidFragmentBuilder {

    @ContributesAndroidInjector
    internal abstract fun bindDataListFragment(): CovidDataListFragment

    @ContributesAndroidInjector
    internal abstract fun bindDataDetailFragment(): CovidDataDetailFragment
}
