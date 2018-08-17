package com.sano.spxdataclient.launchDetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.model.LinkType

class LinkAdapter: RecyclerView.Adapter<LinkViewHolder>() {

    private val mData: MutableList<Pair<@LinkType Int, String>> = ArrayList()

    override fun onCreateViewHolder(container: ViewGroup, type: Int): LinkViewHolder {
        val view = LayoutInflater.from(container.context)
                .inflate(R.layout.list_item_link, container, false)

        return LinkViewHolder(view)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(viewHolder: LinkViewHolder, position: Int) {
        viewHolder.bind(mData[position])
    }

    fun setData(links: List<Pair<@LinkType Int, String>>) {
        mData.clear()
        mData.addAll(links)
        notifyDataSetChanged()
    }
}