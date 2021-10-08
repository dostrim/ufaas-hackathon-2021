package com.bebridge.android_template.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bebridge.android_template.CustomApplication
import com.bebridge.android_template.api.response.ErrorResponse
import com.bebridge.android_template.api.response.LoginUser
import com.bebridge.android_template.databinding.FragmentLoginBinding
import com.bebridge.android_template.viewModel.LoginViewModel
import javax.inject.Inject

class LoginFragment : Fragment(), LoginNavigator {

  @Inject
  lateinit var viewModel: LoginViewModel

  private val args: LoginFragmentArgs by navArgs()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    (activity?.application as CustomApplication).getComponent().inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val binding = FragmentLoginBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel
    viewModel.onCreateView(this)

    return binding.root
  }

  override fun onDestroyView() {
    viewModel.onDestroyView()
    super.onDestroyView()
  }

  override fun onSuccessLogin() {
      val action = LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment()
      findNavController().navigate(action)
  }

  override fun transitionToSnsSignUp(loginUser: LoginUser) {
  }

  override fun onErrorLogin(errorResponse: ErrorResponse) {
    Toast.makeText(context, errorResponse.messageString(requireContext()), Toast.LENGTH_SHORT)
      .show()
  }

  companion object {
    const val REQUEST_LOGIN_GOOGLE = 1000

    const val REQUEST_LOGIN_LINE = 1001
  }

}

interface LoginNavigator {

  fun onSuccessLogin()

  fun transitionToSnsSignUp(loginUser: LoginUser)

  fun onErrorLogin(errorResponse: ErrorResponse)
}
