package com.nfremont.testleboncoin

import android.app.Application
import com.nfremont.albums.list.albumsListModule
import com.nfremont.core.httpClientModule
import com.nfremont.core.objectMapperModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LeboncoinApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            // TODO: Modify Koin Logger when it finally supports Kotlin 1.6+
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@LeboncoinApplication)
            modules(
                objectMapperModule,
                albumsListModule,
                httpClientModule,
            )
        }
    }
}