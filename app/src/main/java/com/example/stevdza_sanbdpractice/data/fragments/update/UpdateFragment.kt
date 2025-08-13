package com.example.stevdza_sanbdpractice.data.fragments.update

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stevdza_sanbdpractice.R
import com.example.stevdza_sanbdpractice.data.models.User
import com.example.stevdza_sanbdpractice.data.viewmodels.UserViewModel
import com.example.stevdza_sanbdpractice.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater,
            container,
            false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.updateFirstNameEt.setText(args.currentUser.firstName)
        binding.updateLastNameEt.setText(args.currentUser.lastName)
        binding.updateAgeEt.setText(args.currentUser.age.toString())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.topUpdateBar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        val menuHost : MenuHost = requireActivity()
        setUpMenuHost(menuHost)

        binding.updateBtn.setOnClickListener {
            updateUser()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setUpMenuHost(menuHost: MenuHost){
        menuHost.addMenuProvider(object : MenuProvider{

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.delete_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId ){
                    R.id.menu_delete -> {
                        deleteUser()
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun updateUser(){
        val firstName = binding.updateFirstNameEt.text.toString()
        val lastName = binding.updateLastNameEt.text.toString()
        val age = binding.updateAgeEt.text.toString()

        if (inputCheck(firstName,lastName,age)){
            //Create User Object
            val updatedUser = User(args.currentUser.id,
                firstName,lastName,
                Integer.parseInt(age))
            // Update current user
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(),"Successfully updated!", Toast.LENGTH_LONG).show()
            //Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else{
            Toast.makeText(requireContext(),"Please fill out all the fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean{
        return !(TextUtils.isEmpty(firstName)||
                TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(age))
    }

    private fun deleteUser(){
        val currentFirstName = args.currentUser.firstName
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Delete $currentFirstName?")
            setMessage("Are you sure you want to delete $currentFirstName?")
            setPositiveButton("Yes") {_,_ ->
                mUserViewModel.deleteUser(args.currentUser)

                Toast.makeText(requireContext(),
                    "Successfully removed: $currentFirstName",
                    Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            setNegativeButton("No"){_,_ ->}
            create()
            show()
        }
    }
}