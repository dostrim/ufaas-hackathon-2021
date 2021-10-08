package com.bebridge.android_template.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bebridge.android_template.BuildConfig
import com.bebridge.android_template.CustomApplication
import com.bebridge.android_template.MainActivity
import com.bebridge.android_template.R
import com.bebridge.android_template.api.response.ErrorResponse
import com.bebridge.android_template.databinding.FragmentMenuBinding
import com.bebridge.android_template.util.PreferenceController
import com.bebridge.android_template.viewModel.MenuViewModel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import javax.inject.Inject

class MenuFragment : Fragment(), MenuNavigator {

  @Inject
  lateinit var viewModel: MenuViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (activity?.application as CustomApplication).getComponent().inject(this)
  }


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    val binding = FragmentMenuBinding.inflate(inflater, container, false)

    binding.backButtonBelow.setOnClickListener {
      activity?.onBackPressed()
    }

    binding.backButton.setOnClickListener {
      activity?.onBackPressed()
    }

    binding.termesLayout.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToTermsFragment()
            findNavController().navigate(action)
    }

    binding.privacyLayout.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToPrivacyFragment()
            findNavController().navigate(action)
    }

    binding.shareLayout.setOnClickListener {
      val defaultText = getString(R.string.share_app_message,
        getString(R.string.app_name),
        context?.packageName)

      val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, defaultText)
        type = "text/plain"
      }
      startActivity(Intent.createChooser(intent, getString(R.string.menu_share_app)))
    }

    binding.logoutLayout.setOnClickListener {

      AlertDialog.Builder(context)
        .setTitle(getString(R.string.menu_logout_dialog_title))
        .setMessage(getString(R.string.menu_logout_dialog_message))
        .setPositiveButton(R.string.menu_logout_dialog_positive) { _, _ ->
          PreferenceController.instance.logout()
          val action = MenuFragmentDirections.actionMenuFragmentToHomeFragment()
          findNavController().navigate(action)
        }
        .setNegativeButton(R.string.menu_logout_dialog_negative) { dialog, _ ->
          dialog.dismiss()
        }
        .show()

    }

    binding.licenseLayout.setOnClickListener {
      startActivity(Intent(context, OssLicensesMenuActivity::class.java).apply {
        putExtra("title", getString(R.string.menu_license))
      })
    }

    binding.versionTextView.text = BuildConfig.VERSION_NAME

    binding.viewModel = viewModel
    viewModel.onCreateView(this)


    return binding.root
  }

  override fun onDestroy() {
    viewModel.onDestroy()
    super.onDestroy()
  }

  override fun onError(errorReponse: ErrorResponse) {
    Toast.makeText(
      context,
      errorReponse.messageString(this.requireContext()),
      Toast.LENGTH_SHORT
    ).show()
  }
}

interface MenuNavigator {
  fun onError(errorReponse: ErrorResponse)
}
