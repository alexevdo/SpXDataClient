package com.sano.spxdataclient.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.model.Launch

fun getLaunchStateDrawable(context: Context, launch: Launch): Drawable =
        if (launch.isUpcoming) ContextCompat.getDrawable(context, R.drawable.ic_arrow_up)!!
        else {
            ContextCompat.getDrawable(context, R.drawable.ic_circle)!!.mutate()
                    .apply {
                        if (launch.isSuccess) setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
                        else setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                    }
        }

