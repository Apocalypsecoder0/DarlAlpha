
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;

public class CrossPlatformClient extends Application {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String playerId;
    
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 15; -fx-background-color: #2b2b2b;");

        TextField serverField = new TextField("localhost");
        TextField portField = new TextField("8080");
        TextField playerIdField = new TextField();
        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);
        
        Button connectButton = new Button("Connect");
        connectButton.setOnAction(e -> {
            try {
                socket = new Socket(serverField.getText(), Integer.parseInt(portField.getText()));
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                playerId = playerIdField.getText();
                out.println(playerId);
                
                new Thread(() -> {
                    try {
                        String message;
                        while ((message = in.readLine()) != null) {
                            String finalMessage = message;
                            javafx.application.Platform.runLater(() -> 
                                messageArea.appendText(finalMessage + "\n")
                            );
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }).start();
                
            } catch (IOException ex) {
                messageArea.appendText("Connection failed: " + ex.getMessage() + "\n");
            }
        });

        root.getChildren().addAll(
            new Label("Server:"), serverField,
            new Label("Port:"), portField,
            new Label("Player ID:"), playerIdField,
            connectButton,
            messageArea
        );

        Scene scene = new Scene(root, 400, 600);
        primaryStage.setTitle("Dark Alpha Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
