package com.sano.spxdataclient.presentation.launch

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.model.Launch
import com.sano.spxdataclient.utils.DateFormatManager

class LaunchViewHolder(view: View, val listener: ((Int) -> Unit)?) : RecyclerView.ViewHolder(view) {

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
        itemView.setOnClickListener { listener?.invoke(adapterPosition) }
    }

    private fun getLaunchStateDrawable(context: Context, launch: Launch): Drawable =
            if (launch.isUpcoming) ContextCompat.getDrawable(context, R.drawable.ic_arrow_up)!!
            else {
                ContextCompat.getDrawable(context, R.drawable.ic_circle)!!.mutate()
                        .apply {
                            if (launch.isSuccess) setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
                            else setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                        }
            }

}