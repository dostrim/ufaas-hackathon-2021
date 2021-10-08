package com.bebridge.android_template.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bebridge.android_template.db.AppDatabase
import com.bebridge.android_template.api.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

  @Singleton
  @Provides
  fun provideContext(): Context {
    return application
  }

  @Singleton
  @Provides
  fun provideApiService(): ApiService.IApiService {
    return ApiService(application).service
  }

  @Singleton
  @Provides
  fun provideDb(context: Context): AppDatabase {
    return Room
      .databaseBuilder(context, AppDatabase::class.java, "template")
      .fallbackToDestructiveMigration()
      .build()
  }

}