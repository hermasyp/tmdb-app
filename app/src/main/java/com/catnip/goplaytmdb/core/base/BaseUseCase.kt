package com.catnip.goplaytmdb.core.base

import com.catnip.goplaytmdb.core.wrapper.ViewResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
abstract class BaseUseCase<P, R : Any?> constructor(private val dispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(param: P? = null): Flow<ViewResource<R>> {
        return execute(param)
            .flowOn(dispatcher)
    }

    @Throws(RuntimeException::class)
    abstract suspend fun execute(param: P? = null): Flow<ViewResource<R>>

}