package com.sano.spxdataclient.launch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.api.ApiUtils
import com.sano.spxdataclient.model.Launch
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_launches.*
import org.jetbrains.anko.toast

class LaunchesFragment : Fragment() {

    private lateinit var adapter: LaunchAdapter
    private val composite: CompositeDisposable = CompositeDisposable()
    private var mMenuId: Int = R.id.action_all

    companion object {
        fun newInstance(): LaunchesFragment {
            val fragment = LaunchesFragment()
            fragment.arguments = Bundle()
                    .apply { }
            return fragment;
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launches, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        adapter = LaunchAdapter()
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
            R.id.action_latest -> loadLatestLaunches()
            R.id.action_next -> loadNextLaunch()
            R.id.action_past -> loadPastLaunches()
            R.id.action_upcoming -> loadUpcomingLaunches()
            R.id.action_all -> loadAllLaunches()
        }
    }

    private fun loadLatestLaunches() {
        loadLaunches(ApiUtils.getApiService().getLatestLaunches().map { listOf(it) })
    }

    private fun loadNextLaunch() {
        loadLaunches(ApiUtils.getApiService().getNextLaunch().map { listOf(it) })
    }

    private fun loadPastLaunches() {
        loadLaunches(ApiUtils.getApiService().getPastLaunches())
    }

    private fun loadUpcomingLaunches() {
        loadLaunches(ApiUtils.getApiService().getUpcomingLaunches())
    }

    private fun loadAllLaunches() {
        loadLaunches(ApiUtils.getApiService().getAllLaunches())
    }

    private fun loadLaunches(launches: Single<List<Launch>>) {
        launches.subscribeOn(Schedulers.io())
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
}