package com.sano.spxdataclient.presentation.launch

import android.support.annotation.IntDef
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.sano.spxdataclient.Storage
import com.sano.spxdataclient.api.ApiUtils
import com.sano.spxdataclient.model.Launch
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val LATEST_LAUNCH = 0
const val NEXT_LAUNCH = 1
const val PAST_LAUNCHES = 2
const val UPCOMING_LAUNCHES = 3
const val ALL_LAUNCHES = 4

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@IntDef(LATEST_LAUNCH, NEXT_LAUNCH, PAST_LAUNCHES, UPCOMING_LAUNCHES, ALL_LAUNCHES)
annotation class LaunchType

@InjectViewState
class LaunchesPresenter(private val mStorage: Storage) : MvpPresenter<LaunchesView>() {

    private var mComposite = CompositeDisposable()

    @LaunchType
    var menuId: Int = ALL_LAUNCHES
        set(value) {
            field = value
            loadLaunches()
        }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadLaunches()
    }

    fun onListItemClicked(launch: Launch) = viewState.showLaunchDetailsScreen(launch)

    fun loadLaunches() = when (menuId) {
        LATEST_LAUNCH -> loadLatestLaunch()
        NEXT_LAUNCH -> loadNextLaunch()
        PAST_LAUNCHES -> loadPastLaunches()
        UPCOMING_LAUNCHES -> loadUpcomingLaunches()
        else -> loadAllLaunches()
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

    fun onDestroyDelegate() {
        mComposite.clear()
    }

    private fun Single<List<Launch>>.deliverResult() {
        observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doFinally { viewState.hideProgress() }
                .subscribe({
                    viewState.setData(it)
                }, {
                    viewState.showError(it.message.toString())
                })
                .apply { mComposite.add(this) }
    }

    fun onActionGuideClick() {
        viewState.showGuideScreen()
    }
}