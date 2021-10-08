package com.bebridge.android_template.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bebridge.android_template.CustomApplication
import com.bebridge.android_template.R
import com.bebridge.android_template.api.response.ErrorResponse
import com.bebridge.android_template.databinding.FragmentEditProfileBinding
import com.bebridge.android_template.db.entity.GenderEntity
import com.bebridge.android_template.db.entity.UserEntity
import com.bebridge.android_template.viewModel.EditProfileViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import java.util.*
import javax.inject.Inject

class EditProfileFragment : Fragment(), EditProfileNavigator {

    @Inject
    lateinit var viewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as CustomApplication).getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        viewModel.onCreateView(this)

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.genderEditText.keyListener = null
        binding.genderEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                onGenderEditTextClicked()
            }
        }

        binding.birthdayEditText.keyListener = null
        binding.birthdayEditText.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    viewModel.birthdayEditTextClicked(view)
                }
            }

        binding.updatePhoto.setOnClickListener {
            ImagePicker.with(this)
                .crop(1f, 1f)	    		//Crop Square image(Optional)
                .maxResultSize(800, 800)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        return binding.root
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ImagePicker.REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.data?.let {
                        viewModel.setPhoto(it)
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Canceled
                }
            }
        }
    }

    override fun onLoadUser(user: UserEntity) {
        GenderEntity.codeOf(user.genderCode).let {
            viewModel.gender.set(it)
            viewModel.genderText.set(context?.let { context -> it?.getNameString(context) })
        }
    }

    override fun onSuccessEditProfile() {
        context?.let {
            Toast.makeText(
                it,
                it.getString(R.string.edit_profile_successful),
                Toast.LENGTH_SHORT
            ).show()
        }

        activity?.onBackPressed()
    }

    override fun onBirthdayEditTextClicked(year: Int?, month: Int?, dayOfMonth: Int?) {
        DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialogTheme,
            DatePickerDialog.OnDateSetListener { _, yearPicked, monthPicked, dayOfMonthPicked ->
                viewModel.setBirthday(yearPicked, monthPicked + 1, dayOfMonthPicked)
            }, year ?: 1997, month?.minus(1) ?: 0, dayOfMonth ?: 1
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
            val c = Calendar.getInstance()
                .apply { set(1900, 1, 1) }
            datePicker.minDate = c.timeInMillis
        }.show()
    }

    override fun onGenderEditTextClicked() {
        viewModel.genders.let { genders ->
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.sign_up_gender_hint))
                .setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        genders.map { it.getNameString(requireContext()) }.toTypedArray()
                    )
                ) { dialog, which ->
                    viewModel.genderSelected(which, requireContext())
                    dialog.dismiss()
                }
                .show()
        }


    }

    override fun onError(errorReponse: ErrorResponse) {
        Toast.makeText(
            context,
            errorReponse.messageString(this.requireContext()),
            Toast.LENGTH_SHORT
        ).show()
    }

}

interface EditProfileNavigator {
    fun onLoadUser(user: UserEntity)

    fun onSuccessEditProfile()

    fun onGenderEditTextClicked()

    fun onBirthdayEditTextClicked(year: Int?, month: Int?, dayOfMonth: Int?)

    fun onError(errorReponse: ErrorResponse)
}
