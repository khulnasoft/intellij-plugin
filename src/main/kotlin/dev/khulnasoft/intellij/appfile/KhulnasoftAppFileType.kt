package dev.khulnasoft.intellij.appfile

import com.intellij.json.json5.Json5Language
import com.intellij.openapi.fileTypes.LanguageFileType
import dev.khulnasoft.intellij.Icons
import javax.swing.Icon

class KhulnasoftAppFileType : LanguageFileType(Json5Language.INSTANCE) {
    companion object {
        val INSTANCE = KhulnasoftAppFileType()
    }

    override fun getName(): String {
        return "Khulnasoft App Manifest"
    }

    override fun getDescription(): String {
        return "A file which describes the overall Khulnasoft Application"
    }

    override fun getDefaultExtension(): String {
        return "app"
    }

    override fun getIcon(): Icon {
        return Icons.Icon
    }
}
