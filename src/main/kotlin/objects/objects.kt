package objects

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

//Service result object
data class ServiceResult(
    var success : Boolean,

    var value : String?,

    var errorMessage : String?
)


//AppEngine credentials objects
data class AppengineCredentials(
    @JsonProperty("web")
    var web : Web
)

@JsonRootName("web")
data class Web(
    @JsonProperty("client_id")
    var clientId : String?,

    @JsonProperty("project_id")
    var projectId : String?,

    @JsonProperty("auth_uri")
    var authUri : String?,

    @JsonProperty("token_uri")
    var tokenUri : String?,

    @JsonProperty("auth_provider_x509_cert_url")
    var certUrl : String?,

    @JsonProperty("client_secret")
    var clientSecret : String?,

    @JsonProperty("redirect_uris")
    var redirectUris : List<String>?
)