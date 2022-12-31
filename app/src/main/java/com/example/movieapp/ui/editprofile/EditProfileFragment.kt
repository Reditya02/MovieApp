package com.example.movieapp.ui.editprofile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            resources.apply {
                tvTitleRegister.text = getString(R.string.edit_profile)
                layoutUsername.apply {
                    startIconDrawable = getDrawable(R.drawable.ic_profile)
                    hint = getString(R.string.hint_username)
                }
                layoutName.apply {
                    startIconDrawable = getDrawable(R.drawable.ic_name)
                    hint = getString(R.string.hint_name)
                }
//                layoutAddress.apply {
//                    startIconDrawable = getDrawable(R.drawable.ic_address)
//                    hint = getString(R.string.hint_address)
//                }
//                btnSave.text = getString(R.string.save)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}