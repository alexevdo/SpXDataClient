package com.sano.spxdataclient.presentation.guide

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.sano.spacexlaunches.R
import com.sano.spacexlaunches.R.color.*
import com.sano.spacexlaunches.R.drawable.*
import com.sano.spacexlaunches.R.string.*
import kotlinx.android.synthetic.main.fragment_guide.*
import kotlin.math.max

class GuideFragment: Fragment() {

    companion object {
        fun newInstance() = GuideFragment()
    }

    enum class Guide(@DrawableRes val imageRes: Int, @DrawableRes val numRes: Int, @ColorRes val colorRes: Int,
                     @StringRes val title: Int, @StringRes val description: Int, val isGotIt: Boolean = false) {

        GUIDE_1(R.drawable.guide_1, guide_num_01, black, guide_title_1, guide_description_1),
        GUIDE_2(R.drawable.guide_2, guide_num_02, blue, guide_title_2, guide_description_2),
        GUIDE_3(R.drawable.guide_3, guide_num_03, red, guide_title_3, guide_description_3, true);

        companion object {
            @DrawableRes val images: List<Int> = Guide.values().map { it.imageRes }
        }

        fun getColor(context: Context): Int {
            return ContextCompat.getColor(context, colorRes)
        }
    }

    private var mainGuideColor: Int = 0
    private var nextGuideColor: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = ImagePagerAdapter(requireContext(), Guide.images)

        val partialWidth = resources.getDimensionPixelSize(R.dimen.guide_page_padding)

        viewPager.clipToPadding = false
        viewPager.setPadding(partialWidth, 0, partialWidth, 0)

        nextButton.setOnClickListener {
            if(viewPager.currentItem != viewPager.childCount - 1)
            viewPager.currentItem = viewPager.currentItem + 1

        }
        gotItButton.setOnClickListener { fragmentManager?.popBackStack() }

        viewPager.addOnPageScrolledListener { position: Int, percent: Float -> onPageScrolled(position, percent) }

        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) = menu.clear()

    private fun onPageScrolled(position: Int, percent: Float) {
        val mainGuide: Guide = Guide.values()[position]
        val nextGuide: Guide? = if (position + 1 < Guide.values().size) Guide.values()[position + 1] else null

        val isEven = position % 2 == 0

        val mainNumberView = if (isEven) numberImage else numberImage2
        val nextNumberView = if (isEven) numberImage2 else numberImage

        val mainTitleView = if (isEven) title else title2
        val nextTitleView = if (isEven) title2 else title

        val mainDescription = if (isEven) description else description2
        val nextDescription = if (isEven) description2 else description

        if(numberImage.tag != mainGuide) {
            numberImage.tag = mainGuide

            switchZ(mainNumberView, nextNumberView)

            mainNumberView.setImageResource(mainGuide.numRes)
            mainTitleView.text = getString(mainGuide.title)
            mainDescription.text = getString(mainGuide.description)
            mainGuideColor = ContextCompat.getColor(requireContext(), mainGuide.colorRes)

            if (nextGuide != null) {
                nextNumberView.setImageResource(nextGuide.numRes)
                nextTitleView.text = getString(nextGuide.title)
                nextDescription.text = getString(nextGuide.description)
                nextGuideColor = ContextCompat.getColor(requireContext(), nextGuide.colorRes)
            }
        }

        mainNumberView.alpha = 1 - percent
        nextNumberView.alpha = percent

        mainTitleView.alpha = 1 - percent
        nextTitleView.alpha = percent

        mainDescription.alpha = 1 - percent
        nextDescription.alpha = percent

        val backgroundColor = getColor(mainGuideColor, nextGuideColor, percent)

        rootLayout.setBackgroundColor(backgroundColor)

        if(mainGuide.isGotIt) {
            nextButton.alpha = 0f
            gotItButton.alpha = 1f
            nextButton.visibility = GONE
        } else if (nextGuide != null && nextGuide.isGotIt) {
            nextButton.alpha = 1 - percent
            gotItButton.alpha = percent
            gotItButton.setTextColor(backgroundColor)
            nextButton.visibility = VISIBLE
        } else {
            gotItButton.alpha = 0f
            nextButton.alpha = 1f
            nextButton.visibility = VISIBLE
        }
    }

    @ColorInt
    private fun getColor(mainColor: Int, additionalColor: Int, percent: Float) =
            Color.rgb(
                    (Color.red(mainColor) + (Color.red(additionalColor) - Color.red(mainColor)) * percent).toInt(),
                    (Color.green(mainColor) + (Color.green(additionalColor) - Color.green(mainColor)) * percent).toInt(),
                    (Color.blue(mainColor) + (Color.blue(additionalColor) - Color.blue(mainColor)) * percent).toInt())

    private fun switchZ(mainView: View, nextView: View) {
        val tmpZ = ViewCompat.getZ(mainView)
        ViewCompat.setZ(mainView, max(tmpZ, ViewCompat.getZ(nextView)))
        ViewCompat.setZ(nextView, ViewCompat.getZ(mainView) - 1)
    }

    private fun ViewPager.addOnPageScrolledListener(onPageScrolled: (Int, Float) -> Unit) {
        this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                onPageScrolled(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }
}