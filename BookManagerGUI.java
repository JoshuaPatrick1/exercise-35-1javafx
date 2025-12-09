import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class BookManagerGUI extends Application {
    private Connection conn;

    @Override
    public void start(Stage primaryStage) {
        connectToDB();

        TextField titleField = new TextField();
        titleField.setPromptText("Book Title");

        TextField authorField = new TextField();
        authorField.setPromptText("Author");

        TextField yearField = new TextField();
        yearField.setPromptText("Year");

        Button addButton = new Button("Add Book");
        addButton.setOnAction(e -> addBook(titleField.getText(), authorField.getText(), Integer.parseInt(yearField.getText())));

        TableView<Book> table = new TableView<>();
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        TableColumn<Book, Integer> yearCol = new TableColumn<>("Year");
        table.getColumns().addAll(titleCol, authorCol, yearCol);

        VBox root = new VBox(10, titleField, authorField, yearField, addButton, table);
        Scene scene = new Scene(root, 500, 400);

        primaryStage.setTitle("Exercise 35.1 - Book Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToDB() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "user", "password");
            System.out.println("Connected to database.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addBook(String title, String author, int year) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Books (title, author, year) VALUES (?, ?, ?)");
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, year);
            stmt.executeUpdate();
            System.out.println("Book added: " + title);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
