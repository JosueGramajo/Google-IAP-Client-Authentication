import retrofit.RetrofitServices

fun main(){
    var refreshToken = ""
    var accessToken = ""

    //AUTH CODE
    //This code is obtained by login with an authorized email in the following url type:
    //      https://accounts.google.com/o/oauth2/v2/auth?client_id=[REPLACE WITH CLIENT ID]&response_type=code&scope=openid%20email&access_type=offline&redirect_uri=urn:ietf:wg:oauth:2.0:oob
    val dummyAuthorizationCode = "4/ggEpsj4xwv7m3ZQJVdDgAHa0r-GuWA_-VoqPqCxiskAB7iUfGDodvBY"

    //REFRESH TOKEN
    //(Theoretically, this token only needs to be obtained once, an then keep using the same, that's because it doesn't expires )
    val refreshTokenResult = RetrofitServices.getRefreshToken(dummyAuthorizationCode)
    if (refreshTokenResult.success){
        refreshToken = refreshTokenResult.value!!
    }else{
        println(refreshTokenResult.errorMessage)
    }

    //ACCESS TOKEN
    val accessTokenResult = RetrofitServices.getAccessToken(refreshToken)
    if (accessTokenResult.success){
        accessToken = accessTokenResult.value!!
    }else{
        println(accessTokenResult.errorMessage)
        return
    }

    //AUTHORIZED CALL
    val serviceResult = RetrofitServices.testService(accessToken)

}

