package com.myandroid.blinkitclone.auth

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.myandroid.blinkitclone.R
import com.myandroid.blinkitclone.Utils
import com.myandroid.blinkitclone.databinding.FragmentSigninBinding


class  SigninFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentSigninBinding.inflate(layoutInflater)
        setStatusBarColor()
        getUserNumber()
        onContinueButtonClick()
        bottomScroll()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun bottomScroll() {

        binding.etUserNumber.setOnClickListener{
            binding.signinScroll.fullScroll(View.FOCUS_DOWN)
        }

    }

    private fun onContinueButtonClick(){
        binding.btnContinue.setOnClickListener{
            val number =binding.etUserNumber.text.toString()
            if(number.isEmpty() || number.length !=10){
                Utils.showToast(requireContext(), "Please enter a valid Phone number")
            }
            else{
                val bundle = Bundle()
                bundle.putString("number",number)
                findNavController().navigate(R.id.action_signinFragment_to_OTPFragment,bundle)
            }
        }
    }

    private fun getUserNumber(){
        binding.etUserNumber.addTextChangedListener (object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(number: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val len = number?.length

                if (len == 10) {
                    binding.btnContinue.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.green
                        )
                    )
                } else {
                    binding.btnContinue.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grayish_blue
                        )
                    )
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }



    private fun setStatusBarColor(){
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(),R.color.yellow)
            statusBarColor =  statusBarColors
            if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M){
                decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }



    }
