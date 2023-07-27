package com.example.homeassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeassignment.databinding.CustomUserRepoItemsBinding
import com.example.homeassignment.model.UserRepos

class UserRepoAdapter(private val repoList: List<UserRepos>): RecyclerView.Adapter<UserRepoAdapter.MyViewHolder>() {
    var onItemClick:((position: Int) -> Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding=CustomUserRepoItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userRepos: UserRepos=repoList[position]
        holder.bind(userRepos)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }

    class MyViewHolder(private val itemsBinding: CustomUserRepoItemsBinding): RecyclerView.ViewHolder(itemsBinding.root) {
        fun bind(userRepos: UserRepos){
            itemsBinding.txtRepoName.text=userRepos.name
            itemsBinding.txtRepoDesc.text=userRepos.description
        }

    }

}

