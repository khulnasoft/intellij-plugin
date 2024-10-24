package dev.khulnasoft.intellij.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class Settings : Configurable {
    private var mySettingsComponent: SettingsPanel? = null

    override fun createComponent(): JComponent {
        mySettingsComponent = SettingsPanel()
        return mySettingsComponent!!.getPanel()
    }

    override fun isModified(): Boolean {
        val settings = settingsState()
        var modified = false
        modified = modified || mySettingsComponent!!.getEnable() != settings.enabled
        modified = modified || mySettingsComponent!!.getFile() != settings.khulnasoftBinary

        return modified
    }

    override fun apply() {
        val settings = settingsState()
        settings.enabled = mySettingsComponent!!.getEnable()
        settings.khulnasoftBinary = mySettingsComponent!!.getFile()
    }

    override fun reset() {
        val settings = settingsState()
        mySettingsComponent!!.setEnable(settings.enabled)
        mySettingsComponent!!.setFile(settings.khulnasoftBinary)
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }

    override fun getDisplayName(): String {
        return "Khulnasoft"
    }
}
