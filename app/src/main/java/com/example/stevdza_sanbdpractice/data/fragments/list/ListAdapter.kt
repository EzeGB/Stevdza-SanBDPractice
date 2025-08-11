package com.example.stevdza_sanbdpractice.data.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stevdza_sanbdpractice.data.models.User
import com.example.stevdza_sanbdpractice.databinding.CustomRowBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = CustomRowBinding.inflate(LayoutInflater.from(
            parent.context),
            parent,
            false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val currentUser = userList[position]
        holder.binding.idText.text = currentUser.id.toString()
        holder.binding.firstnameText.text = currentUser.firstName
        holder.binding.lastnameText.text = currentUser.lastName
        holder.binding.ageText.text = "(${currentUser.age})"
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(users: List<User>){
        this.userList = users
        notifyDataSetChanged()
    }

    class MyViewHolder (val binding: CustomRowBinding): RecyclerView.ViewHolder(binding.root){

    }


}