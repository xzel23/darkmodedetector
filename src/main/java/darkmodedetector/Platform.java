package darkmodedetector;

import java.util.Locale;

/**
 * Enumeration for the different Platforms/Operating systems.
 */
public enum Platform {
    /**
     * The Linux operating system.
     */
    LINUX,
    /**
     * The macOS operating system.
     */
    MACOS,
    /**
     * The Windows operating system.
     */
    WINDOWS,
    /**
     * Unknown operating system.
     */
    UNKNOWN;

    private static final System.Logger LOG = System.getLogger(Platform.class.getName());
    private static final Platform DETECTED = determinePlatform();

    private static Platform determinePlatform() {
        final Platform platform;

        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ROOT);
        if ((os.contains("mac")) || (os.contains("darwin"))) {
            platform = MACOS;
        } else if (os.contains("windows")) {
            platform = WINDOWS;
        } else if (os.contains("linux")) {
            platform = LINUX;
        } else {
            platform = UNKNOWN;
        }

        LOG.log(System.Logger.Level.DEBUG, () -> "platform identified as: " + platform);

        return platform;
    }

    /**
     * Get the detected platform that the program runs on.
     *
     * @return the detected platform
     */
    public static Platform currentPlatform() {
        return DETECTED;
    }

    /**
     * Check if current platform is Windows.
     *
     * @return true if currently running under a Windows operating system
     */
    public static boolean isWindows() {
        return DETECTED == WINDOWS;
    }

    /**
     * Check if current platform is Linux.
     *
     * @return true if currently running under a Linux operating system
     */
    public static boolean isLinux() {
        return DETECTED == LINUX;
    }

    /**
     * Check if current platform is macOS.
     *
     * @return true if currently running under a macOS operating system
     */
    public static boolean isMacOS() {
        return DETECTED == MACOS;
    }

    /**
     * Check if current platform is unknown.
     *
     * @return true if currently running under an unknown operating system
     */
    public static boolean isUnknown() {
        return DETECTED == UNKNOWN;
    }

    /**
     * Check if argument needs to be quoted before passing to {@link ProcessBuilder}.
     * <p>
     * The default version of this method returns false and is overridden to return true on Windows.
     *
     * @param s the argument
     * @return true, if s needs to be quoted
     */
    public boolean isProcessBuilderQuotingNeeded(String s) {
        return false;
    }

    /**
     * Quote argument for {@link ProcessBuilder}.
     * <p>
     * Only WINDOWS applies special quoting rules; on other platforms the default assumes no quoting is necessary.
     *
     * @param s the argument
     * @return quoted version of s if quoting needed, otherwise s
     */
    public String quoteProcessBuilderArg(String s) {
        return s;
    }

}
