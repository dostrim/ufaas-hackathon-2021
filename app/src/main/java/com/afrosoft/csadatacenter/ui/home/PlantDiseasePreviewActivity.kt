package com.afrosoft.csadatacenter.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afrosoft.csadatacenter.R
import com.squareup.picasso.Picasso
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.afrosoft.csadatacenter.databinding.ActivityPlantDiseasePreviewBinding
import com.afrosoft.csadatacenter.network.NetworkClient
import com.bumptech.glide.Glide

class PlantDiseasePreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantDiseasePreviewBinding
    var image: String? = null
    var name: String? = null
    var desc: String? = null
    var cause: String? = null
    var symptom: String? = null
    var treatment: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlantDiseasePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        image = intent.extras!!.getString("image")
        name = intent.extras!!.getString("name")
        desc = intent.extras!!.getString("description")
        cause = intent.extras!!.getString("cause")
        symptom = intent.extras!!.getString("symptom")
        treatment = intent.extras!!.getString("treatment")

        Glide.with(this).asBitmap().load("${NetworkClient().baseUrl}PlantDiseaseImages/${image}")
            .into(binding.diseaseImagePreview)

        //Picasso.get().load(image).into(binding.diseaseImagePreview)
        binding.diseaseCausePreview.setText(cause)
        binding.diseaseDescriptionPreview.setText(desc)
        binding.diseaseNamePreview.setText(name)
        binding.diseaseSymptomPreview.setText(symptom)
        binding.diseaseTreatmentPreview.setText(treatment)
        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
        val appBarLayout = findViewById<AppBarLayout>(R.id.app_bar)
        appBarLayout.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = name // plant disease Name
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title =
                        " " //careful there should a space between double quote otherwise it wont work
                    isShow = false
                }
            }
        })
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val playStore =
                "https://play.google.com/store/apps/details?id=" + applicationContext.packageName
            val shareBody = "Download Our APp Application at : \n$playStore"
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(shareIntent, "Share via:"))
        }
    }
}