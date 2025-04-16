package com.example.finalyearproject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.ArrayList;
import java.util.List;

public interface API {

    @FormUrlEncoded
    @POST("demo/Average.php")
    Call<Float> average(@Field("style") String style);

    @FormUrlEncoded
    @POST("demo/Addcontent.php")
    Call<String> addcontent(@Field("name") String name, @Field("visual") String visual,
                            @Field("audiliter") String audiliter);

    @FormUrlEncoded
    @POST("demo/Addmodule.php")
    Call<String> addmodule(@Field("name") String name, @Field("acronym") String acronym,
                              @Field("category") String category, @Field("level") String level);

    @FormUrlEncoded
    @POST("demo/Content.php")
    Call<List<Information>> getcontent(@Field("name") String name);

    @FormUrlEncoded
    @POST("demo/Datacollect.php")
    Call<List<Information>> getdata(@Field("style") String style);

    @FormUrlEncoded
    @POST("demo/Material.php")
    Call<String> getmaterial(@Field("name") String name, @Field("content") String content, @Field("style") String style);

    @POST("demo/Modules.php")
    Call<List<Information>> getmodules();

    @FormUrlEncoded
    @POST("demo/Questions.php")
    Call<List<Questions>> getquestions(@Field("name") String name);

    @FormUrlEncoded
    @POST("demo/User.php")
    Call<User> getuser(@Field("username") String username);

    @FormUrlEncoded
    @POST("demo/Login.php")
    Call<String> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("demo/Scoreload.php")
    Call<String> scoreload(@Field("username") String username, @Field("module") String module);

    @FormUrlEncoded
    @POST("demo/Scoreupdate.php")
    Call<ResponseBody> scoreupdate(@Field("username") String username, @Field("module") String module,
                                   @Field("style") String style, @Field("score") String score);

    @FormUrlEncoded
    @POST("demo/Settingsupdate.php")
    Call<ResponseBody> settingsupdate(@Field("username") String username, @Field("style") String style,
                                 @Field("fontsize") String fontsize, @Field("speechrate") String speechrate);

    @FormUrlEncoded
    @POST("demo/Signup.php")
    Call<String> signup(@Field("username") String username, @Field("password") String password,
                        @Field("style") String style, @Field("fontsize") String fontsize,
                        @Field("speechrate") String speechrate, @Field("role") String role);
}
