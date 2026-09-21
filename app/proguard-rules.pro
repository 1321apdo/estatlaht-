# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Preserve Coroutines and Room internals safely
-keepclassmembers class * extends androidx.room.RoomDatabase {
    <init>();
}

# Protect business logic and disguise class names
-repackageclasses 'com.secure.vault'
-allowaccessmodification

# Hide source file and line numbers in released builds
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Strip logging statements for user and developer privacy
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
}

# Preserve Room entities and DAOs
-keep class com.example.data.local.** { *; }
-keep interface com.example.data.local.** { *; }
