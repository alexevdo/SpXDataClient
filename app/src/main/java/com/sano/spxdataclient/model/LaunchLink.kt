package com.sano.spxdataclient.model

import android.support.annotation.IntDef


const val REDDIT = 0
const val PDF = 1
const val LINK = 2
const val WIKIPEDIA = 3
const val VIDEO = 4

@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
@IntDef(REDDIT, PDF, LINK, WIKIPEDIA, VIDEO)
annotation class LinkType

data class LaunchLink(@LinkType val type: Int, val url: String)