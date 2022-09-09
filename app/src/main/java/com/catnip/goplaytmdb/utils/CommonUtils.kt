package com.catnip.goplaytmdb.utils

import android.content.Context
import android.content.Intent
import com.catnip.goplaytmdb.R
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object CommonUtils {

    fun getWatchlistIcon(isUserWatchlist: Boolean): Int {
        return if (isUserWatchlist) R.drawable.ic_check else R.drawable.ic_add
    }

    fun shareFilm(context: Context, movieViewParam: MovieViewParam) {
        val shareIntent = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Watch this Movie! ${movieViewParam.title} ${movieViewParam.posterPath}"
            )
            type = "text/plain"
        }, null)
        context.startActivity(shareIntent)
    }
}