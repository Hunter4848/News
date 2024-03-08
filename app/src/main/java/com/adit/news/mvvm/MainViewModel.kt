package com.adit.news.mvvm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adit.news.activity.MainActivity
import com.adit.news.models.NewsData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {
    private val errorMsg = MutableLiveData<String>()
    private val listNews = MutableLiveData<ArrayList<NewsData>>()

    fun setDataAPI() {
        val list = ArrayList<NewsData>()

        val client = AsyncHttpClient()
        val url =
            "https://newsapi.org/v2/top-headlines?country=us&apiKey=00c25299286547eb93ecc2804d8cc7ea"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = String(responseBody!!)

                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("articles")

                    for (i in 0 until items.length()) {
                        val jsonObject = items.getJSONObject(i)
                        val data = NewsData()
                        data.author = jsonObject.getString("author")
                        data.description = jsonObject.getString("description")
                        data.photo = jsonObject.getString("urlToImage")
                        data.title = jsonObject.getString("title")
                        data.urlNews = jsonObject.getString("url")

                        val date = SimpleDateFormat(
                            "yyyy-MM-dd'T'HH:mm:ss'Z'",
                            Locale.US
                        ).parse(jsonObject.getString("publishedAt"))
                        val formatter =
                            SimpleDateFormat("E, dd MMMM HH.mm", Locale.US).format(date!!)
                        data.publish = formatter


                        val source = jsonObject.getJSONObject("source")
                        data.name = source.getString("name")

                        list.add(data)
                    }
                    listNews.postValue(list)
                } catch (e: Exception) {

                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
                errorMsg.postValue(error?.localizedMessage)
            }

        })
    }

    fun setSearchDataAPI(title: String) {
        val list = ArrayList<NewsData>()

        val client = AsyncHttpClient()
        val url = "https://newsapi.org/v2/everything?q=$title"
        client.addHeader("Authorization", "00c25299286547eb93ecc2804d8cc7ea")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = String(responseBody!!)

                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("articles")

                    for (i in 0 until items.length()) {
                        val jsonObject = items.getJSONObject(i)
                        val data = NewsData()
                        data.author = jsonObject.getString("author")
                        data.description = jsonObject.getString("description")
                        data.photo = jsonObject.getString("urlToImage")
                        data.title = jsonObject.getString("title")
                        data.urlNews = jsonObject.getString("url")

                        val date = SimpleDateFormat(
                            "yyyy-MM-dd'T'HH:mm:ss'Z'",
                            Locale.US
                        ).parse(jsonObject.getString("publishedAt"))
                        val formatter =
                            SimpleDateFormat("E, dd MMMM HH.mm", Locale.US).format(date!!)
                        data.publish = formatter


                        val source = jsonObject.getJSONObject("source")
                        data.name = source.getString("name")

                        list.add(data)
                    }
                    listNews.postValue(list)
                } catch (e: Exception) {

                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
                errorMsg.postValue(error?.localizedMessage)
            }

        })
    }

    fun getDataAPI(): LiveData<ArrayList<NewsData>> {
        return listNews
    }

    fun getError(): LiveData<String> = errorMsg
}