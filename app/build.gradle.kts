import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.flight.wallpaper2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.flight.wallwow"
        minSdk = 24
        targetSdk = 34
        versionCode = 7
        versionName = "1.0.7"
        archivesName = "WallWow_v${versionName}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("io.github.cymchad:BaseRecyclerViewAdapterHelper:4.0.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.work:work-runtime-ktx:2.8.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-config-ktx")
    // implementation ("com.google.firebase:firebase-crashlytics-ktx")
    //facebook
    implementation("com.facebook.android:facebook-android-sdk:latest.release")
    implementation("com.android.installreferrer:installreferrer:2.2")
    //adjust
    implementation("com.adjust.sdk:adjust-android:4.33.4")
    implementation("com.adjust.sdk:adjust-android-webbridge:4.33.4")

    //ad
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    //google play necessary
    implementation("com.google.android.gms:play-services-basement:18.1.0")
    implementation("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation("com.google.android.gms:play-services-appset:16.0.2")

    //Anythink (Necessary)
    implementation("com.anythink.sdk:core:6.2.81")
    implementation("com.anythink.sdk:nativead:6.2.81")
    implementation("com.anythink.sdk:banner:6.2.81")
    implementation("com.anythink.sdk:interstitial:6.2.81")
    implementation("com.anythink.sdk:rewardedvideo:6.2.81")
    implementation("com.anythink.sdk:splash:6.2.81")

    //Vungle
    implementation("com.anythink.sdk:adapter-vungle:6.2.81")
    implementation("com.vungle:vungle-ads:7.0.0")


    //UnityAds
    implementation("com.anythink.sdk:adapter-unityads:6.2.81")
    implementation("com.unity3d.ads:unity-ads:4.9.2")

    //Ironsource
    implementation("com.anythink.sdk:adapter-ironsource:6.2.81")
    implementation("com.ironsource.sdk:mediationsdk:7.5.2")


    //Bigo
    implementation("com.anythink.sdk:adapter-bigo:6.2.81")
    implementation("com.bigossp:bigo-ads:4.2.0")

    //Pangle
    implementation("com.anythink.sdk:adapter-pangle-nonchina:6.2.81")
    implementation("com.pangle.global:ads-sdk:5.6.0.1")

    //Inmobi
    implementation("com.anythink.sdk:adapter-inmobi:6.2.81")
    implementation("com.inmobi.monetization:inmobi-ads-kotlin:10.6.2")

    //AppLovin
    implementation("com.anythink.sdk:adapter-applovin:6.2.81")
    implementation("com.applovin:applovin-sdk:12.0.0")

    //Mintegral
    implementation("com.anythink.sdk:adapter-mintegral-nonchina:6.2.81")
    implementation("com.mbridge.msdk.oversea:reward:16.5.61")
    implementation("com.mbridge.msdk.oversea:newinterstitial:16.5.61")
    implementation("com.mbridge.msdk.oversea:mbnative:16.5.61")
    implementation("com.mbridge.msdk.oversea:mbnativeadvanced:16.5.61")
    implementation("com.mbridge.msdk.oversea:mbsplash:16.5.61")
    implementation("com.mbridge.msdk.oversea:mbbanner:16.5.61")
    implementation("com.mbridge.msdk.oversea:mbbid:16.5.61")

    //Chartboost
    implementation("com.anythink.sdk:adapter-chartboost:6.2.81")
    implementation("com.chartboost:chartboost-sdk:9.5.0")
    implementation("com.chartboost:chartboost-mediation-sdk:4.7.0")
    implementation("com.chartboost:chartboost-mediation-adapter-chartboost:4.9.5.0.0")
    implementation("org.greenrobot:eventbus:3.3.1")

    //Fyber
    implementation("com.anythink.sdk:adapter-fyber:6.2.81")
    implementation("com.fyber:marketplace-sdk:8.2.3")

    //Tramini
    implementation("com.anythink.sdk:tramini-plugin:6.2.81")

    // implementation("com.picwish.daemon:daemon-boot:1.0")
    implementation("com.picwish.daemon:flight-wallwow:1.0")
}