# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keepclassmembers class ** extends com.airbnb.mvrx.MavericksViewModel {
    public <init>(...);
    public static *** create(...);
    public static *** initialState(...);
}

# If a MvRxViewModelFactory is used without JvmStatic, keep create and initalState methods which
# are accessed via reflection.
-keepclassmembers class ** implements com.airbnb.mvrx.MavericksViewModelFactory {
     public <init>(...);
     public *** create(...);
     public *** initialState(...);
}

# The constructor as well as the copy$default() method and the component*() methods of the Kotlin data classes used as
# the state in MvRx are read via reflection which cause trouble with Proguard if they are not kept.
-keepclassmembers class ** implements com.airbnb.mvrx.MavericksState {
   public <init>(...);
   synthetic *** copy$default(...);
   public *** component*();
}
