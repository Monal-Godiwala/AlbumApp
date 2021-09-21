package com.blazze.albumapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.blazze.albumapp.R
import com.blazze.albumapp.database.AppDatabase
import com.blazze.albumapp.databinding.ActivityMainBinding
import com.blazze.albumapp.network.ApiClient
import com.blazze.albumapp.network.ApiInterface
import com.blazze.albumapp.network.ApiListener
import com.blazze.albumapp.repository.AlbumRepository
import com.blazze.albumapp.utils.AppUtils.isNetworkAvailable
import com.blazze.albumapp.view.adapter.AlbumAdapter
import com.blazze.albumapp.viewmodel.AlbumViewModel
import com.blazze.albumapp.viewmodel.AlbumViewModelFactory

class MainActivity : AppCompatActivity(), ApiListener {

    private lateinit var binding: ActivityMainBinding

    private var apiInterface: ApiInterface? = ApiClient.client?.create(ApiInterface::class.java)
    private var appDatabase: AppDatabase? = null
    private var repository: AlbumRepository? = null

    private val viewModel: AlbumViewModel by lazy {
        ViewModelProvider(
            this,
            AlbumViewModelFactory(repository, this)
        ).get(AlbumViewModel::class.java)
    }


    //<editor-fold desc="Override Methods">

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
    }

    //</editor-fold>

    //<editor-fold desc="Private Methods">

    private fun init() {

        appDatabase = AppDatabase.getInstance(this)
        repository = AlbumRepository(apiInterface, appDatabase)

        // Fetch Album list
        if (isNetworkAvailable()) {
            viewModel.getAlbums(isOffline = false)
            binding.textNoNetwork.visibility = View.GONE
            observeAlbumList()
        } else {
            viewModel.getAlbums(isOffline = true)
            binding.textNoNetwork.visibility = View.VISIBLE
            observeAlbumList(isNetworkAvailable = false)
        }


    }

    private fun observeAlbumList(isNetworkAvailable: Boolean = true) {
        viewModel.albumList.observe(this) { albumList ->

            // Sort list by title
            albumList?.sortedBy {
                it.title
            }?.let { albums ->

                if (isNetworkAvailable) {
                    // Add albums to offline database
                    viewModel.insertOfflineAlbums(albums)
                }

                // populate album in recycler view
                binding.listAlbum.apply {
                    // Set divider
                    addItemDecoration(
                        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    )
                    // Set adapter
                    adapter = AlbumAdapter(albums)
                }
            }

            updateUi(isAlbumLoaded = true)
        }
    }

    private fun updateUi(isAlbumLoaded: Boolean) {
        if (isAlbumLoaded) {
            binding.shimmerList.stopShimmer()
            binding.shimmerList.visibility = View.GONE
            binding.listAlbum.visibility = View.VISIBLE
        } else {
            binding.shimmerList.startShimmer()
            binding.shimmerList.visibility = View.VISIBLE
            binding.listAlbum.visibility = View.GONE
        }
    }

    //</editor-fold>

    //<editor-fold desc="Api Listener">

    override fun onStarted(requestCode: Int) {
        updateUi(isAlbumLoaded = false)
    }

    override fun onFailure(requestCode: Int, message: String) {
        updateUi(isAlbumLoaded = true)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //</editor-fold>
}