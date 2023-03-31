package com.dongeul.yogi.github_users.di

import android.app.Application
import androidx.room.Room
import com.dongeul.yogi.BuildConfig
import com.dongeul.yogi.github_users.common.Constants
import com.dongeul.yogi.github_users.data.data_source.UserDatabase
import com.dongeul.yogi.github_users.data.remote.GithubApi
import com.dongeul.yogi.github_users.data.repository.UserRepositoryImpl
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app, UserDatabase::class.java,
            UserDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideOkHttp(app: Application): OkHttpClient {
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
    fun provideUserRepository(api: GithubApi, db: UserDatabase): UserRepository {
        return UserRepositoryImpl(api = api, dao = db.userDao)
    }
}