package com.example.homeassignment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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

    lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: UserViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        userData()
    }
    private fun init(){
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as UserApplication).applicationComponent.inject(this)
        userViewModel= ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
    }
    private fun userData(){
        userViewModel.userData.observe(this, Observer {
            println("Result User>>>>>> $it")
            Picasso.get().load(it.avatar_url).into(binding.imgUser);
            binding.txtUsername.text=it.name

        })
        userViewModel.userRepos.observe(this, Observer {
            println("Result List>>>>>> $it")
            binding.progressBar.visibility=View.GONE
            if(it.isNotEmpty()){
                binding.recyclerView.layoutManager=LinearLayoutManager(this)
                val adapter=UserRepoAdapter(it)
                binding.recyclerView.adapter=adapter
                adapter.onItemClick = {position ->
                    val bundle= Bundle()
                    bundle.putString("name", it[position].name)
                    bundle.putString("description", it[position].description)
                    bundle.putString("updated_at", it[position].updated_at)
                    bundle.putString("stargazers_count", it[position].stargazers_count)
                    bundle.putString("forks", it[position].forks)
                    val intent= Intent(this,RepoDetailActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)

                }
            }else{
                showNoRecordText(true)
            }


        })
        userViewModel.errorMessage.observe(this, Observer {
            binding.progressBar.visibility=View.GONE
            showNoRecordText(true)
            binding.txtNoRecord.text=it
        })
    }

    fun onButtonClick(view: View) {
        showNoRecordText(false)
        if(binding.edtUserId.text.trim().isNotEmpty()){
            binding.progressBar.visibility=View.VISIBLE
            lifecycleScope.launch {
            userViewModel.getUserInfo(binding.edtUserId.text.toString())
        }
        }else{
            binding.edtUserId.error=getString(R.string.error_msg_userid)
        }
        hideKeybaord(view)

    }

    private fun showNoRecordText(isShown: Boolean){
        if(isShown){
            binding.txtNoRecord.visibility=View.VISIBLE
            binding.txtNoRecord.text=getString(R.string.no_record_found)
        }else{
            binding.txtNoRecord.visibility=View.GONE
        }
    }
    private fun hideKeybaord(v: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

}