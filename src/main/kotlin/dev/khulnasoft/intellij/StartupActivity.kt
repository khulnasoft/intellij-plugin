package dev.khulnasoft.intellij

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.util.text.SemVer
import dev.khulnasoft.intellij.sqldb.Detector
import dev.khulnasoft.intellij.utils.isInKhulnasoftApp
import dev.khulnasoft.intellij.utils.runKhulnasoftCommand

/*
 * Startup Activity allows us to run blocking external calls to the khulnasoft daemon to extract information
 * about the project, which we can then store and reference at runtime
 */
class StartupActivity : StartupActivity.Background, StartupActivity.RequiredForSmartMode {
    override fun runActivity(project: Project) {
        // Skip our startup if it's not an Khulnasoft app
        if (!isInKhulnasoftApp(project)) {
            return
        }

        Khulnasoft.LOG.info("Performing startup activity for Khulnasoft...")

        // Verify Khulnasoft is installed
        val versionOutput = runKhulnasoftCommand(project, "version") ?: return
        if (!versionOutput.startsWith("khulnasoft version ")) {
            return
        }
        Khulnasoft.khulnasoftInstalled = true

        // Get the version
        val versionString = versionOutput.trim().lines()[0].removePrefix("khulnasoft version ")
        if (versionString.startsWith("v")) {
            Khulnasoft.khulnasoftVersion = SemVer.parseFromText(versionString.removePrefix("v"))
        } else {
            // If the version string doesn't start with v then it's a dev build, so default to 999.999.999
            Khulnasoft.khulnasoftVersion = SemVer(versionString, 999, 999, 999)
        }
        Khulnasoft.LOG.info("Verified that Khulnasoft is installed and running version ${Khulnasoft.khulnasoftVersion}")

        // Ask khulnasoft for the database connection info
        if (Khulnasoft.isAtLeast(1, 9, 2)) {
            Khulnasoft.LOG.info("Getting database connection information from Khulnasoft...")
            Detector.loadDatabaseConnection(project)
        }
    }
}
