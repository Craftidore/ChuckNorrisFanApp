package com.craftidore.chucknorris

import android.app.Application
import com.craftidore.chucknorris.data.ChuckNorrisApiService
import com.craftidore.chucknorris.data.category.CategoryRepository
import com.craftidore.chucknorris.data.db.InternalJokeRepository
import com.craftidore.chucknorris.data.joke.JokeRepository
import com.craftidore.chucknorris.data.search.SearchRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
class StudyHelperApplication: Application() {
    // Needed to create ViewModels with the ViewModelProvider.Factory
    lateinit var studyRepository: StudyRepository

    // For onCreate() to run, android:name=".StudyHelperApplication" must
    // be added to <application> in AndroidManifest.xml
    override fun onCreate() {
        super.onCreate()
        studyRepository = StudyRepository(this.applicationContext)
    }
}
*/

class ChuckNorrisFanApp: Application(){
    lateinit var jokeRepository: JokeRepository
    lateinit var categoryRepository: CategoryRepository
    lateinit var searchRepository: SearchRepository
    lateinit var internalJokeRepository: InternalJokeRepository

    override fun onCreate() {
        super.onCreate()
        val chuckNorrisApiService: ChuckNorrisApiService by lazy {
            val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.chucknorris.io/jokes/")
                .build()
            retrofit.create(ChuckNorrisApiService::class.java)
        }

        jokeRepository = JokeRepository(chuckNorrisApiService)
        categoryRepository = CategoryRepository(chuckNorrisApiService)
        searchRepository = SearchRepository(chuckNorrisApiService)
        internalJokeRepository = InternalJokeRepository(this.applicationContext)
    }
}