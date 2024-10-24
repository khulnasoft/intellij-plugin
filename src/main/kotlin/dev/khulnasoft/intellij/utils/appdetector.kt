package dev.khulnasoft.intellij.utils

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.project.rootManager
import com.intellij.psi.PsiDirectory
import dev.khulnasoft.intellij.settings.settingsState

const val KhulnasoftAppFile = "khulnasoft.app"

fun isInKhulnasoftApp(dir: PsiDirectory?): Boolean {
    if (!settingsState().enabled) {
        return false
    }

    if (dir == null) {
        return false
    }

    val appFile = dir.findFile(KhulnasoftAppFile)
    if (appFile != null && appFile.isValid) {
        return true
    }

    return isInKhulnasoftApp(dir.parentDirectory)
}

fun isInKhulnasoftApp(project: Project): Boolean {
    if (!settingsState().enabled) {
        return false
    }

    // Check if the projects root directory has an khulnasoft.app file
    val projectDir = project.guessProjectDir() ?: return false
    val appFile = projectDir.findChild(KhulnasoftAppFile)
    if (appFile != null && appFile.exists()) {
        return true
    }

    // Then check the projects modules
    return ModuleManager.getInstance(project).modules.any { module ->
        module.rootManager.contentRoots.any { folder ->
            folder.findChild(KhulnasoftAppFile)?.exists() == true
        }
    }
}
