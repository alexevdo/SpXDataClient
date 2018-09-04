package com.sano.spxdataclient.presentation.launch

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.Storage
import com.sano.spxdataclient.api.ApiUtils
import com.sano.spxdataclient.model.Launch
import com.sano.spxdataclient.presentation.launchDetails.LaunchDetailsFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_launches.*
import org.jetbrains.anko.toast

class LaunchesFragment : Fragment(), LaunchesView {

    companion object {
        fun newInstance() = LaunchesFragment()
    }

    private lateinit var adapter: LaunchAdapter
    private lateinit var mPresenter: LaunchesPresenter

    override var isShowProgress: Boolean
        get() = refresher.isRefreshing
        set(value) {
            refresher.isRefreshing = value
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launches, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is Storage.StorageOwner) {
            mPresenter = LaunchesPresenter(this, (context as Storage.StorageOwner).obtainStorage())
        } else {
            throw IllegalStateException("Activity must be Storage.StorageOwner child")
        }
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

        mPresenter.loadLaunches()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.launches, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mPresenter.menuId = item.itemId
        return true
    }

    override fun onDestroyView() {
        mPresenter.onDestroy()
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
}