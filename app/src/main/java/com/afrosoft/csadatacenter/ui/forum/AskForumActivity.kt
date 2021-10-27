package com.afrosoft.csadatacenter.ui.forum

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.afrosoft.csadatacenter.R
import com.afrosoft.csadatacenter.databinding.ActivityAskForumBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import com.squareup.picasso.Picasso
import java.io.File

class AskForumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAskForumBinding

    private val TAG = "AskForumActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAskForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Ask Community"

        binding.picture.visibility = View.GONE
        binding.buttonImagePicker.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .compress(1024)
                .maxResultSize(400, 400)
                .saveDir(File(getExternalFilesDir("Pics")!!.path))
                .createIntent { intent ->
                    startForResult(intent){ result ->
                        val uri: Uri = result.data?.data!!

                        Picasso.get().load(File(uri.path)).into(binding.picture)
                        binding.picture.visibility = View.VISIBLE
                        Log.d(TAG, "onViewCreated: ")
                    }
                }
        }

    }
}