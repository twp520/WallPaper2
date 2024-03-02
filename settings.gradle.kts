pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        flatDir {
            dirs("libs")
        }
        //Anythink(Core)
        maven("https://jfrog.anythinktech.com/artifactory/overseas_sdk")

        //Ironsource
        maven("https://android-sdk.is.com/")

        //Pangle
        maven("https://artifact.bytedance.com/repository/pangle")

        //Mintegral
        maven("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea")

        //Chartboost
        maven("https://cboost.jfrog.io/artifactory/chartboost-ads")

        maven("https://cboost.jfrog.io/artifactory/chartboost-mediation")

        maven("https://maven.aliyun.com/repository/public")
        maven("https://packages.aliyun.com/maven/repository/2454557-release-zor4QY/").credentials.apply {
                username = "65d2d49970561251c928645d"
                password = "xQL6L(INE6Gw"
            }
        maven("https://packages.aliyun.com/maven/repository/2454557-snapshot-rsqrz7/").credentials.apply {
                username = "65d2d49970561251c928645d"
                password = "xQL6L(INE6Gw"
            }
    }
}

rootProject.name = "WallPaper2"
include(":app")
