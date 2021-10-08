package com.bebridge.android_template.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bebridge.android_template.CustomApplication
import com.bebridge.android_template.api.response.ErrorResponse
import com.bebridge.android_template.databinding.FragmentHomeBinding
import com.bebridge.android_template.util.DebugManager
import com.bebridge.android_template.util.PreferenceController
import com.bebridge.android_template.viewModel.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment(), HomeNavigator{

  var binding: FragmentHomeBinding? = null

  @Inject
  lateinit var viewModel: HomeViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    (activity?.application as CustomApplication).getComponent().inject(this)

    DebugManager.instance.log(this, "onCreate")
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = FragmentHomeBinding.inflate(inflater, container, false)
    binding?.viewModel = viewModel

    binding?.profileImage?.setOnClickListener {
      val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
      findNavController().navigate(action)
    }

    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    if (!PreferenceController.instance.getIsOnBoardingDone() && (PreferenceController.instance.getAccessToken() == null)) {
      val action = HomeFragmentDirections.actionHomeFragmentToOnBoardingFragment()
      findNavController().navigate(action)
    } else if (!PreferenceController.instance.getIsUserDataDone()) {
      val action = HomeFragmentDirections.actionHomeFragmentToSignUpFragment()
      findNavController().navigate(action)
    }
  }

  override fun onDestroyView() {
    viewModel.onDestroyView()
    binding = null
    super.onDestroyView()
  }

  override fun onRefreshed() {
  }

  override fun onError(errorResponse: ErrorResponse) {
    context?.let {
      Toast.makeText(it, errorResponse.messageString(it), Toast.LENGTH_SHORT).show()
    }
  }

}

interface HomeNavigator {
  fun onRefreshed()
  fun onError(errorResponse: ErrorResponse)
}
