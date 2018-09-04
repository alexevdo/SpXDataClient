package com.sano.spxdataclient.presentation.launch

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sano.spxdataclient.model.Launch

interface LaunchesView: MvpView {
    fun setData(launches: List<Launch>)
    fun showError(message: String)
    @StateStrategyType(SkipStrategy::class)
    fun showLaunchDetailsScreen(launch: Launch)
    fun showProgress()
    fun hideProgress()
}