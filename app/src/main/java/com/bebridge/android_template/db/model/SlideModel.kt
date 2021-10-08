package com.bebridge.android_template.db.model

class SlideModel {

    private var slideImage: Int = 0
    private var slideTitle: String? = null
    private var slideDescription: String? = null

    fun getSlideImage(): Int {
        return slideImage
    }

    fun setSlideImage(slideImage: Int) {
        this.slideImage = slideImage
    }

    fun getSlideTitle(): String? {
        return slideTitle
    }

    fun setSlideTitle(slideTitle: String?) {
        this.slideTitle = slideTitle
    }


    fun getSlideDescription(): String? {
        return slideDescription
    }

    fun setSlideDescription(slideDescription: String?) {
        this.slideDescription = slideDescription
    }


}