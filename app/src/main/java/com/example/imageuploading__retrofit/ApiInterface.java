package com.example.imageuploading__retrofit;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
/*
    @Multipart
    @POST("uploadimages.php")
    Call<ResponsePOJO> uploadImage(@Part MultipartBody.Part image);
 */

    @FormUrlEncoded
    @POST("uploadimages.php")
    Call<ResponsePOJO> uploadIm(@Field("name") String name, @Field("image") String image);
   //
}