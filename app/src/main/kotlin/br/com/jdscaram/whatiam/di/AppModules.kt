package br.com.jdscaram.whatiam.di

import android.content.Context
import android.content.SharedPreferences
import br.com.jdscaram.whatiam.config.FirebaseService
import br.com.jdscaram.whatiam.config.FirebaseServiceImpl
import br.com.jdscaram.whatiam.config.RemoteConfig
import br.com.jdscaram.whatiam.config.RemoteConfigImpl
import br.com.jdscaram.whatiam.domain.MainRepository
import br.com.jdscaram.whatiam.domain.MainRepositoryImpl
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val MINIMUM_FETCH = 3600L

@Module
@InstallIn(SingletonComponent::class)
class UnknownModule {

    @Provides
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val config = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = MINIMUM_FETCH
        }
        config.setConfigSettingsAsync(configSettings)
        return config
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("tea_reveal", Context.MODE_PRIVATE)
    }

}

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @[Binds Reusable]
    fun bindsFirebaseService(firebaseService: FirebaseServiceImpl): FirebaseService

    @[Binds Reusable]
    fun bindsRemoteConfig(remoteConfigImpl: RemoteConfigImpl): RemoteConfig

    @[Binds Reusable]
    fun bindsRepository(repository: MainRepositoryImpl): MainRepository
}
