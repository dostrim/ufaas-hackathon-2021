package com.bebridge.android_template.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bebridge.android_template.CustomApplication
import com.bebridge.android_template.R
import com.bebridge.android_template.api.response.ErrorResponse
import com.bebridge.android_template.databinding.FragmentChangePasswordBinding
import com.bebridge.android_template.viewModel.ChangePasswordViewModel
import javax.inject.Inject

class ChangePasswordFragment : Fragment(), ChangePasswordNavigator {

    @Inject
    lateinit var viewModel: ChangePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as CustomApplication).getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        viewModel.onCreateView(this)

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }

    override fun onError(errorResponse: ErrorResponse) {
        Toast.makeText(context, errorResponse.messageString(this.requireContext()), Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessChangePassword() {
        Toast.makeText(context, getString(R.string.change_password_completed), Toast.LENGTH_SHORT).show()
        activity?.onBackPressed()
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

}

interface ChangePasswordNavigator {
    fun onError(errorResponse: ErrorResponse)
    fun onSuccessChangePassword()
}
