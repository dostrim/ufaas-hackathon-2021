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
import com.bebridge.android_template.databinding.FragmentSignUpBinding
import com.bebridge.android_template.viewModel.SignUpViewModel
import javax.inject.Inject

class SignUpFragment : Fragment(), SignUpNavigator {

  @Inject
  lateinit var viewModel: SignUpViewModel
  var binding: FragmentSignUpBinding? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    (activity?.application as CustomApplication).getComponent().inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentSignUpBinding.inflate(inflater, container, false)
    binding?.viewModel = viewModel
    viewModel.onCreateView(this)

    binding?.backButton?.setOnClickListener {
      activity?.onBackPressed()
    }

    return binding?.root
  }

  override fun onError(errorResponse: ErrorResponse) {
    Toast.makeText(
      context,
      errorResponse.messageString(this.requireContext()),
      Toast.LENGTH_SHORT
    ).show()
  }

  override fun onSuccessSignup() {
    val action = SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
    findNavController().navigate(action)

  }

  override fun onDestroy() {
    viewModel.onDestroy()
    super.onDestroy()
  }

}

interface SignUpNavigator {
  fun onError(errorResponse: ErrorResponse)
  fun onSuccessSignup()
}
