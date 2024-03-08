package com.adit.news.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsData(
    var photo: String? = null,
    var title: String? = null,
    var name: String? = null,
    var author: String? = null,
    var description: String? = null,
    var publish: String? = null,
    var urlNews: String? = null
) : Parcelable
