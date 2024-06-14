package com.myandroid.blinkitclone.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.myandroid.blinkitclone.R
import com.myandroid.blinkitclone.Utils
import com.myandroid.blinkitclone.databinding.FragmentOTPBinding
import com.myandroid.blinkitclone.models.Users
import com.myandroid.blinkitclone.viewmodels.AuthViewModel
import kotlinx.coroutines.launch


class OTPFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentOTPBinding
    private lateinit var userNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOTPBinding.inflate(layoutInflater)
        getUserNumber()
        customizingEnteringOtp()
        onBackButtonClicked()
        sendOtp()
        loginButtonClick()

        return binding.root


    }

    private fun loginButtonClick() {
        binding.btnLogin.setOnClickListener {

            val editTexts = arrayOf(
                binding.etOtp1,
                binding.etOtp2,
                binding.etOtp3,
                binding.etOtp4,
                binding.etOtp5,
                binding.etOtp6
            )
            val otp = editTexts.joinToString("") { it.text.toString() }

            if (otp.length < editTexts.size) {
                Utils.showToast(requireContext(), "Please enter correct otp")
            } else {

                editTexts.forEach { it.text?.clear();it.clearFocus() }
                Utils.showDialog(requireContext(), "Signing you...")
                verifyOtp(otp)
            }
        }


    }

    private fun verifyOtp(otp: String) {
        val user = Users(uid = Utils.getCurrentUserId(), userPhoneNumber = userNumber, userAddress = null)
        viewModel.signInWithPhoneAuthCredential(otp, userNumber, user)





            lifecycleScope.launch {

                try {
                    viewModel.issignedSuccessfully.collect {
                        if (it) {

                            Utils.hideDialog()
                            Utils.showToast(requireContext(), "Logged In... $userNumber")
//             startActivity(Intent(requireActivity(), UsersMainActivity::class.java))
//             requireActivity().finish()
                        }
                    }
                }
                catch (e: Exception) {
                    Utils.showToast(requireContext(), e.toString())
                }



            }



    }


    private fun sendOtp() {
        Utils.showDialog(requireContext(), "Sending Otp ...")
        viewModel.apply {

            viewModel.sendOTP(userNumber, requireActivity())

            lifecycleScope.launch {
                otpSent.collect {
                    if (it) {
                        Utils.hideDialog()
                        Utils.showToast(requireContext(), "Otp Sent..")
                    }
                }
            }


        }

    }

    private fun onBackButtonClicked() {
        binding.tbOtpFragment.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_OTPFragment_to_signinFragment)
        }
    }

    private fun getUserNumber() {
        val bundle = arguments
        userNumber = bundle?.getString("number").toString()
        binding.tvUserNumber.text = userNumber


    }

    private fun customizingEnteringOtp() {
        val editTexts = arrayOf(
            binding.etOtp1,
            binding.etOtp2,
            binding.etOtp3,
            binding.etOtp4,
            binding.etOtp5,
            binding.etOtp6
        )

        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.length == 1) {
                        if (i < editTexts.size - 1) {
                            editTexts[i + 1].requestFocus()
                        }
                    } else if (p0?.length == 0) {
                        if (i > 0) {
                            editTexts[i - 1].requestFocus()
                        }
                    }
                }

            })
        }
    }


}