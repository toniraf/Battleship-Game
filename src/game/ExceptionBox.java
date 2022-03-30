/**
 * 
 */
package game;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.geometry.*;
/**
 * @author toni
 *
 */
public class ExceptionBox {
	
	

	public static void display(String title, String message) {
		
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(500);
		
		window.setOnCloseRequest(e -> {
			 Platform.exit();
		     System.exit(0);
		    });
		
		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button ("Close");
		closeButton.setOnAction(e -> {
			window.close();
			Platform.exit();
			System.exit(0);
		});
		
		VBox layout = new VBox(20); 
		layout.getChildren().addAll(label,closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}

	public static void BeforeStart(String title, String message) {
		
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(500);
		
		
		
		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button ("Ok");
		closeButton.setOnAction(e -> {
			window.close();
			
		});
		
		VBox layout = new VBox(20); 
		layout.getChildren().addAll(label,closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}

 


}
