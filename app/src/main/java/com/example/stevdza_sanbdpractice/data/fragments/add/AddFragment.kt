package com.example.stevdza_sanbdpractice.data.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.stevdza_sanbdpractice.R
import com.example.stevdza_sanbdpractice.data.User
import com.example.stevdza_sanbdpractice.data.UserViewModel
import com.example.stevdza_sanbdpractice.databinding.FragmentAddBinding
import com.example.stevdza_sanbdpractice.databinding.FragmentListBinding

class AddFragment : Fragment() {

    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater,container,false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addBtn.setOnClickListener {
            insertDataToDatabase()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun insertDataToDatabase(){
        val firstName = binding.editFirstNameEt.text.toString()
        val lastName = binding.editLastNameEt.text.toString()
        val age = binding.editAgeEt.text

        if(inputCheck(firstName,lastName,age)){
            //Create User Object
            val user = User(0,firstName,lastName,Integer.parseInt(age.toString()))
            //Add Data to the Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(),"Successfully added!", Toast.LENGTH_LONG).show()
            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else{
            Toast.makeText(requireContext(),"Please fill out all the fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName)&&TextUtils.isEmpty(lastName) && age.isEmpty())
    }
}