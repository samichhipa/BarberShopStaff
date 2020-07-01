package com.example.barbershopstaff.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(

            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAMSyrlj8:APA91bFdXbTsWSQ4GF90gL3ys6sl9JwOz9wFaRVROE7SfUwaRrgVyaFe9ktvgu4_2WZxzvdFNTKuTl42E1zLOH6f614Bw8wzi0tmf0i36YVvetaGv3_ocHQc5ulUnVOLOuDq66XXfZas"

            }

    )


    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
