module quiz.app {
    requires java.sql;
    requires javafx.graphics;
    requires jbcrypt;
    requires javafx.controls;
    requires java.compiler;
    requires javafx.base;
    opens mini_project.view.Professor to javafx.base;
    exports mini_project to javafx.graphics;
    exports mini_project.view.StudentInterface;
    exports mini_project.view.Professor;



}