package comtest.ct.cd.yoasfebrianus.data.pojo

import com.google.gson.annotations.SerializedName

data class UserPojo(

    @SerializedName("repos_url")
    val reposUrl: String = "",

    @SerializedName("login")
    val login: String = "",

    @SerializedName("followers_url")
    val followersUrl: String = "",

    @SerializedName("type")
    val type: String = "",

    @SerializedName("url")
    val url: String = "",

    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String = "",

    @SerializedName("score")
    val score: Double = 0.0,

    @SerializedName("received_events_url")
    val receivedEventsUrl: String = "",

    @SerializedName("avatar_url")
    val avatarUrl: String = "",

    @SerializedName("html_url")
    val htmlUrl: String = "",

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("gravatar_id")
    val gravatarId: String = "",

    @SerializedName("node_id")
    val nodeId: String = "",

    @SerializedName("organizations_url")
    val organizationsUrl: String = "",

    @SerializedName("name")
    val name: String = ""


)