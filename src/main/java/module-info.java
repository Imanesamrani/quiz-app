module quiz.app {
    requires java.sql;
    requires javafx.graphics;
    requires jbcrypt;
    requires javafx.controls;
    requires java.compiler;

    exports mini_project to javafx.graphics;
    exports mini_project.view.StudentInterface;



}