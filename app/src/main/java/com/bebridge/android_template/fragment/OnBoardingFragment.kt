package com.bebridge.android_template.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.bebridge.android_template.R
import com.bebridge.android_template.databinding.FragmentOnBoardingBinding
import com.bebridge.android_template.db.model.SlideModel
import com.bebridge.android_template.util.PreferenceController
import com.bebridge.android_template.util.SlidingImageAdapter
import com.viewpagerindicator.CirclePageIndicator
import java.util.*


class OnBoardingFragment : Fragment() {


    private val pc: PreferenceController = PreferenceController.instance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        binding.login.setOnClickListener {
            pc.setIsOnBoardingDone(true)
            val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewPager()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun populateSlideModelList(): ArrayList<SlideModel> {

        val myImageList = intArrayOf(
            R.drawable.t_one,
            R.drawable.t_two,
            R.drawable.t_three
        )


        val slideTitleList = arrayOf(
            requireContext().getString(R.string.on_boarding_measure_title),
            requireContext().getString(R.string.on_boarding_record_title),
            requireContext().getString(R.string.on_boarding_manage_title)
        )

        val slideDescriptionList = arrayOf(
            requireContext().getString(R.string.on_boarding_measure_description),
            requireContext().getString(R.string.on_boarding_record_description),
            requireContext().getString(R.string.on_boarding_manage_description)
        )

        val list = ArrayList<SlideModel>()

        for (i in 0..2) {
            val slideModel = SlideModel()
            slideModel.setSlideImage(myImageList[i])
            slideModel.setSlideTitle(slideTitleList[i])
            slideModel.setSlideDescription(slideDescriptionList[i])
            list.add(slideModel)
        }

        return list
    }

    private fun setupViewPager() {

        val mPager = requireView().findViewById(R.id.pager) as ViewPager
        mPager.adapter = SlidingImageAdapter(layoutInflater, populateSlideModelList())


        val indicator = requireView().findViewById<CirclePageIndicator>(R.id.indicator)
        indicator.setViewPager(mPager)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.radius = 5 * density

    }

    companion object {
        private var currentPage = 0
    }


}
