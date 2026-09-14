## keep dto classes
-keep class com.deskvestre.fieldopstracker.data.remote.dto.** {*;}
## keep room entities
-keep class com.deskvestre.fieldopstracker.data.local.entity.** {*;}
## Know rules to keep Gson in general
-keepattributes Signature
-keepattributes Signature, Exception
-keepattributes *Annotation*
## Avoid R8 over optimization service interfaces with suspend functions
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**