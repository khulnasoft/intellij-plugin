package dev.khulnasoft.intellij.settings

import com.intellij.openapi.ui.TextComponentAccessor
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class SettingsPanel {
    private val myMainPanel: JPanel
    private val enableStatus: JBCheckBox = JBCheckBox("Enable Khulnasoft plugin", true)
    private val file = TextFieldWithBrowseButton()

    init {
        myMainPanel = FormBuilder.createFormBuilder()
            .addSeparator()
            .addLabeledComponent("Enable plugin", enableStatus)
            .addLabeledComponent("Path to Khulnasoft binary", file)
            .addComponent(JBLabel("If changing the Khulnasoft binary, you will need to reopen your project to pickup the new version"))
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPanel(): JPanel {
        file.addBrowseFolderListener(
            "",
            "Path to khulnasoft binary",
            null,
            khulnasoftBinaryDescriptor(),
            TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT,
        )

        return myMainPanel
    }

    fun getPreferredFocusedComponent(): JComponent {
        return enableStatus
    }

    fun getEnable(): Boolean {
        return enableStatus.isSelected
    }

    fun setEnable(newStatus: Boolean) {
        enableStatus.isSelected = newStatus
    }

    fun getFile(): String {
        return file.text
    }

    fun setFile(newFile: String) {
        file.text = newFile
    }
}
