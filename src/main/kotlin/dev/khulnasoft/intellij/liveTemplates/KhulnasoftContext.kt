package dev.khulnasoft.intellij.liveTemplates

import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType
import dev.khulnasoft.intellij.utils.isInKhulnasoftApp

class KhulnasoftContext : TemplateContextType( "Khulnasoft go file") {
    override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
        return isInKhulnasoftApp(templateActionContext.file.originalFile.containingDirectory) && templateActionContext.file.name.endsWith(".go")
    }
}
