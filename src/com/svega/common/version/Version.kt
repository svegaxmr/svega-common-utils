package com.svega.common.version

open class Version(val major: Int, val minor: Int, val bugfix: Int, val extra: String){
    val versionString = "$major.$minor.$bugfix $extra"

    companion object {
        val loaded = HashMap<String, Version>() //string for package path, version of that
        @Throws(IllegalArgumentException::class, NullPointerException::class)
        fun requires(packageName: String, major: Int, minMinor: Int){
            if(loaded.containsKey(packageName)){
                val version = if (loaded[packageName] != null) loaded[packageName]!! else throw NullPointerException("Expression 'loaded.get(packageName)' must not be null")
                if(version.major != major)
                    throw VersionMismatchException("Package $packageName is major version ${version.major}, requested $major")
                if(version.minor < minMinor)
                    throw VersionMismatchException("Package $packageName is minor version ${version.minor}, requested minimum $minMinor")
                return
            }
            val clz = Class.forName("$packageName.Version")
            try{
                val version = clz.getConstructor().newInstance() as Version
                loaded[packageName] = version
                if(version.major != major)
                    throw VersionMismatchException("Package $packageName is major version ${version.major}, requested $major")
                if(version.minor < minMinor)
                    throw VersionMismatchException("Package $packageName is minor version ${version.minor}, requested minimum $minMinor")
            }catch(e: Exception){
                throw IllegalArgumentException("Package class Version is not supered by from com.svega.common.version!")
            }catch(e: RuntimeException){
                throw IllegalArgumentException("Package class Version is not supered by from com.svega.common.version!")
            }
        }

        fun requiresNoThrow(packageName: String, major: Int, minMinor: Int): Boolean{
            try{
                requires(packageName, major, minMinor)
            }catch (e: Exception) {
                return false
            }
            return true
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