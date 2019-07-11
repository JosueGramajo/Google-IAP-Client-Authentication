package retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface EmitirFacturaInterface{
    @GET("/demoService")
    fun getDemoService(
        @Header("Authorization") token : String
    ) : Call<ResponseBody>
}

interface AccessTokenServiceInterface {
    @POST("/oauth2/v4/token")
    @FormUrlEncoded
    fun getToken(
        @Field("client_id")
        clientId: String,

        @Field("client_secret")
        clientSecret: String,

        @Field("refresh_token")
        refreshToken: String,

        @Field("grant_type")
        grantType: String,

        @Field("audience")
        audience : String

    ): Call<TokenResponse>
}

interface RefreshTokenServiceInterface{
    @POST("/oauth2/v4/token")
    @FormUrlEncoded
    fun getRefreshToken(
        @Field("client_id")
        clientId : String,

        @Field("client_secret")
        clientSecret : String,

        @Field("code")
        code : String,

        @Field("redirect_uri")
        redirectUri : String,

        @Field("grant_type")
        grantType : String
    ) : Call<TokenResponse>
}