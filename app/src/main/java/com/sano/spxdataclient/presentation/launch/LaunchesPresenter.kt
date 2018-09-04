package com.sano.spxdataclient.presentation.launch

import com.sano.spacexlaunches.R
import com.sano.spxdataclient.Storage
import com.sano.spxdataclient.api.ApiUtils
import com.sano.spxdataclient.model.Launch
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LaunchesPresenter(private val mView: LaunchesView, private val mStorage: Storage) {

    private var mComposite = CompositeDisposable()

    var menuId: Int = R.id.action_all
        set(value) {
            field = value
            loadLaunches()
        }

    fun onListItemClicked(launch: Launch) = mView.showLaunchDetailsScreen(launch)

    fun loadLaunches() {
        when (menuId) {
            R.id.action_latest -> loadLatestLaunch()
            R.id.action_next -> loadNextLaunch()
            R.id.action_past -> loadPastLaunches()
            R.id.action_upcoming -> loadUpcomingLaunches()
            R.id.action_all -> loadAllLaunches()
        }
    }

    private fun loadLatestLaunch() {
        ApiUtils.getApiService().getLatestLaunch().map { listOf(it) }
                .subscribeOn(Schedulers.io())
                .onErrorReturn {
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(it::class))
                        return@onErrorReturn mStorage.getLatestLaunch()
                    null
                }
                .deliverResult()
    }

    private fun loadNextLaunch() {
        ApiUtils.getApiService().getNextLaunch().map { listOf(it) }
                .subscribeOn(Schedulers.io())
                .onErrorReturn {
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(it::class))
                        return@onErrorReturn mStorage.getNextLaunch()
                    null
                }
                .deliverResult()
    }

    private fun loadPastLaunches() {
        ApiUtils.getApiService().getPastLaunches()
                .subscribeOn(Schedulers.io())
                .onErrorReturn {
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(it::class))
                        return@onErrorReturn mStorage.getPastLaunches()
                    null
                }
                .deliverResult()
    }

    private fun loadUpcomingLaunches() {
        ApiUtils.getApiService().getUpcomingLaunches()
                .subscribeOn(Schedulers.io())
                .onErrorReturn {
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(it::class))
                        return@onErrorReturn mStorage.getUpcomingLaunches()
                    null
                }
                .deliverResult()
    }

    private fun loadAllLaunches() {
        ApiUtils.getApiService().getAllLaunches()
                .subscribeOn(Schedulers.io())
                .doOnSuccess { mStorage.overwriteLaunches(it) }
                .onErrorReturn {
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(it::class))
                        return@onErrorReturn mStorage.getAllLaunches()
                    null
                }
                .deliverResult()
    }

    fun onDestroy() {
        mComposite.clear()
    }

    private fun Single<List<Launch>>.deliverResult() {
        observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mView.isShowProgress = true }
                .doFinally { mView.isShowProgress = false }
                .subscribe({
                    mView.setData(it)
                }, {
                    mView.showError(it.message.toString())
                })
                .apply { mComposite.add(this) }
    }
}