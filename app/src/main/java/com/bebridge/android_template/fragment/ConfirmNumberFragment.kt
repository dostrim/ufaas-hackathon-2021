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
import com.bebridge.android_template.databinding.FragmentConfirmNumberBinding
import com.bebridge.android_template.viewModel.ConfirmNumberViewModel
import javax.inject.Inject

class ConfirmNumberFragment : Fragment(), ResetPasswordNavigator {

  @Inject
  lateinit var viewModel: ConfirmNumberViewModel

  var binding: FragmentConfirmNumberBinding? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    (activity?.application as CustomApplication).getComponent().inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentConfirmNumberBinding.inflate(inflater, container, false)
    binding?.viewModel = viewModel
    viewModel.onCreateView(this)

    return binding?.root
  }

  override fun onError(errorResponse: ErrorResponse) {
    Toast.makeText(context, errorResponse.messageString(this.requireContext()), Toast.LENGTH_SHORT)
      .show()
  }

  override fun onSuccessConfirmPassword() {
    val action = ConfirmNumberFragmentDirections.actionConfirmNumberFragmentToHomeFragment()
    findNavController().navigate(action)
  }

  override fun onDestroy() {
    viewModel.onDestroyView()
    super.onDestroy()
  }

}

interface ResetPasswordNavigator {
  fun onError(errorResponse: ErrorResponse)
  fun onSuccessConfirmPassword()
}
