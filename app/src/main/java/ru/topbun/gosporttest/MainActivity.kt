package ru.topbun.gosporttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.topbun.gosporttest.data.RequestResponseMergeStrategy
import ru.topbun.gosporttest.data.repositories.FoodRepositoryImpl
import ru.topbun.gosporttest.data.source.local.AppDatabase
import ru.topbun.gosporttest.data.source.remote.RetrofitFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}