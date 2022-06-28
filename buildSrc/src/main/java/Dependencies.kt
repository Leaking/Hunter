object Versions {
    val hunter_debug_library = "1.2.0"
    val hunter_debug_plugin = "1.2.0"
}
//object Libs {
//    val hunter_debug_library = "com.quinn.hunter:hunter-debug-library:${Versions.hunter_debug_library}"
//}
//object Plugins {
//    val hunter_debug_plugin = "com.quinn.hunter:hunter-debug-plugin:${Versions.hunter_debug_plugin}"
//}

object Libs {
    val hunter_debug_library = "project(':hunter-debug-library')"
    val hunter_okhttp_library = "project(':hunter-okhttp-library')"
    val hunter_okhttp_library_compat = "project(':hunter-okhttp-library-compat')"
    val hunter_linelog_library = "project(':hunter-linelog-library')"
    val hunter_timing_library = "project(':hunter-timing-library')"
}

object Plugins {
    val hunter_debug_plugin = "project(':hunter-debug-plugin')"
    val hunter_okhttp_plugin = "project(':hunter-okhttp-plugin')"
    val hunter_linglog_plugin = "project(':hunter-linelog-plugin')"
    val hunter_timing_plugin = "project(':hunter-timing-plugin')"
    val hunter_transform = "project(':hunter-transform')"
}


