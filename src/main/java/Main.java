import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	private static final String ZAD1 = "zadanie_1.txt";
	private static final String ZAD2 = "zadanie_2.txt";
	private static final String ZAD3 = "zadanie_3.txt";
	private static String seed;
	private static String polynomial;
	private static String plainText;
	private static Boolean firstLine = true;
	private static Boolean secondline = false;
	private static Boolean thirdline = false;

	public static void main(String[] args) throws IOException {
		launch(args);
	}

	public static void zadanie3(TextArea textAreaLeft, TextArea textAreaRight) {
		firstLine = true;
		try (Stream<String> stream = Files.lines(Paths.get(ZAD3))) {
			stream.forEach(e -> {
				if (thirdline) {
					seed = e;
					thirdline = false;
				}
				if (secondline) {
					plainText = e;
					thirdline = true;
					secondline = false;
				}
				if (firstLine) {
					polynomial = e;
					firstLine = false;
					secondline = true;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (firstLine != null && seed != null) {
			try {
				Cac cac = new Cac(polynomial, seed, plainText);
				textAreaLeft.setText(cac.getLeftSide().toString());
				textAreaRight.setText(cac.getRightSide().toString());
			} catch (Exception e) {
				textAreaLeft.setText("Wielomian dluzszy od ziarna.");
			}
		} else {
			System.out.println("No seed or polynomial.");
		}

	}

	public static void zadanie2(TextArea textAreaLeft, TextArea textAreaRight) {
		firstLine = true;
		try (Stream<String> stream = Files.lines(Paths.get(ZAD2))) {
			stream.forEach(e -> {
				if (thirdline) {
					seed = e;
					thirdline = false;
				}
				if (secondline) {
					plainText = e;
					thirdline = true;
					secondline = false;
				}
				if (firstLine) {
					polynomial = e;
					firstLine = false;
					secondline = true;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (firstLine != null && seed != null) {
			try {
				Ssc ssc = new Ssc(polynomial, seed, plainText);
				textAreaLeft.setText(ssc.getLeftSide().toString());
				textAreaRight.setText(ssc.getRightSide().toString());
			} catch (Exception e) {
				textAreaLeft.setText("Wielomian dluzszy od ziarna.");
			}
		} else {
			System.out.println("No seed or polynomial.");
		}

	}

	public static void zadanie1(TextArea textAreaLeft, TextArea textAreaRight) {
		firstLine = true;
		try (Stream<String> stream = Files.lines(Paths.get(ZAD1))) {
			stream.forEach(e -> {
				if (firstLine) {
					polynomial = e;
					firstLine = false;
				} else
					seed = e;
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (firstLine != null && seed != null) {
			Lfsr lfsr;
			try {
				lfsr = new Lfsr(polynomial, seed);
				textAreaLeft.setText(lfsr.getLeftSide().toString());
				textAreaRight.setText(lfsr.getRightSide().toString());
			} catch (Exception e) {
				textAreaLeft.setText("Wielomian dluzszy od ziarna.");
			}

		} else {
			System.out.println("No seed or polynomial.");
		}
	}

	@Override
	public void start(Stage arg0) throws Exception {
		BorderPane borderPane = new BorderPane();
		final short prefHeight = 300;

		VBox vBoxLeft = new VBox();
		vBoxLeft.setPrefHeight(prefHeight);
		TextArea textAreaLeft = new TextArea();
		textAreaLeft.setStyle("-fx-control-inner-background: #3f3f3f3f; -fx-text-fill: black;");
		textAreaLeft.setEditable(false);
		textAreaLeft.setPrefHeight(vBoxLeft.getPrefHeight());
		vBoxLeft.getChildren().add(textAreaLeft);
		vBoxLeft.setPadding(new Insets(0, 0, 0, 10));
		vBoxLeft.setStyle("-fx-background-color: #222222");

		VBox vBoxRight = new VBox();
		vBoxRight.setPrefHeight(prefHeight);
		TextArea textAreaRight = new TextArea();
		textAreaRight.setStyle("-fx-control-inner-background: #3f3f3f3f; -fx-text-fill: black;");
		textAreaRight.setEditable(false);
		textAreaRight.setPrefHeight(vBoxRight.getPrefHeight());
		vBoxRight.getChildren().add(textAreaRight);
		vBoxRight.setPadding(new Insets(0, 10, 0, 0));
		vBoxRight.setStyle("-fx-background-color: #222222");

		VBox vBoxCenter = new VBox();
		vBoxCenter.setStyle("-fx-background-color: #222222");
		Label centerLabel = new Label("=>");
		// Image image = new Image(getClass().getResourceAsStream("arrow.png"));
		// ImageView imageView = new ImageView(image);
		// imageView.setFitWidth(20);
		// imageView.setFitHeight(20);
		// centerLabel.setGraphic(imageView);
		centerLabel.setTextFill(Color.WHITE);
		vBoxCenter.setAlignment(Pos.CENTER);
		vBoxCenter.setPadding(new Insets(0, 10, 0, 10));
		vBoxCenter.getChildren().add(centerLabel);

		HBox hBoxTop = new HBox();
		hBoxTop.setPrefWidth(323);
		hBoxTop.setStyle("-fx-background-color: #222222");
		hBoxTop.setPadding(new Insets(10, 10, 10, 10));
		hBoxTop.setSpacing(11);
		Button lfsrBtn = new Button("Zadanie 1");
		lfsrBtn.setMinWidth(hBoxTop.getPrefWidth());
		Button sscBtn = new Button("Zadanie 2 kryptosystem");
		sscBtn.setMinWidth(hBoxTop.getPrefWidth());
		Button cacBtn = new Button("Zadanie 3 kryptosystem");
		cacBtn.setMinWidth(hBoxTop.getPrefWidth());
		hBoxTop.getChildren().addAll(lfsrBtn, sscBtn, cacBtn);
		lfsrBtn.setOnAction(e -> zadanie1(textAreaLeft, textAreaRight));
		sscBtn.setOnAction(e -> zadanie2(textAreaLeft, textAreaRight));
		cacBtn.setOnAction(e -> zadanie3(textAreaLeft, textAreaRight));

		borderPane.setTop(hBoxTop);
		borderPane.setLeft(vBoxLeft);
		borderPane.setCenter(vBoxCenter);
		borderPane.setRight(vBoxRight);

		Scene scene = new Scene(borderPane);

		arg0.setTitle("BSK_PS_1");
		arg0.setScene(scene);
		arg0.show();

	}

}
