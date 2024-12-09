package org.connecttag.utilities.global

import java.util.Locale

/**
 * OsUtils class.
 */
object OsUtils {

    /**
     * Get current Operation System (OS).
     *
     * @return the OS Name that is currently running the application.
     */
    @JvmStatic
    val osAgentName: String
        get() {
            if (isAndroidOs)
                return OsNames.ANDROID_OS_NAME.agentName

            val currentOsName = System.getProperty("os.name")?.lowercase(Locale.ROOT)

            for (osName in OsNames.entries) {
                if (currentOsName != null) {
                    if (currentOsName.startsWith(osName.naming))
                        return osName.agentName
                }
            }

            return OsNames.UNKNOWN_OS.agentName
        }

    /**
     * Checks whether the current OS is android.
     *
     * @return *true* if current OS is android, *false* otherwise.
     */
    private val isAndroidOs: Boolean
        get() {
            return try {
                Class.forName("android.os.Build")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
        }

    /**
     * Enum with names of OSs to filter the *os.name* system property, and return values
     * for virgil-agent.
     */
    private enum class OsNames {
        ANDROID_OS_NAME("android"),
        LINUX_OS_NAME("linux"),
        WINDOWS_OS_NAME("windows"),
        MACOS_OS_NAME("mac os", "darwin"),
        UNKNOWN_OS("unknown");

        val naming: String
        val agentName: String

        constructor(naming: String) {
            this.naming = naming
            this.agentName = naming
        }

        constructor(naming: String, loggedName: String) {
            this.naming = naming
            this.agentName = loggedName
        }

        override fun toString(): String {
            return naming
        }
    }
}
