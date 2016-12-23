package com.w8.w8monitor.android.api;


import com.w8.w8monitor.android.api.model.Measurement;
import com.w8.w8monitor.android.api.model.ObjectMeasurement;
import com.w8.w8monitor.android.api.model.ProfileApi;
import com.w8.w8monitor.android.api.model.UserApi;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by AleksandrP on 16.11.2016.
 */

public interface ServiceApi {

    /**
     * login in server
     *
     * @param email
     * @param mpassword
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Call<UserApi> login(
            @Field("email") String email,
            @Field(("password")) String mpassword);


    /**
     * login in server ever social networks
     *
     * @param email
     * @param social_user_id
     * @return
     */
    @FormUrlEncoded
    @POST("login/social")
    Call<UserApi> loginSocial(
            @Field("email") String email,
            @Field(("social_user_id")) String social_user_id,
            @Field(("name")) String name);


    /**
     * register in server
     *
     * @param email
     * @param mpassword
     * @return
     */
    @FormUrlEncoded
    @POST("register")
    Call<UserApi> register(
            @Field("email") String email,
            @Field(("password")) String mpassword);


    /**
     * created profile in server
     *
     * @param profile
     * @param activity_type
     * @param height
     * @param gender
     * @return
     */
    @FormUrlEncoded
    @POST("profiles/{profile}")
    Call<ProfileApi> profileCreate(
            @Path("profile") int profile,
            @Field("activity_type") int activity_type,
            @Field("height") int height,
            @Field("is_current") int is_current,
            @Field("age") int age,
            @Field(("gender")) int gender);


    /**
     * send measurements at server
     *
     * @param bmi
     * @param body_water
     * @param bone_mass
     * @param calories
     * @param fat
     * @param fat_level
     * @param muscle_mass
     * @param float_weight
     * @param created_at
     * @return
     */
    @FormUrlEncoded
    @POST("profiles/{id_profile}/measurements")
    Call<Measurement> measurements(
            @Path("id_profile") int id_profile,
            @Field("bmi") String bmi,
            @Field("body_water") String body_water,
            @Field("bone_mass") String bone_mass,
            @Field("calories") String calories,
            @Field("fat") String fat,
            @Field("fat_level") String fat_level,
            @Field("muscle_mass") String muscle_mass,
            @Field("float_weight") String float_weight,
            @Field("created_at") String created_at);


    @GET("profiles/{id_profile}/measurements")
    Call<ObjectMeasurement> genAllMeasurements(
            @Path("id_profile") int id_profile);

    @GET("profiles/{id_profile}/measurements")
    Call<ObjectMeasurement> genAllMeasurementsTimestamp(
            @Path("id_profile") int id_profile,
            @Query("timestamp") String timestamp);


    /**
     * first send and created in server
     *
     * @return
     */
    @FormUrlEncoded
    @POST("profiles/{id_profile}/measurements/mass")
    Call<ObjectMeasurement> measurements_mass(
            @Path("id_profile") int id_profile,
            @FieldMap Map<String, Object> mParamsBodies);

    /**
     * change password
     *
     * @param current_password
     * @param password
     * @param password_confirmation
     * @return
     */
    @FormUrlEncoded
    @POST("password")
    Call<UserApi> password(
            @Field("password") String password,
            @Field("current_password") String current_password,
            @Field(("password_confirmation")) String password_confirmation);


    /**
     * update data profile
     *
     * @param id
     * @param user_id
     * @param activity_type
     * @param height
     * @param gender
     * @param birthday
     * @param created_at
     * @param updated_at
     * @return
     */
    @FormUrlEncoded
    @POST("profiles/{id}/one")
    Call<ProfileApi> updateProfile(
            @Path("id") int id,
            @Path("user_id") int user_id,
            @Field("activity_type") int activity_type,
            @Field("height") int height,
            @Field("gender") int gender,
            @Field("birthday") int birthday,
            @Field("created_at") String created_at,
            @Field(("updated_at")) String updated_at);


    @FormUrlEncoded
    @POST("profiles/sync")
    Call<UserApi> profileSync(
            @Field("is_imperial") int is_imperial,
            @Field("keep_login") int keep_login,
            @Field("theme") int theme,
            @Field("language") String language,
            @Field("profile_number") int profile_number,
            @FieldMap Map<String, Object> mProfiles);


    @FormUrlEncoded
    @POST("alarms")
    Call<UserApi> updateAlarms(
            @FieldMap Map<String, Object> mAlarms);

    @FormUrlEncoded
    @POST("password/reset")
    Call<Object> resetPassword(
            @Field("email") String email);


    @FormUrlEncoded
    @POST("support")
    Call<Object> sendQuestion(
            @Field("email") String email,
            @Field("body") String body);

}
