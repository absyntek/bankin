package com.example.bankintest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankintest.data_base.AppDatabase
import com.example.bankintest.models.Category
import com.example.bankintest.models.MainModel
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okhttp3.OkHttpClient
import okhttp3.Request

/*** Reference technique
 * @author Etienne
 * @since 24/02/2023
 * @see <a href="https://github.com/bridgeapi-engineering/challenge-android">Spec</a>
 */
class MainViewModel : ViewModel(){
    private val viewModelJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private val decoder = Json { ignoreUnknownKeys = true }

    var database: AppDatabase? = null

    fun getData(onError:(() -> Unit)){
        scope.launch {
            try {
                val httpC = OkHttpClient.Builder().build()
                val request = Request.Builder()
                    .url("https://raw.githubusercontent.com/bankin-engineering/challenge-android/master/categories.json")
                    .get()
                val response = httpC.newCall(request.build()).execute()
                response.body?.let {
                    val model = decoder.decodeFromStream<MainModel>(it.byteStream())
                    database?.categoryDao()?.insertAll(model.resources)
                }
            }catch (e:Exception){
                onError()
                e.printStackTrace()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        stopJobs()
    }

    private fun stopJobs(){
        scope.cancel()
        viewModelJob.cancel()
    }
}