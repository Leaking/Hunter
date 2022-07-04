object Versions {
    val hunter_common_version = "1.2.1"

    val hunter_transform = hunter_common_version

    val debug_library = hunter_common_version
    val debug_plugin = hunter_common_version

    val linelog_library = hunter_common_version
    val linelog_plugin = hunter_common_version

    val okhttp_library = hunter_common_version
    val okhttp_library_compat = hunter_common_version
    val okhttp_plugin = hunter_common_version

    val timing_library = hunter_common_version
    val timing_plugin = hunter_common_version

    val AGP = "4.1.3"
    val AGP_API = "4.1.0"
    val ASM = "7.1"
    val common_io = "2.6"
}

object PublishVersions {
    val hunter_common_publish_version = "1.2.1"

    val hunter_transform = hunter_common_publish_version

    val debug_library = hunter_common_publish_version
    val debug_plugin = hunter_common_publish_version

    val linelog_library = hunter_common_publish_version
    val linelog_plugin = hunter_common_publish_version

    val okhttp_library = hunter_common_publish_version
    val okhttp_library_compat = hunter_common_publish_version
    val okhttp_plugin = hunter_common_publish_version

    val timing_library = hunter_common_publish_version
    val timing_plugin = hunter_common_publish_version
}
//object Libs {
//    val hunter_debug_library = "com.quinn.hunter:hunter-debug-library:${Versions.hunter_debug_library}"
//}
//object Plugins {
//    val hunter_debug_plugin = "com.quinn.hunter:hunter-debug-plugin:${Versions.hunter_debug_plugin}"
//}

object Group {
    val commonGrouop = "cn.quinnchen.hunter"
}
object Artifacts {
    val okhttp_library_compat = "hunter-debug-library-compat"
    val hunter_transform = "hunter-transform"

    val debug_library = "hunter-debug-library"
    val okhttp_library = "hunter-okhttp-library"
    val linelog_library = "hunter-linelog-library"
    val timing_library = "hunter-timing-library"

    val debug_plugin = "hunter-debug-plugin"
    val okhttp_plugin = "hunter-okhttp-plugin"
    val linelog_plugin = "hunter-linelog-plugin"
    val timing_plugin = "hunter-timing-plugin"
}

//object Libs {
//    val hunter_debug_library = "project(':hunter-debug-library')"
//    val hunter_okhttp_library = "project(':hunter-okhttp-library')"
//    val hunter_okhttp_library_compat = "project(':hunter-okhttp-library-compat')"
//    val hunter_linelog_library = "project(':hunter-linelog-library')"
//    val hunter_timing_library = "project(':hunter-timing-library')"
//}

object Libs {
    val hunter_debug_library = "${Group.commonGrouop}:${Artifacts.debug_library}:${Versions.debug_library}"
    val hunter_okhttp_library = "${Group.commonGrouop}:${Artifacts.okhttp_library}:${Versions.okhttp_library}"
    val hunter_okhttp_library_compat = "${Group.commonGrouop}:${Artifacts.okhttp_library_compat}:${Versions.okhttp_library_compat}"
    val hunter_linelog_library = "${Group.commonGrouop}:${Artifacts.linelog_library}:${Versions.linelog_library}"
    val hunter_timing_library = "${Group.commonGrouop}:${Artifacts.timing_library}:${Versions.timing_library}"
    val AGP = "com.android.tools.build:gradle:${Versions.AGP}"
    val AGP_API = "com.android.tools.build:gradle-api:${Versions.AGP_API}"
    val asm = "org.ow2.asm:asm:${Versions.ASM}"
    val asm_util = "org.ow2.asm:asm-util:${Versions.ASM}"
    val asm_commons = "org.ow2.asm:asm-commons:${Versions.ASM}"
    val common_io = "commons-io:commons-io:${Versions.common_io}"
}

object Plugins {
    val hunter_transform = "${Group.commonGrouop}:${Artifacts.hunter_transform}:${Versions.hunter_transform}"
    val hunter_debug_plugin = "${Group.commonGrouop}:${Artifacts.debug_plugin}:${Versions.debug_plugin}"
    val hunter_okhttp_plugin = "${Group.commonGrouop}:${Artifacts.okhttp_plugin}:${Versions.okhttp_plugin}"
    val hunter_linelog_plugin = "${Group.commonGrouop}:${Artifacts.linelog_plugin}:${Versions.linelog_plugin}"
    val hunter_timing_plugin = "${Group.commonGrouop}:${Artifacts.timing_plugin}:${Versions.timing_plugin}"
}

object Android {
    val compileSdkVersion = 29
    val buildToolsVersion = "29.0.3"
    val minSdkVersion = 15
    val targetSdkVersion = 29
    val versionCode = 1
    val  versionName = "1.0"
}


