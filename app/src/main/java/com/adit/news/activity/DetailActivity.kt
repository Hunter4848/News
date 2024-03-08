package com.adit.news.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adit.news.databinding.ActivityDetailBinding
import com.adit.news.models.NewsData
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var modelNews: NewsData?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        modelNews = intent.getParcelableExtra(INTENT_DATA)
        modelNews?.let { getData(it) }

    }

    private fun getData(data: NewsData){
        Glide.with(this)
            .load(data.photo)
            .into(binding.imageNews)

        binding.tvTitleNews.text = data.title
        binding.tvDescriptionNews.text = "Author : ${data.author} \n ${data.description}"
        binding.tvUrlNews.text = "Url : " + data.urlNews
    }

    companion object {
        const val INTENT_DATA = "intent_data"
    }
}