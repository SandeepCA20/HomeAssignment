package com.example.homeassignment.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeassignment.R
import com.example.homeassignment.UserApplication
import com.example.homeassignment.adapter.UserRepoAdapter
import com.example.homeassignment.databinding.ActivityMainBinding
import com.example.homeassignment.viewmodel.UserViewModel
import com.example.homeassignment.viewmodel.UserViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: UserViewModelFactory  //inject factory class

    private val adapter = UserRepoAdapter(mutableListOf())  //initilized adapter class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        userData()
    }

    ///initialized function
    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as UserApplication).applicationComponent.inject(this)
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    ///observe data from view model and set data accordingly
    private fun userData() {
        userViewModel.userData.observe(this, Observer {
            Picasso.get().load(it.avatarUrl).into(binding.imgUser)
            binding.txtUsername.text = it.name
        })
        userViewModel.userRepos.observe(this, Observer {
            if (it.isNotEmpty()) {
                controlVisibility(true, false)
                adapter.updateListData(it)
                adapter.onItemClick = { position ->
                    val intent = Intent(this, RepoDetailActivity::class.java)
                    intent.putExtra("userData", it[position])
                    startActivity(intent)
                }
                showNoRecordText(false)
            } else {
                controlVisibility(false, false)
                showNoRecordText(true)
            }
        })
        userViewModel.errorMessage.observe(this, Observer {
            controlVisibility(false, false)
            showNoRecordText(true)
            binding.txtNoRecord.text = it
        })
    }

    ///on search button click handle
    fun onButtonClick(view: View) {
        showNoRecordText(false)

        if (binding.edtUserId.text.trim().isNotEmpty()) {
            controlVisibility(false, true)
            lifecycleScope.launch {
                userViewModel.getUserInfo(binding.edtUserId.text.toString())
            }
        } else {
            controlVisibility(false, false)
            binding.edtUserId.error = getString(R.string.error_msg_userid)
        }
        hideKeybaord(view)
    }

    ///if repo list is empty showing text
    private fun showNoRecordText(isShown: Boolean) {
        if (isShown) {
            binding.txtNoRecord.visibility = View.VISIBLE
            binding.txtNoRecord.text = getString(R.string.no_record_found)
        } else {
            binding.txtNoRecord.visibility = View.GONE
        }
    }

    ///handle view visibility
    private fun controlVisibility(visibility: Boolean, progressBarVisibility: Boolean) {
        binding.run {
            recyclerView.isVisible = visibility
            progressBar.isVisible = progressBarVisibility
            imgUser.isVisible = visibility
            txtUsername.isVisible = visibility
        }
    }

    ///handle keyboard hide on search button click
    private fun hideKeybaord(v: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }
}