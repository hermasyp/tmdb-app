package com.catnip.goplaytmdb.domain.mapper

import com.catnip.ajaibpretest.utils.mapper.ViewParamMapper
import com.catnip.goplaytmdb.data.network.model.response.GenreResponse
import com.catnip.goplaytmdb.domain.viewparam.GenreViewParam

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object GenreMapper : ViewParamMapper<GenreResponse, GenreViewParam> {
    override fun toViewParam(dataObject: GenreResponse?) = GenreViewParam(
        id = dataObject?.id ?: -1,
        name = dataObject?.name.orEmpty()
    )
}