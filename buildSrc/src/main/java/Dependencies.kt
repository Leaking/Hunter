object Versions {
    const val hunter_common_version = "1.3.0"

    const val hunter_transform = hunter_common_version

    const val debug_library = hunter_common_version
    const val debug_plugin = hunter_common_version

    const val linelog_library = hunter_common_version
    const val linelog_plugin = hunter_common_version

    const val okhttp_library = hunter_common_version
    const val okhttp_library_compat = hunter_common_version
    const val okhttp_plugin = hunter_common_version

    const val timing_library = hunter_common_version
    const val timing_plugin = hunter_common_version

    const val AGP = "8.7.0"
    const val ASM = "9.7"
}

object PublishVersions {
    const val hunter_common_publish_version = "1.3.0"

    const val hunter_transform = hunter_common_publish_version

    const val debug_library = hunter_common_publish_version
    const val debug_plugin = hunter_common_publish_version

    const val linelog_library = hunter_common_publish_version
    const val linelog_plugin = hunter_common_publish_version

    const val okhttp_library = hunter_common_publish_version
    const val okhttp_library_compat = hunter_common_publish_version
    const val okhttp_plugin = hunter_common_publish_version

    const val timing_library = hunter_common_publish_version
    const val timing_plugin = hunter_common_publish_version
}

object Group {
    const val commonGrouop = "cn.quinnchen.hunter"
}

object Artifacts {
    const val okhttp_library_compat = "hunter-okhttp-library-compat"
    const val hunter_transform = "hunter-transform"

    const val debug_library = "hunter-debug-library"
    const val okhttp_library = "hunter-okhttp-library"
    const val linelog_library = "hunter-linelog-library"
    const val timing_library = "hunter-timing-library"

    const val debug_plugin = "hunter-debug-plugin"
    const val okhttp_plugin = "hunter-okhttp-plugin"
    const val linelog_plugin = "hunter-linelog-plugin"
    const val timing_plugin = "hunter-timing-plugin"
}

object Libs {
    const val hunter_debug_library = "${Group.commonGrouop}:${Artifacts.debug_library}:${Versions.debug_library}"
    const val hunter_okhttp_library = "${Group.commonGrouop}:${Artifacts.okhttp_library}:${Versions.okhttp_library}"
    const val hunter_okhttp_library_compat = "${Group.commonGrouop}:${Artifacts.okhttp_library_compat}:${Versions.okhttp_library_compat}"
    const val hunter_linelog_library = "${Group.commonGrouop}:${Artifacts.linelog_library}:${Versions.linelog_library}"
    const val hunter_timing_library = "${Group.commonGrouop}:${Artifacts.timing_library}:${Versions.timing_library}"
    const val AGP = "com.android.tools.build:gradle:${Versions.AGP}"
    const val AGP_API = "com.android.tools.build:gradle-api:${Versions.AGP}"
    const val asm = "org.ow2.asm:asm:${Versions.ASM}"
    const val asm_util = "org.ow2.asm:asm-util:${Versions.ASM}"
    const val asm_commons = "org.ow2.asm:asm-commons:${Versions.ASM}"
    const val asm_tree = "org.ow2.asm:asm-tree:${Versions.ASM}"
}

object Plugins {
    const val hunter_transform = "${Group.commonGrouop}:${Artifacts.hunter_transform}:${Versions.hunter_transform}"
    const val hunter_debug_plugin = "${Group.commonGrouop}:${Artifacts.debug_plugin}:${Versions.debug_plugin}"
    const val hunter_okhttp_plugin = "${Group.commonGrouop}:${Artifacts.okhttp_plugin}:${Versions.okhttp_plugin}"
    const val hunter_linelog_plugin = "${Group.commonGrouop}:${Artifacts.linelog_plugin}:${Versions.linelog_plugin}"
    const val hunter_timing_plugin = "${Group.commonGrouop}:${Artifacts.timing_plugin}:${Versions.timing_plugin}"
}

object Android {
    const val compileSdkVersion = 34
    const val buildToolsVersion = "34.0.0"
    const val minSdkVersion = 21
    const val targetSdkVersion = 34
    const val versionCode = 1
    const val versionName = "1.0"
}
