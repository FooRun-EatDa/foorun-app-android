package kr.foorun.core.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.foorun.data.local.PreferenceManager
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext context: Context): SharedPreferences
    = PreferenceManager.getPreferences(context)

    @Singleton
    @Provides
    fun providePreferencesManager(preferences: SharedPreferences): PreferenceManager
    = PreferenceManager(preferences)

//    @Singleton //for Room later
//    @Provides
//    fun provideRoomDB(@ApplicationContext context: Context) : RoomDB
//            = Room.databaseBuilder(context, RoomDB::class.java, "MemberDB")
//        .fallbackToDestructiveMigration()
//        .addTypeConverter(RoomTypeConverter())
//        .build()
}