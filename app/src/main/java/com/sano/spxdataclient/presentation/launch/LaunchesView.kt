package com.sano.spxdataclient.presentation.launch

import com.sano.spxdataclient.model.Launch

interface LaunchesView {
    fun setData(launches: List<Launch>)
    fun showError(message: String)
    fun showLaunchDetailsScreen(launch: Launch)
    var isShowProgress: Boolean
}