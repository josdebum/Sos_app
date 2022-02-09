import android.util.Log
import android.widget.Toast
import com.example.sosapp.APIService
import com.example.sosapp.LocationDetails
import com.example.sosapp.ServiceBuilder
import com.example.sosapp.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RestApiService {
    fun sendSOS(userData: UserInfo){
        Log.e("RestAPIService", "sendSOS: called")
        val retrofit = ServiceBuilder.buildService(APIService::class.java)
        retrofit.createEmployee(userData).enqueue(
            object : Callback<UserInfo> {
                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    Log.e("RestAPIService", "onFailure message: ${t.message}")
                }
                override fun onResponse( call: Call<UserInfo>, response: Response<UserInfo>) {
                    print("Response : ${response.body().toString()}")
                    Log.e("RestAPIService", "onResponse isSuccessful: ${response.isSuccessful}", )
                    Log.e("RestAPIService", "onResponse message: ${response.message()}")
                    Log.e("RestAPIService", "onResponse body(): ${response.body()}", )
                    Log.e("RestAPIService", "onResponse code: ${response.code()}")
                }
            }
        )
    }



}