package kz.just_code.retorfitapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kz.just_code.retorfitapp.api.WeatherApiData
import kz.just_code.retorfitapp.api.WeatherResponse
import kz.just_code.retorfitapp.data.WeatherRepository
import kz.just_code.retorfitapp.data.WeatherRepositoryImpl
@HiltViewModel
class MainViewModel: BaseViewModel() {
    private val repository: WeatherRepository = WeatherRepositoryImpl(WeatherApiData.getApi())

    private var _currentWeatherLiveData = MutableLiveData<WeatherResponse?>()
    val currentWeatherLiveData: LiveData<WeatherResponse?> = _currentWeatherLiveData

    fun getCurrentWeather(city: String) {
        launch(
            request = {
                repository.getCurrentWeather(city)
            },
            onSuccess = {
                _currentWeatherLiveData.postValue(it)
            }
        )
    }
}

abstract class BaseViewModel: ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    private var _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private var _exceptionLiveData = MutableLiveData<String?>()
    val exceptionLiveData: LiveData<String?> = _exceptionLiveData

    fun <T> launch(
        request: suspend () -> T,
        onSuccess: (T) -> Unit = { }
    ) {
        coroutineScope.launch {
            try {
                _loadingLiveData.postValue(true)
                val response = request.invoke()
                onSuccess.invoke(response)
            } catch (e: Exception) {
                _exceptionLiveData.postValue(e.message)
                Log.e(">>>", e.message.orEmpty())
            } finally {
                _loadingLiveData.postValue(false)
            }
        }
    }
}