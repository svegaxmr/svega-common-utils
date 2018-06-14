package com.svega.common.version

open class Version(val major: Int, val minor: Int, val bugfix: Int, val extra: String){
    val versionString = "$major.$minor.$bugfix $extra"

    companion object {
        fun isVersionSupported(version: Version, major: Int, minMinor: Int): Boolean{
            if(version.major != major)
                return false
            return version.minor >= minMinor
        }
        fun makeExtra(extra: String, number: Int) = "$extra-$number"
        fun makeExtra(extra: Extra, number: Int) = "${extra.string}-$number"
    }
}

enum class Extra(val string: String){
    ALPHA("alpha"),
    BETA("beta"),
    INTERNAL("internal"),
    STABLE("stable")
}