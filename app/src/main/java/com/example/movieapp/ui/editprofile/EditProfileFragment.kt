package com.example.movieapp.ui.editprofile

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.movieapp.worker.BlurWorker
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentEditProfileBinding
import com.example.movieapp.ui.uriToFile
import java.util.*

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var workManager: WorkManager

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

        (activity as AppCompatActivity).supportActionBar?.title = "Profile"

        workManager = WorkManager.getInstance(requireContext())

        binding.apply {
            resources.apply {
                tvTitleEditProfile.text = getString(R.string.edit_profile)
                layoutUsername.apply {
                    startIconDrawable = getDrawable(R.drawable.ic_profile)
                    hint = getString(R.string.hint_username)
                }
                layoutName.apply {
                    startIconDrawable = getDrawable(R.drawable.ic_name)
                    hint = getString(R.string.hint_name)
                }
                layoutAddress.apply {
                    startIconDrawable = getDrawable(R.drawable.ic_address)
                    hint = getString(R.string.hint_address)
                }
                btnSave.text = getString(R.string.save)

            }
            btnSave.setOnClickListener {
                val save = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
                findNavController().navigate(save)
            }
            btnEditAvatar.setOnClickListener {
                openGallery()
            }
        }
    }

    private val imageResult = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val image = it?.let { it1 ->
            uriToFile(it1, requireContext())
        }

        binding.ivAvatar.setImageURI(it)

        val data = Data.Builder()
            .putString("image", image?.path)
            .build()
        val workRequest = OneTimeWorkRequest
            .Builder(BlurWorker::class.java)
            .setInputData(data)
            .build()

        workManager.enqueue(workRequest)
        workManager.getWorkInfoByIdLiveData(workRequest.id).observe(viewLifecycleOwner) { workResult ->
            if (workResult.state.isFinished) {
                val result = workResult.outputData.keyValueMap["result"]
                result?.let {
                    binding.ivAvatar.setImageURI(result as Uri)
                }
            }
        }

    }

    private fun openGallery() {
        imageResult.launch("image/*")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}