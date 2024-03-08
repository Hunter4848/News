package com.adit.news.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.news.R
import com.adit.news.activity.DetailActivity
import com.adit.news.databinding.ListNewsBinding
import com.adit.news.models.NewsData
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {
    private val listNews = ArrayList<NewsData>()

    fun setData(items: ArrayList<NewsData>) {
        listNews.clear()
        listNews.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: NewsData) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(news.photo)
                    .into(imageNews)
                textName.text = itemView.resources.getString(R.string.name_source, news.name)
                textAuthor.text = itemView.resources.getString(R.string.author, news.author)
                textDescription.text = news.description
                textTitle.text = news.title
                textPublish.text = itemView.resources.getString(R.string.publish, news.publish)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.INTENT_DATA, news)
                    itemView.context.startActivity(intent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val mView = ListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listNews[position])
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.RED)
        } else if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int = listNews.size

}