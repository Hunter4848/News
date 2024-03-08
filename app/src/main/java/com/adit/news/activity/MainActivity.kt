package com.adit.news.activity

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.news.R
import com.adit.news.adapter.NewsAdapter
import com.adit.news.databinding.ActivityMainBinding
import com.adit.news.mvvm.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var adapter: NewsAdapter = NewsAdapter()

    private val mainViewModels: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)

        showRecyclerView()
        mainViewModels.setDataAPI()
        mainViewModels.getDataAPI().observe(this, { data ->
            if (data != null) {
                adapter.setData(data)
                showLoading(false)
            }
        })
        mainViewModels.getError().observe(this,{
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun showRecyclerView() {
        binding.rvNews.setHasFixedSize(true)
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter
        showLoading(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService<SearchManager>()
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager?.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    showLoading(false)
                } else {
                    showLoading(true)
                    mainViewModels.setSearchDataAPI(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    showLoading(false)
                }
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun showLoading(progress: Boolean) {
        if (progress) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}