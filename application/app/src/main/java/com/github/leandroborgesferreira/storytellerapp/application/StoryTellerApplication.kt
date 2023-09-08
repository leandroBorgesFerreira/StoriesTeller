package com.github.leandroborgesferreira.storytellerapp.application

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.kotlin.core.Amplify
import com.github.leandroborgesferreira.storytellerapp.auth.core.AuthInitializer

class StoryTellerApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            AuthInitializer.initializeAwsAuth()
            Amplify.configure(applicationContext)
            Log.i("StoryTellerApplication", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("StoryTellerApplication", "Could not initialize Amplify", error)
        }
    }
}