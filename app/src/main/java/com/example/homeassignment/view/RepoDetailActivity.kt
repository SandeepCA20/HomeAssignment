package com.example.homeassignment.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.homeassignment.R
import com.example.homeassignment.databinding.ActivityRepoBinding
import com.example.homeassignment.model.UserRepos
import java.io.Serializable

class RepoDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityRepoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detail)

        setData()
    }

    private fun setData() {
        val userObj = intent.serializable<UserRepos>("userData")
        if (userObj != null) {
            binding.run {
                txtNameDetail.text = userObj.name
                txtDescDetail.text = userObj.description
                txtUpdatedDetail.text = userObj.updatedAt
                txtStrgrCntDetail.text = userObj.stargazersCount
                txtFrksCntDetail.text =
                    "${userObj.forks}${if (userObj.forks.toInt() >= LARGE_FORK_NUMBER) "*" else ""}"
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

    companion object {
        private const val LARGE_FORK_NUMBER = 5000
    }
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
        key,
        T::class.java
    )
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}