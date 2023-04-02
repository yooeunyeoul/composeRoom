package com.dongeul.yogi.di

import android.app.Application
import androidx.room.Room
import com.dongeul.yogi.data.repository.FakeUserRepository
import com.dongeul.yogi.github_users.common.Constants
import com.dongeul.yogi.github_users.data.data_source.local.UserDatabase
import com.dongeul.yogi.github_users.data.data_source.remote.GithubApi
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): UserDatabase {
        return Room.inMemoryDatabaseBuilder(
            app, UserDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApi(okHttpClient: OkHttpClient): GithubApi {
        return Retrofit.Builder()
            .baseUrl(
                Constants.BASE_URL
            )
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: UserDatabase): UserRepository {
        return FakeUserRepository(db.userDao)
    }
}