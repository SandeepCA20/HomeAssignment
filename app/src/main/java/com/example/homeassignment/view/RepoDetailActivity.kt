package com.example.homeassignment.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.homeassignment.R
import com.example.homeassignment.databinding.ActivityRepoBinding


class RepoDetailActivity: AppCompatActivity() {
    lateinit var binding: ActivityRepoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init(){
        binding= ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = getString(R.string.detail)
        setData()
    }
    private fun setData(){
        val bundle = intent.extras
        if (bundle != null){
            binding.txtNameDetail.text = bundle.getString("name")
            binding.txtDescDetail.text = bundle.getString("description")
            binding.txtUpdatedDetail.text = bundle.getString("updated_at")
            binding.txtStrgrCntDetail.text = bundle.getString("stargazers_count")
            val forksCount = bundle.getString("forks")
            if (forksCount != null) {
                if(forksCount.toInt()>=5000){
                    binding.txtFrksCntDetail.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.star,
                        0)

                }
                binding.txtFrksCntDetail.text= "$forksCount"
            }

        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}