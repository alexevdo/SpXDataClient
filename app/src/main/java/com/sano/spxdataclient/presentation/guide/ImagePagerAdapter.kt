package com.sano.spxdataclient.presentation.guide

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.sano.spacexlaunches.R

class ImagePagerAdapter(context: Context, @DrawableRes val drawableList: List<Int>): PagerAdapter() {

    val layoutInflater = LayoutInflater.from(context)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return drawableList.size
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val imageLayout = layoutInflater.inflate(R.layout.fragment_guide_item, collection, false) as ImageView
        imageLayout.setImageResource(drawableList[position])
        collection.addView(imageLayout)

        return imageLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}