package com.example.assesment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assesment.R
import com.example.assesment.api.repository.Repository
import com.example.assesment.ui.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val mainViewModel : MainViewModel = MainViewModel(repository = Repository())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeUserPost()
        mainViewModel.fetchDetails()


    }

    private fun observeUserPost() {
        mainViewModel._userLiveData.observe(this){
            if (!it.isNullOrEmpty())  {
                val adapter = mAdapter(
                    this@MainActivity,it
                )
                rv.adapter = adapter
            }
        }
    }


}

