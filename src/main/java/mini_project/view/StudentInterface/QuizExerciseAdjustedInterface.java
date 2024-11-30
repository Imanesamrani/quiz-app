package mini_project.view.StudentInterface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import mini_project.model.Quiz;
import mini_project.model.TestCase;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizExerciseAdjustedInterface extends Application {

    private Quiz quiz; // Store quiz details
    Label testCaseLabel;

    public void setQuizDetails(Quiz quiz) {
        this.quiz = quiz;
        quiz.ViewQuiz();
    }

    @Override
    public void start(Stage primaryStage) {
        // Prepare the initial code with class wrapping
        String codeBody = String.format("""
        public class UserSolution {
            %s
        }
        """, quiz.getStarterCode().trim());

        testCaseLabel = new Label();

        // Root Layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #E8F4FC;");

        // Top Bar
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-width: 0 0 1 0;");

        Label title = new Label("Quiz App");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Circle profileIcon = new Circle(15, Color.LIGHTGRAY);
        profileIcon.setStroke(Color.BLACK);

        topBar.getChildren().addAll(title, spacer, profileIcon);
        root.setTop(topBar);

        // Main Content
        HBox mainContent = new HBox();
        mainContent.setPadding(new Insets(20));
        mainContent.setSpacing(20);

        // Left Section: Exercise Details
        VBox leftSection = new VBox();
        leftSection.setSpacing(10);
        leftSection.setPrefWidth(400);

        Label backArrow = new Label(quiz.getTitle());
        backArrow.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        VBox objectiveBox = new VBox();
        objectiveBox.setSpacing(10);

        Label objectiveTitle = new Label("Objective");
        objectiveTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label objectiveDescription = new Label(quiz.getObjective());
        objectiveDescription.setFont(Font.font("Arial", 14));
        objectiveDescription.setWrapText(true);

        Label deadlineTitle = new Label("Deadline to submit");
        deadlineTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label deadlineValue = new Label(quiz.getDeadLine().toString() + " at 11:59 PM");
        deadlineValue.setFont(Font.font("Arial", 14));

        objectiveBox.getChildren().addAll(objectiveTitle, objectiveDescription, deadlineTitle, deadlineValue);

        leftSection.getChildren().addAll(backArrow, objectiveBox);

        // Right Section: Code Editor and Buttons
        VBox rightSection = new VBox();
        rightSection.setSpacing(10);
        rightSection.setAlignment(Pos.TOP_CENTER);
        rightSection.setPrefWidth(500);

        // Code Editor
        TextArea codeEditor = new TextArea(codeBody);
        codeEditor.setPrefSize(400, 300);
        codeEditor.setStyle("""
                -fx-font-family: Consolas;
                -fx-font-size: 14px;
                -fx-background-color: #F5F5F5;
                -fx-border-radius: 10;
                -fx-border-color: black;
                -fx-border-width: 1;
                -fx-padding: 10;
        """);

        // Buttons
        HBox buttons = new HBox();
        buttons.setSpacing(50);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        Button submitButton = new Button("Submit");
        submitButton.setPrefWidth(200);
        submitButton.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; -fx-font-weight: bold;-fx-background-radius:5em");

        Button runCodeButton = new Button("Run Code");
        runCodeButton.setOnAction(actionEvent -> {
            String fullCode = codeEditor.getText();
            if (compileUserCode(fullCode)) {
                testCaseLabel.setText("Code passed all tests!");
                testCaseLabel.setStyle("-fx-text-fill: green;");
            } else {
                testCaseLabel.setStyle("-fx-text-fill: red;");
            }
        });

        runCodeButton.setPrefWidth(200);
        runCodeButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius:5em");

        buttons.getChildren().addAll(submitButton, runCodeButton);

        VBox container = new VBox();
        container.setPadding(new Insets(10));
        container.setSpacing(10);
        container.setAlignment(Pos.TOP_CENTER);
        container.setStyle("""
                -fx-background-color: #F5F5F5;
                -fx-border-radius: 5;
                -fx-border-color: black;
                -fx-border-width: 1;
        """);

        testCaseLabel.setFont(Font.font("Arial", 14));
        testCaseLabel.setStyle("-fx-text-fill: black;");

        HBox testCases = new HBox();
        testCases.setSpacing(10);
        testCases.setAlignment(Pos.CENTER);

        for (int i = 0; i < quiz.getTestCases().size(); i++) {
            Button caseButton = new Button(String.format("Case %d",i+1));
            int finalI = i;
            caseButton.setOnAction(actionEvent -> {
                showTestCase(finalI);
            });
            caseButton.setPrefWidth(100);
            caseButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-font-weight: bold;");
            testCases.getChildren().add(caseButton);
        }

        container.getChildren().addAll(testCases, testCaseLabel);

        rightSection.getChildren().addAll(buttons, codeEditor, container);

        // Adjust layout proportions
        HBox.setHgrow(leftSection, Priority.ALWAYS);
        HBox.setHgrow(rightSection, Priority.ALWAYS);

        mainContent.getChildren().addAll(leftSection, rightSection);
        root.setCenter(mainContent);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quiz App - Exercise");
        primaryStage.show();
    }

    private boolean compileUserCode(String fullCode) {
        Path outputDir = Paths.get("compiled_classes");
        try {
            Files.createDirectories(outputDir);
        } catch (Exception e) {
            testCaseLabel.setText("Error creating output directory: " + e.getMessage());
            return false;
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            testCaseLabel.setText("No Java compiler available. Ensure JDK is installed.");
            return false;
        }

        JavaFileObject file = new JavaSourceFromString("UserSolution", fullCode);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);

        Iterable<String> options = Arrays.asList("-d", outputDir.toString());
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostic -> {
            testCaseLabel.setText("Compilation error: " + diagnostic.getMessage(null));
        }, options, null, compilationUnits);

        boolean success = task.call();
        if (!success) return false;

        return runTests(outputDir, quiz.getTestCases());
    }

    private boolean runTests(Path outputDir, List<TestCase> testCaseList) {
        try {
            // Load the compiled class
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{outputDir.toUri().toURL()});
            Class<?> cls = Class.forName("UserSolution", true, classLoader);

            // Retrieve the first method in the class (assuming it is the solution)
            Method method = cls.getDeclaredMethods()[0];

            // If the method is not static, create an instance of the class
            Object instance = Modifier.isStatic(method.getModifiers()) ? null : cls.getDeclaredConstructor().newInstance();

            // Iterate through the test cases
            for (TestCase testCase : testCaseList) {
                String[] inputs = testCase.getInput().split(" ");
                Class<?>[] parameterTypes = method.getParameterTypes();

                // Dynamically parse the inputs based on parameter types
                Object[] parsedInputs = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    parsedInputs[i] = parseInput(inputs[i], parameterTypes[i]);
                }

                // Invoke the method
                Object result = method.invoke(instance, parsedInputs);

                // Compare the result with the expected output
                if (!result.toString().equals(testCase.getOutput())) {
                    System.out.println(String.format("Test failed: input=%s, expected=%s, got=%s", testCase.getInput(), testCase.getOutput(), result));
                    testCaseLabel.setText(String.format("Test failed: input=%s, expected=%s, got=%s",
                            testCase.getInput(), testCase.getOutput(), result));
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            testCaseLabel.setText("Runtime error: " + e.getMessage());
            return false;
        }
    }

    private Object parseInput(String input, Class<?> type) {
        if (type == int.class) {
            return Integer.parseInt(input);
        } else if (type == double.class) {
            return Double.parseDouble(input);
        } else if (type == float.class) {
            return Float.parseFloat(input);
        } else if (type == boolean.class) {
            return Boolean.parseBoolean(input);
        } else if (type == String.class) {
            return input;
        }
        throw new IllegalArgumentException("Unsupported parameter type: " + type.getName());
    }

    private static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }


    }

    private void showTestCase(int id){
        TestCase testCase = quiz.getTestCases().get(id);
        testCaseLabel.setText( "Input:"+ testCase.getInput() + "  expected Output : " + testCase.getOutput());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
