package comtest.ct.cd.yoasfebrianus.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("total_count")
    @Expose
    val totalCount: Int = 0,

    @SerializedName("incomplete_results")
    @Expose
    val incompleteResult: Boolean = false,

    @SerializedName("items")
    @Expose
    val userList: List<UserPojo> = mutableListOf()
)