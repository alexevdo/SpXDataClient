package com.sano.spxdataclient.presentation.launchDetails

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.model.Launch
import com.sano.spxdataclient.utils.DateFormatManager
import kotlinx.android.synthetic.main.fragment_launch_details.*

class LaunchDetailsFragment : Fragment() {
    companion object {
        const val LAUNCH_EXTRA_KEY = "LAUNCH_EXTRA_KEY";

        fun newInstance(launch: Launch): LaunchDetailsFragment {
            val fragment = LaunchDetailsFragment()
            fragment.arguments = Bundle()
                    .apply { putParcelable(LAUNCH_EXTRA_KEY, launch) }

            return fragment;
        }
    }

    private lateinit var mAdapter: LinkAdapter
    private lateinit var mLaunch: Launch

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launch_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLaunch = arguments.getParcelableOrThrow(LAUNCH_EXTRA_KEY)

        iv_logo.setImageResource(R.drawable.spacex_logo)
        tv_launch_name.text = mLaunch.missionName
        tv_rocket_name.text = mLaunch.rocket.rocketName
        tv_launch_date.text = DateFormatManager.formatDate(mLaunch.timestamp)
        tv_status.text = getLaunchStatus(mLaunch)
        tv_details.text = mLaunch.details
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mAdapter = LinkAdapter()
        mAdapter.setData(mLaunch.links.getLinks())
        recycler_links.adapter = mAdapter
        recycler_links.layoutManager = LinearLayoutManager(context)

        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) = menu.clear()

    private fun getLaunchStatus(launch: Launch): String {
        val statusId = when {
            launch.isUpcoming -> R.string.upcoming
            launch.isSuccess -> R.string.success
            else -> R.string.fail
        }

        return getString(R.string.status_with_value, getString(statusId))
    }

    private fun <T : Parcelable> Bundle?.getParcelableOrThrow(key: String): T {
        if (this == null || getParcelable<T>(key) == null) {
            throw IllegalArgumentException("Provide $key extra")
        }

        return getParcelable(key)!!
    }
}