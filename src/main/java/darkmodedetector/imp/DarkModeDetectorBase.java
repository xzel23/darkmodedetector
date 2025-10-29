package darkmodedetector.imp;

import darkmodedetector.DarkModeDetector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * An abstract base class for detecting and handling dark mode status.
 * This class provides a foundational implementation of the {@link DarkModeDetector} interface,
 * managing listeners and notifying them of dark mode state changes.
 * <p>
 * Subclasses should implement the specific logic for detecting dark mode and invoking
 * {@link #onChangeDetected(boolean)} when a change is detected.
 * <p>
 * The class handles:
 * - Adding and removing listeners for dark mode state changes.
 * - Logging state changes and listener actions.
 */
public abstract class DarkModeDetectorBase implements DarkModeDetector {

    private final Collection<Consumer<Boolean>> listeners = new ArrayList<>();

    /**
     * Constructs an instance of {@code DarkModeDetectorBase}.
     */
    protected DarkModeDetectorBase() {}

    /**
     * Adds a listener for dark mode state changes.
     * The listener is a {@link Consumer} that accepts a {@code Boolean} value representing
     * the dark mode state. A value of {@code true} indicates that dark mode is enabled,
     * and {@code false} indicates that it is disabled.
     *
     * @param listener the listener to be notified of dark mode state changes;
     *                 cannot be {@code null}
     */
    @Override
    public final void addListener(Consumer<Boolean> listener) {
        listeners.add(listener);
        monitorSystemChanges( true);
    }

    /**
     * Removes a previously added listener for dark mode state changes.
     * If the specified listener is not present in the list, no action is taken.
     *
     * @param listener the listener to be removed; must not be null
     */
    @Override
    public final void removeListener(Consumer<Boolean> listener) {
        listeners.remove(listener);
        monitorSystemChanges(!listeners.isEmpty());
    }

    /**
     * Notifies all registered listeners of a detected change in the dark mode status.
     *
     * @param darkMode a boolean indicating the current state of dark mode; {@code true} if dark mode is enabled, {@code false} otherwise
     */
    protected final void onChangeDetected(boolean darkMode) {
        listeners.forEach(l -> l.accept(darkMode));
    }

    /**
     * Monitors system changes for dark mode state.
     * This method is invoked to enable or disable the monitoring of system-level events
     * that indicate a change in dark mode status.
     * <p>
     * Subclasses must implement this method to define the specific behavior for starting
     * or stopping the monitoring functionality.
     *
     * @param enable {@code true} enables and {@code false} disables monitoring
     */
    protected abstract void monitorSystemChanges(boolean enable);
}
