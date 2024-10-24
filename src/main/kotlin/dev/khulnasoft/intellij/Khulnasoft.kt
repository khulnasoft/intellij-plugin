package dev.khulnasoft.intellij

import com.intellij.openapi.diagnostic.Logger
import com.intellij.util.text.SemVer

object Khulnasoft {
    val LOG: Logger = Logger.getInstance(Khulnasoft.javaClass)

    /* Is khulnasoft installed? */
    @Volatile
    var khulnasoftInstalled = false

    /* What version is Khulnasoft? */
    @Volatile
    var khulnasoftVersion: SemVer? = null

    /*
    Returns true if Khulnasoft is at least at the given release
     */
    fun isAtLeast(major: Int, minor: Int, patch: Int): Boolean {
        val version = khulnasoftVersion ?: return false

        return version.isGreaterOrEqualThan(major, minor, patch)
    }
}
