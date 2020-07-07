package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.LoadState
import com.example.myapplication.ui.SearchUsersController
import com.example.myapplication.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModel()
    private val requestManager by lazy { Glide.with(this) }
    private val controller by lazy { SearchUsersController(requestManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        initViews()

        viewModel.users.observe(this, Observer {
            controller.submitList(it)
        })
        viewModel.loadState.observe(this, Observer {
            when (it) {
                is LoadState.Loading -> binding.progress.visibility = View.VISIBLE
                is LoadState.NotLoading -> binding.progress.visibility = View.GONE
                is LoadState.Error -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(this, it.throwable.message, LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initViews() {
        binding.recyclerView.apply {
            setController(controller)
        }
    }
}