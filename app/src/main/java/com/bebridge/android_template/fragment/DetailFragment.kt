package com.bebridge.android_template.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bebridge.android_template.CustomApplication
import com.bebridge.android_template.R
import com.bebridge.android_template.api.response.ErrorResponse
import com.bebridge.android_template.databinding.FragmentDetailBinding
import com.bebridge.android_template.viewModel.DetailViewModel
import javax.inject.Inject

class DetailFragment : Fragment(), DetailNavigator {

  var binding: FragmentDetailBinding? = null

  @Inject
  lateinit var viewModel: DetailViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    (activity?.application as CustomApplication).getComponent().inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = FragmentDetailBinding.inflate(inflater, container, false)
    binding!!.viewModel = viewModel

    binding!!.profileImage.setOnClickListener {
      findNavController().navigate(R.id.action_global_homeFragment)
    }


    return binding!!.root
  }


  override fun onError(errorResponse: ErrorResponse) {
    context?.let {
      Toast.makeText(it, errorResponse.messageString(it), Toast.LENGTH_SHORT).show()
    }
  }
}

interface DetailNavigator {
  fun onError(errorResponse: ErrorResponse)
}
