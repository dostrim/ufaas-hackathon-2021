package com.bebridge.android_template.util

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bebridge.android_template.R
import com.bebridge.android_template.db.model.SlideModel

class SlidingImageAdapter(
    private val inflater: LayoutInflater,
    private val slideModelArrayList: ArrayList<SlideModel>
) : PagerAdapter() {

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return slideModelArrayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.image_slide_layout, view, false)!!

        val slideImage = imageLayout.findViewById(R.id.image) as ImageView
        val slideTitle = imageLayout.findViewById(R.id.slide_title) as TextView
        val slideDescription = imageLayout.findViewById(R.id.slide_description) as TextView

        slideImage.setImageResource(slideModelArrayList[position].getSlideImage())
        slideTitle.text = slideModelArrayList[position].getSlideTitle()
        slideDescription.text = slideModelArrayList[position].getSlideDescription()

        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }

}