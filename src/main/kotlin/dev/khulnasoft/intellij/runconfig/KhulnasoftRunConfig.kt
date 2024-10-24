package dev.khulnasoft.intellij.runconfig

import com.goide.execution.GoBuildingRunConfiguration
import com.goide.execution.GoRunConfigurationBase
import com.goide.execution.GoRunningState
import com.goide.execution.application.GoApplicationConfiguration
import com.goide.execution.extension.GoRunConfigurationExtension
import com.goide.execution.testing.GoTestRunConfiguration
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.target.TargetedCommandLineBuilder
import com.intellij.execution.target.value.TargetValue
import dev.khulnasoft.intellij.settings.settingsState
import dev.khulnasoft.intellij.utils.isInKhulnasoftApp

class KhulnasoftRunConfig : GoRunConfigurationExtension() {
    override fun isApplicableFor(configuration: GoRunConfigurationBase<*>): Boolean {
        if (configuration is GoTestRunConfiguration) { //  || isRunCommand(configuration)) {
            return isInKhulnasoftApp(configuration.getProject())
        }
        return false
    }

    override fun isEnabledFor(
        applicableConfiguration: GoRunConfigurationBase<*>,
        runnerSettings: RunnerSettings?
    ): Boolean {
        if (applicableConfiguration is GoTestRunConfiguration || isRunCommand(applicableConfiguration)) {
            return isInKhulnasoftApp(applicableConfiguration.getProject())
        }
        return false
    }

    override fun patchCommandLine(
        configuration: GoRunConfigurationBase<*>,
        runnerSettings: RunnerSettings?,
        cmdLine: TargetedCommandLineBuilder,
        runnerId: String,
        state: GoRunningState<out GoRunConfigurationBase<*>>,
        commandLineType: GoRunningState.CommandLineType
    ) {
        var useKhulnasoftBinary = false
        if (configuration is GoTestRunConfiguration) {
            useKhulnasoftBinary = if (configuration.kind == GoBuildingRunConfiguration.Kind.DIRECTORY) {
                // Directory style tests just run `go test -json [dir]`
                // so we always want to swap to the Khulnasoft binary for these
                true
            } else {
                // Otherwise we only want to use the Khulnasoft binary for build commands, as
                // GoLand first builds the binary (when we want to use Khulnasoft)
                // Then uses `go tools test2json [builtbinary]` - where we want to use the standard binary
                commandLineType == GoRunningState.CommandLineType.BUILD
            }
        }

//        if (isRunCommand(configuration)) {
//            useKhulnasoftBinary = true
//        }

        if (useKhulnasoftBinary) {
            cmdLine.exePath = TargetValue.fixed(settingsState().khulnasoftBinary)
        }
        super.patchCommandLine(configuration, runnerSettings, cmdLine, runnerId, state, commandLineType)
    }

    private fun isRunCommand(configuration: GoRunConfigurationBase<*>): Boolean {
        if (configuration is GoApplicationConfiguration) {
            return configuration.kind == GoBuildingRunConfiguration.Kind.PACKAGE && configuration.`package` == "khulnasoft.app"
        }
        return true
    }
}
