package com.sano.spxdataclient.presentation.launch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.model.Launch
import com.sano.spxdataclient.utils.DateFormatManager

class LaunchAdapter: RecyclerView.Adapter<LaunchViewHolder>() {

    private val mData: MutableList<Launch> = ArrayList()
    private var mOnClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LaunchViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.list_item_launch, viewGroup, false)
        return LaunchViewHolder(view, mOnClickListener)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(viewHolder: LaunchViewHolder, position: Int) =
            viewHolder.bind(mData[position])

    fun setData(launches: List<Launch>) {
        val sortedLaunches = launches.sortedByDescending { DateFormatManager.getDate(it.timestamp) }

        mData.clear()
        mData.addAll(sortedLaunches)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: ((Int) -> Unit)?) {
        mOnClickListener = listener
    }

    fun getItem(position: Int): Launch {
        return mData[position]
    }
}