package com.thariqzs.wanderai

import com.thariqzs.wanderai.data.api.ApiService
import com.thariqzs.wanderai.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideAuthRepository(apiService: ApiService) = AuthRepository(apiService)

//    @Provides
//    fun provideMainRepository(mainApiService: MainApiService) = MainRepository(mainApiService)
}