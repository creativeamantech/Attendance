# Keep Google Sheets API models
-keep class com.company.attendanceapp.data.remote.sheets.dto.** { *; }
# Keep Retrofit interfaces
-keep,allowobfuscation interface * { @retrofit2.http.* <methods>; }
# Keep Moshi adapters
-keepclassmembers class * { @com.squareup.moshi.* <fields>; <methods>; }
# Keep Room entities
-keep @androidx.room.Entity class *
