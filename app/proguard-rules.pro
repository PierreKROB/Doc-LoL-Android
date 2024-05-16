# Règles pour supprimer les avertissements liés aux classes manquantes
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE

# Retrofit
-keep interface retrofit2.http.** { *; }
-keepattributes Signature, RuntimeVisibleAnnotations, AnnotationDefault
-keep class retrofit2.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.TypeToken { *; }
-keepattributes Signature

# Garder les informations de type générique pour Retrofit et Gson
-keepattributes Signature
-keepattributes Exceptions

# Garder les classes de modèles et ViewModels
-keep class com.pkrob.ApiDocLoL.model.** { *; }
-keep class com.pkrob.ApiDocLoL.viewmodel.** { *; }

# Garder les attributs de ProGuard pour les classes de modèles et ViewModels
-keepattributes RuntimeVisibleAnnotations, AnnotationDefault
