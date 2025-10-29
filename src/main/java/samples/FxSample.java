package samples;

import darkmodedetector.DarkModeDetector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Minimal JavaFX application that shows three radio buttons to choose the UiMode
 * and renders in light/dark according to DarkModeDetector's active dark mode.
 */
public final class FxSample extends Application {
    private static final DarkModeDetector DETECTOR = DarkModeDetector.getInstance();

    private UiMode currentMode = UiMode.SYSTEM_DEFAULT;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Dark Mode Sample (JavaFX)");

        BorderPane root = new BorderPane();
        HBox buttons = new HBox(12);
        buttons.setPadding(new Insets(12));

        ToggleGroup group = new ToggleGroup();
        RadioButton rbSystem = new RadioButton("System default");
        RadioButton rbLight = new RadioButton("Light");
        RadioButton rbDark = new RadioButton("Dark");

        rbSystem.setToggleGroup(group);
        rbLight.setToggleGroup(group);
        rbDark.setToggleGroup(group);

        rbSystem.setSelected(true);

        buttons.getChildren().addAll(new Label("Theme:"), rbSystem, rbLight, rbDark);
        root.setCenter(buttons);

        Scene scene = new Scene(root, 360, 120);
        stage.setScene(scene);

        // Apply initial theme
        applyTheme(currentMode, root);

        // Change Theme when user toggles
        rbSystem.setOnAction(e -> { currentMode = UiMode.SYSTEM_DEFAULT; applyTheme(currentMode, root); });
        rbLight.setOnAction(e -> { currentMode = UiMode.LIGHT; applyTheme(currentMode, root); });
        rbDark.setOnAction(e -> { currentMode = UiMode.DARK; applyTheme(currentMode, root); });

        // Change Theme when system setting changes (only while System Default is selected)
        DETECTOR.addListener(dark -> Platform.runLater(() -> {
            if (currentMode == UiMode.SYSTEM_DEFAULT && rbSystem.isSelected()) {
                applyTheme(dark ? UiMode.DARK : UiMode.LIGHT, root);
            }
        }));

        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();
    }

    private static void applyTheme(UiMode mode, Parent root) {
        boolean dark = mode == UiMode.DARK || (mode == UiMode.SYSTEM_DEFAULT && DETECTOR.isDarkMode());

        Scene scene = root.getScene();
        if (scene == null) return; // scene not yet set

        String resource = dark ? "dark.css" : "light.css";
        String url = FxSample.class.getResource(resource).toExternalForm();
        scene.getStylesheets().setAll(url);
    }
}
