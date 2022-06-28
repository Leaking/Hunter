object Versions {
    val hunter_transform = "1.2.1"

    val debug_library = "1.2.0"
    val debug_plugin = "1.2.0"

    val linelog_library = "1.2.0"
    val linelog_plugin = "1.2.0"

    val okhttp_library = "1.2.0"
    val okhttp_library_compat = "1.2.0"
    val okhttp_plugin = "1.2.0"

    val timing_library = "1.2.0"
    val timing_plugin = "1.2.0"
}

object PublishVersions {
    val hunter_transform = "1.2.1"

    val debug_library = "1.2.0"
    val debug_plugin = "1.2.0"

    val linelog_library = "1.2.0"
    val linelog_plugin = "1.2.0"

    val okhttp_library = "1.2.0"
    val okhttp_library_compat = "1.2.0"
    val okhttp_plugin = "1.2.0"

    val timing_library = "1.2.0"
    val timing_plugin = "1.2.0"
}
//object Libs {
//    val hunter_debug_library = "com.quinn.hunter:hunter-debug-library:${Versions.hunter_debug_library}"
//}
//object Plugins {
//    val hunter_debug_plugin = "com.quinn.hunter:hunter-debug-plugin:${Versions.hunter_debug_plugin}"
//}

object Group {
    val commonGrouop = "com.quinn.hunter"
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
}

object Plugins {
    val hunter_transform = "${Group.commonGrouop}:${Artifacts.hunter_transform}:${Versions.hunter_transform}"
    val hunter_debug_plugin = "${Group.commonGrouop}:${Artifacts.debug_plugin}:${Versions.debug_plugin}"
    val hunter_okhttp_plugin = "${Group.commonGrouop}:${Artifacts.okhttp_plugin}:${Versions.okhttp_plugin}"
    val hunter_linelog_plugin = "${Group.commonGrouop}:${Artifacts.linelog_plugin}:${Versions.linelog_plugin}"
    val hunter_timing_plugin = "${Group.commonGrouop}:${Artifacts.timing_plugin}:${Versions.timing_plugin}"
}


