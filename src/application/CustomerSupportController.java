package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerSupportController {

    @FXML
    private VBox supportContent;

    @FXML
    private Label supportTitle;

    // Set the customer support content dynamically
    public void setSupportContent(String content) {
        Label contentLabel = new Label(content);
        contentLabel.setWrapText(true);
        supportContent.getChildren().add(contentLabel);
    }

    // Close the customer support window
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) supportContent.getScene().getWindow();
        stage.close();
    }
}
