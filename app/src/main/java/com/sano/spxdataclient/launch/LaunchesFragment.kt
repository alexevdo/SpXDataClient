package com.sano.spxdataclient.launch

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.Storage
import com.sano.spxdataclient.api.ApiUtils
import com.sano.spxdataclient.launchDetails.LaunchDetailsFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_launches.*
import org.jetbrains.anko.toast

class LaunchesFragment : Fragment() {

    private lateinit var adapter: LaunchAdapter
    private lateinit var composite: CompositeDisposable
    private var mMenuId: Int = R.id.action_all
    private lateinit var mStorage: Storage

    companion object {
        fun newInstance() = LaunchesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        composite = CompositeDisposable()
        return inflater.inflate(R.layout.fragment_launches, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is Storage.StorageOwner) {
            mStorage = (context as Storage.StorageOwner).obtainStorage()
        } else {
            throw IllegalStateException("Activity must be Storage.StorageOwner child")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        adapter = LaunchAdapter()
        adapter.setOnClickListener {
            fragmentManager
                    ?.beginTransaction()
                    ?.add(R.id.container, LaunchDetailsFragment.newInstance(adapter.getItem(it)))
                    ?.addToBackStack(null)
                    ?.commit()
        }

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        refresher.setOnRefreshListener { loadChoosedLaunches() }

        loadChoosedLaunches()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.launches, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mMenuId = item.itemId
        loadChoosedLaunches()
        return true
    }

    override fun onDestroyView() {
        composite.dispose()
        super.onDestroyView()
    }

    private fun loadChoosedLaunches() {
        when (mMenuId) {
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
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { refresher.isRefreshing = true }
                .doFinally { refresher.isRefreshing = false }
                .subscribe({
                    adapter.setData(it)
                }, {
                    context?.toast(it.message.toString())
                })
                .apply { composite.add(this) }
    }

    private fun loadNextLaunch() {
        ApiUtils.getApiService().getNextLaunch().map { listOf(it) }
                .subscribeOn(Schedulers.io())
                .onErrorReturn {
                    if(ApiUtils.NETWORK_EXCEPTIONS.contains(it::class))
                        return@onErrorReturn mStorage.getNextLaunch()
                    null
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { refresher.isRefreshing = true }
                .doFinally { refresher.isRefreshing = false }
                .subscribe({
                    adapter.setData(it)
                }, {
                    context?.toast(it.message.toString())
                })
                .apply { composite.add(this) }
    }

    private fun loadPastLaunches() {
        ApiUtils.getApiService().getPastLaunches()
                .subscribeOn(Schedulers.io())
                .onErrorReturn {
                    if(ApiUtils.NETWORK_EXCEPTIONS.contains(it::class))
                        return@onErrorReturn mStorage.getPastLaunches()
                    null
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { refresher.isRefreshing = true }
                .doFinally { refresher.isRefreshing = false }
                .subscribe({
                    adapter.setData(it)
                }, {
                    context?.toast(it.message.toString())
                })
                .apply { composite.add(this) }
    }

    private fun loadUpcomingLaunches() {
        ApiUtils.getApiService().getUpcomingLaunches()
                .subscribeOn(Schedulers.io())
                .onErrorReturn {
                    if(ApiUtils.NETWORK_EXCEPTIONS.contains(it::class))
                        return@onErrorReturn mStorage.getUpcomingLaunches()
                    null
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { refresher.isRefreshing = true }
                .doFinally { refresher.isRefreshing = false }
                .subscribe({
                    adapter.setData(it)
                }, {
                    context?.toast(it.message.toString())
                })
                .apply { composite.add(this) }
    }

    private fun loadAllLaunches() {
        ApiUtils.getApiService().getAllLaunches()
                .subscribeOn(Schedulers.io())
                .doOnSuccess { mStorage.overwriteLaunches(it) }
                .onErrorReturn {
                    if(ApiUtils.NETWORK_EXCEPTIONS.contains(it::class))
                        return@onErrorReturn mStorage.getAllLaunches()
                    null
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { refresher.isRefreshing = true }
                .doFinally { refresher.isRefreshing = false }
                .subscribe({
                    adapter.setData(it)
                }, {
                    context?.toast(it.message.toString())
                    it.printStackTrace()
                })
                .apply { composite.add(this) }
    }
}