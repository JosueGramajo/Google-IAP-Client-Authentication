package retrofit

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import objects.AppengineCredentials
import objects.ServiceResult

object RetrofitServices{
    private val googleApisBaseURL = "https://www.googleapis.com/"
    private lateinit var clientID : String
    private lateinit var clientSecret : String
    private lateinit var iapClientID : String

    init {
        initCredentials()
    }

    private fun initCredentials(){
        //There are two ways of initialize these credentials

        //Download both the oauth credentials and the iap credentials json file, this can be found in the credentials page, selecting one and in the top there is a "DOWNLOAD JSON" button
        //  then put both of them in the resources folder an load them as objects.
        val oauthJson = AppengineCredentials::class.java.getResourceAsStream("/oauthCredentials.json")
        val oauthObject = ObjectMapper().readValue<AppengineCredentials>(oauthJson)

        clientID = oauthObject.web.clientId!!
        clientSecret = oauthObject.web.clientSecret!!

        val iapJson = AppengineCredentials::class.java.getResourceAsStream("/iapCredentials.json")
        val iapObject = ObjectMapper().readValue<AppengineCredentials>(iapJson)

        iapClientID = iapObject.web.clientId!!


        //Or manually search for the credentials clientID and client secret and hard-code them in te variables
        //clientID = "[REPLACE WITH CLIENT ID]"
        //clientSecret = "[REPLACE WITH CLIENT SECRET]"
        //iapClientID = "[REPLACE WITH IAP CLIENT ID]"
    }

    fun getRefreshToken(code : String) : ServiceResult{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(googleApisBaseURL)
            .build()

        val service = retrofit.create(RefreshTokenServiceInterface::class.java)

        val call = service.getRefreshToken(
            clientID,
            clientSecret,
             code,
            "urn:ietf:wg:oauth:2.0:oob",
            "authorization_code")
        val response = call.execute()

        response.errorBody()?.let {
            return ServiceResult(false, null, it.string())
        } ?: run {
            return ServiceResult(true, response.body()!!.refreshToken, null)
        }
    }

    fun getAccessToken(refreshToken: String) : ServiceResult{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(googleApisBaseURL)
            .build()

        val service = retrofit.create(AccessTokenServiceInterface::class.java)

        val call = service.getToken(
            clientID,
            clientSecret,
            refreshToken,
            "refresh_token",
            iapClientID)

        val response = call.execute()

        response.errorBody()?.let {
            return ServiceResult(false, null, it.string())
        } ?: run {
            return ServiceResult(true, response.body()!!.idToken, null)
        }
    }

    fun testService(accessToken: String) : ServiceResult{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://appstoretestapp.appspot.com/")
            .build()

        val service = retrofit.create(EmitirFacturaInterface::class.java)

        val call = service.getDemoService("Bearer $accessToken")

        val response = call.execute()

        response.errorBody()?.let {
            return ServiceResult(false, null, it.string())
        } ?: run {
            return ServiceResult(true, response.body()!!.string(), null)
        }
    }
}