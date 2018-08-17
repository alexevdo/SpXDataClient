package com.sano.spxdataclient.launch

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.model.Launch
import com.sano.spxdataclient.utils.DateFormatManager
import com.sano.spxdataclient.utils.getLaunchStateDrawable

class LaunchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val logo: ImageView = itemView.findViewById(R.id.iv_logo)
    private val launchName: TextView = itemView.findViewById(R.id.tv_launch_name)
    private val rocketName: TextView = itemView.findViewById(R.id.tv_rocket_name)
    private val date: TextView = itemView.findViewById(R.id.tv_launch_date)
    private val status: ImageView = itemView.findViewById(R.id.iv_status)

    fun bind(launch: Launch) {
        logo.setImageResource(R.drawable.spacex_logo)
        launchName.text = launch.missionName
        rocketName.text = launch.rocket.rocketName
        date.text = DateFormatManager.formatDate(launch.timestamp)
        status.setImageDrawable(getLaunchStateDrawable(itemView.context, launch))
    }

}