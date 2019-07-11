package retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("access_token")
    @Expose
    var accessToken : String,

    @SerializedName("expires_in")
    @Expose
    var expiresIn : Long,

    @SerializedName("refresh_token")
    @Expose
    var refreshToken : String,

    @SerializedName("scope")
    @Expose
    var scope : String,

    @SerializedName("token_type")
    @Expose
    var tokenType : String,

    @SerializedName("id_token")
    @Expose
    var idToken : String
)