package com.sano.spxdataclient.presentation.launch

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.Storage
import com.sano.spxdataclient.model.Launch
import com.sano.spxdataclient.presentation.guide.GuideFragment
import com.sano.spxdataclient.presentation.launchDetails.LaunchDetailsFragment
import kotlinx.android.synthetic.main.fragment_launches.*
import org.jetbrains.anko.toast

class LaunchesFragment : MvpAppCompatFragment(), LaunchesView {

    companion object {
        fun newInstance() = LaunchesFragment()
    }

    private lateinit var adapter: LaunchAdapter

    @InjectPresenter
    lateinit var mPresenter: LaunchesPresenter

    @ProvidePresenter
    fun providePresenter(): LaunchesPresenter {
        if (context is Storage.StorageOwner) {
            return LaunchesPresenter((context as Storage.StorageOwner).obtainStorage())
        } else {
            throw IllegalStateException("Activity must implements Storage.StorageOwner")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launches, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        adapter = LaunchAdapter()
        adapter.setOnClickListener {
            mPresenter.onListItemClicked(adapter.getItem(it))
        }

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        refresher.setOnRefreshListener { mPresenter.loadLaunches() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.launches, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_guide) {
            fragmentManager
                    ?.beginTransaction()
                    ?.add(R.id.container, GuideFragment.newInstance())
                    ?.addToBackStack(null)
                    ?.commit()

            return true
        }

        mPresenter.menuId = when(item.itemId) {
            R.id.action_latest -> LATEST_LAUNCH
            R.id.action_next -> NEXT_LAUNCH
            R.id.action_past -> PAST_LAUNCHES
            R.id.action_upcoming -> UPCOMING_LAUNCHES
            else -> ALL_LAUNCHES
        }

        return true
    }

    override fun onDestroyView() {
        mPresenter.onDestroyDelegate()
        super.onDestroyView()
    }

    override fun setData(launches: List<Launch>) = adapter.setData(launches)

    override fun showError(message: String) {
        context?.toast(message)
    }

    override fun showLaunchDetailsScreen(launch: Launch) {
        fragmentManager
                ?.beginTransaction()
                ?.add(R.id.container, LaunchDetailsFragment.newInstance(launch))
                ?.addToBackStack(null)
                ?.commit()
    }

    override fun showProgress() {
        refresher.isRefreshing = true
    }

    override fun hideProgress() {
        refresher.isRefreshing = false
    }
}