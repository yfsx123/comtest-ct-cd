package comtest.ct.cd.yoasfebrianus.util.network

class OkHttpRetryPolicy(
    var readTimeout: Int,
    var writeTimeout: Int,
    var connectTimeout: Int,
    var maxRetryAttempt: Int
) {

    companion object {
        fun createdDefaultOkHttpRetryPolicy(): OkHttpRetryPolicy {
            return OkHttpRetryPolicy(45, 45, 45, 3)
        }

        fun createdOkHttpNoAutoRetryPolicy(): OkHttpRetryPolicy {
            return OkHttpRetryPolicy(45, 45, 45, 0)
        }

        fun createdOkHttpRetryPolicyQuickTimeOut(): OkHttpRetryPolicy {
            return OkHttpRetryPolicy(1, 1, 1, 0)
        }

        fun createdOkHttpRetryPolicyQuickNoRetry(): OkHttpRetryPolicy {
            return OkHttpRetryPolicy(45, 45, 45, 0)
        }
    }

}