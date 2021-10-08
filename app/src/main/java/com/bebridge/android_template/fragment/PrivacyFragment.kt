package com.bebridge.android_template.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.HttpAuthHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bebridge.android_template.R
import com.bebridge.android_template.databinding.FragmentPrivacyBinding

class PrivacyFragment : androidx.fragment.app.Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    val binding = FragmentPrivacyBinding.inflate(inflater, container, false)

    binding.backButton.setOnClickListener {
      activity?.onBackPressed()
    }

    binding.webView.apply {
      webViewClient = object : WebViewClient() {

        override fun onPageStarted(
          view: WebView?,
          url: String?,
          favicon: Bitmap?
        ) {
          binding.progressBar.visibility = View.VISIBLE
          super.onPageStarted(view, url, favicon)
        }

        override fun onReceivedHttpAuthRequest(
          view: WebView?,
          handler: HttpAuthHandler?,
          host: String?,
          realm: String?
        ) {
          handler?.proceed(
            getString(R.string.digest_auth_user), getString(R.string.digest_auth_pass)
          )
        }

        override fun onPageFinished(
          view: WebView?,
          url: String?
        ) {
          binding.progressBar.visibility = View.GONE
          super.onPageFinished(view, url)
        }
      }
      settings.javaScriptEnabled = true
      loadUrl(getString(R.string.base_path) + getString(R.string.privacy_url))
    }

    return binding.root
  }
}