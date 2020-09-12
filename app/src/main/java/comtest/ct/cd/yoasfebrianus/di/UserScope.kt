package comtest.ct.cd.yoasfebrianus.di

import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class UserScope {
}