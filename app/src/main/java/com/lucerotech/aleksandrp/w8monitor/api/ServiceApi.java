package com.lucerotech.aleksandrp.w8monitor.api;

import com.lucerotech.aleksandrp.w8monitor.api.model.Measurement;
import com.lucerotech.aleksandrp.w8monitor.api.model.ProfileApi;
import com.lucerotech.aleksandrp.w8monitor.api.model.UserApi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    @POST("login")
    Call<UserApi> loginSocial(
            @Field("email") String email,
            @Field(("social_user_id")) String social_user_id);


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
     * @param birthday
     * @param gender
     * @return
     */
    @FormUrlEncoded
    @POST("profiles/{profile}")
    Call<ProfileApi> profileCreate(
            @Path("profile") int profile,
            @Field("activity_type") int activity_type,
            @Field("height") int height,
            @Field("birthday") int birthday,
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
    @POST("measurements")
    Call<Measurement> measurements(
            @Field("bmi") String bmi,
            @Field("body_water") String body_water,
            @Field("bone_mass") String bone_mass,
            @Field("calories") String calories,
            @Field("fat") String fat,
            @Field("fat_level") String fat_level,
            @Field("muscle_mass") String muscle_mass,
            @Field("float_weight") String float_weight,
            @Field("created_at") String created_at);


    /**
     * first send and created in server
     *
     * @param bmi
     * @param body_water
     * @param bone_mass
     * @param calories
     * @param fat
     * @param fat_level
     * @param muscule_mass
     * @param float_weight
     * @param gender
     * @return
     */
    @FormUrlEncoded
    @POST("measurements/mass")
    Call<Measurement> measurements_mass(
            @Field("bmi") String bmi,
            @Field("body_water") String body_water,
            @Field("bone_mass") String bone_mass,
            @Field("calories") String calories,
            @Field("fat") String fat,
            @Field("fat_level") String fat_level,
            @Field("muscule_mass") String muscule_mass,
            @Field("float_weight") String float_weight,
            @Field(("gender")) String gender);

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
    Call<Object> password(
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


}
