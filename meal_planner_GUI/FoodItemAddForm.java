package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class FoodItemAddForm {



    private Button addButton;
    private TextField nameField;
    private TextField IDField;
    private Label title;
    private Label subtitle;
    private Label name;
    private Label ID;
    private Label calories;
    private Label fat;
    private Label carbohydrate;
    private Label fiber;
    private Label protein;
    private TextField calField;
    private TextField fatField;
    private TextField carboField;
    private TextField fiberField;
    private TextField proteinField;
    private Label gram;
    private Label gram2;
    private Label gram3;
    private Label gram4;
    private Label gram5;
    private GridPane gridPane;

    public GridPane AddFoodPane(PrimaryGUI primaryGUI) {
        gridPane = new GridPane();

        HBox hBox;
        //set label for name, cal, fat, carbohydrate, protein
        title = new Label("Add New Food Information");
        title.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 30));


        subtitle = new Label("Add custom food items to your current food list!");
        subtitle.setFont(Font.font("Trebuchet MS", FontWeight.NORMAL, 20));


        name = new Label("Name: ");

        name.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));

        ID = new Label("ID: ");
        ID.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        calories = new Label("Calories: ");
        calories.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        fat = new Label("Fat: ");
        fat.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        carbohydrate = new Label("Carbohydrate: ");
        carbohydrate.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        fiber = new Label("Fiber: ");
        fiber.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        protein = new Label("Protein: ");
        protein.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        gram = new Label(" g");
        gram2 = new Label(" g");
        gram3 = new Label(" g");
        gram4 = new Label(" g");
        gram5 = new Label(" g");
        // set textfield for adding food name
        nameField = new TextField();
        nameField.setPrefColumnCount(10);
        nameField.setEditable(true);

        IDField = new TextField();
        IDField.setPrefColumnCount(10);
        IDField.setEditable(true);

        calField = new TextField();
        calField.setPrefColumnCount(10);
        calField.setEditable(true);

        fatField = new TextField();
        fatField.setPrefColumnCount(10);
        fatField.setEditable(true);

        carboField = new TextField();
        carboField.setPrefColumnCount(10);
        carboField.setEditable(true);

        fiberField = new TextField();
        fiberField.setPrefColumnCount(10);
        fiberField.setEditable(true);

        proteinField = new TextField();
        proteinField.setPrefColumnCount(10);
        proteinField.setEditable(true);

        gridPane.add(ID, 0, 2);
        gridPane.add(IDField, 1, 2);

        gridPane.add(name, 0, 3);
        gridPane.add(nameField, 1, 3);

        gridPane.add(calories, 0, 3 + 1);
        gridPane.add(calField, 1, 3 + 1);
        gridPane.add(gram, 2, 3 + 1);

        gridPane.add(fat, 0, 4 + 1);
        gridPane.add(fatField, 1, 4 + 1);
        gridPane.add(gram2, 2, 4 + 1);

        gridPane.add(carbohydrate, 0, 5 + 1);
        gridPane.add(carboField, 1, 5 + 1);
        gridPane.add(gram3, 2, 5 + 1);

        gridPane.add(fiber, 0, 6 + 1);
        gridPane.add(fiberField, 1, 6 + 1);
        gridPane.add(gram4, 2, 6 + 1);

        gridPane.add(protein, 0, 7 + 1);
        gridPane.add(proteinField, 1, 7 + 1);
        gridPane.add(gram5, 2, 7 + 1);


        // create add Button
        addButton = new Button(" Add New Food");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                //First reset the style of bad input text field
                nameField.setStyle("-fx-color: black;");
                calField.setStyle("-fx-color: black;");
                carboField.setStyle("-fx-color: black;");
                fatField.setStyle("-fx-color: black;");
                proteinField.setStyle("-fx-color: black;");
                fiberField.setStyle("-fx-color: black;");

                boolean badInput = false;
                String id = IDField.getText();
                String name = nameField.getText();

                if (nameField.getText() == null || IDField.getText() == null || nameField.getText()
                    .trim().equals("") || nameField.getText().trim().equals("")) {
                    badInput = true;
                    Alert badInputAlert = new Alert(AlertType.WARNING);
                    badInputAlert.setTitle("Invalid input!");
                    badInputAlert.setContentText("Invalid input: 'Name' cannot be empty!");
                    badInputAlert.setResizable(true);
                    badInputAlert.showAndWait();
                    nameField.setStyle("-fx-text-box-border: red;");
                    return;
                }

                FoodItem newFood = new FoodItem(id, name);

                try {
                    // Check if each field is empty. If left empty, the default value is 0
                    if (calField.getText().trim() != "") {
                        // Nutrient value has to be greater than or equals 0
                        if (Double.parseDouble(calField.getText().trim()) < 0) {
                            throw new NumberFormatException();
                        }
                        newFood
                            .addNutrient("calories", Double.parseDouble(calField.getText().trim()));
                    } else {
                        newFood.addNutrient("calories", 0d);
                    }
                } catch (NumberFormatException e) {
                    badInput = true;
                    Alert badInputAlert = new Alert(AlertType.WARNING);
                    badInputAlert.setTitle("Invalid input!");
                    badInputAlert.setContentText(
                        "Invalid input on 'calories' field! Must be a non-negative and non-empty number!'");
                    badInputAlert.setResizable(true);
                    badInputAlert.showAndWait();
                    calField.setStyle("-fx-text-box-border: red;");
                    return;
                }

                try {
                    // Check if each field is empty. If left empty, the default value is 0
                    if (fatField.getText().trim() != "") {
                        // Nutrient value has to be greater than or equals 0
                        if (Double.parseDouble(fatField.getText().trim()) < 0) {
                            throw new NumberFormatException();
                        }
                        newFood.addNutrient("fat", Double.parseDouble(fatField.getText()));
                    } else {
                        newFood.addNutrient("fat", 0d);
                    }
                } catch (NumberFormatException e) {
                    badInput = true;
                    Alert badInputAlert = new Alert(AlertType.INFORMATION);
                    badInputAlert.setTitle("Invalid input!");
                    badInputAlert.setContentText(
                        "Invalid input on 'fat' field! Must be a non-negative and non-empty number!'");
                    badInputAlert.setResizable(true);
                    badInputAlert.showAndWait();
                    fatField.setStyle("-fx-text-box-border: red;");
                    return;
                }

                try {
                    // Check if each field is empty. If left empty, the default value is 0
                    if (carboField.getText().trim() != "") {
                        // Nutrient value has to be greater than or equals 0
                        if (Double.parseDouble(carboField.getText().trim()) < 0) {
                            throw new NumberFormatException();
                        }
                        newFood
                            .addNutrient("carbohydrate", Double.parseDouble(carboField.getText()));
                    } else {
                        newFood.addNutrient("carbohydrate", 0d);
                    }
                } catch (NumberFormatException e) {
                    badInput = true;
                    Alert badInputAlert = new Alert(AlertType.INFORMATION);
                    badInputAlert.setTitle("Invalid input!");
                    badInputAlert.setContentText(
                        "Invalid input on 'carbohydrate' field! Must be a non-negative and non-empty number!'");
                    badInputAlert.setResizable(true);
                    badInputAlert.showAndWait();
                    carboField.setStyle("-fx-text-box-border: red;");
                    return;
                }

                try {
                    // Check if each field is empty. If left empty, the default value is 0
                    if (fiberField.getText().trim() != "") {
                        // Nutrient value has to be greater than or equals 0
                        if (Double.parseDouble(fiberField.getText().trim()) < 0) {
                            throw new NumberFormatException();
                        }
                        newFood.addNutrient("fiber", Double.parseDouble(fiberField.getText()));
                    } else {
                        newFood.addNutrient("fiber", 0d);
                    }
                } catch (NumberFormatException e) {
                    badInput = true;
                    Alert badInputAlert = new Alert(AlertType.INFORMATION);
                    badInputAlert.setTitle("Invalid input!");
                    badInputAlert.setContentText(
                        "Invalid input on 'fiber' field! Must be a non-negative and non-empty number!'");
                    badInputAlert.setResizable(true);
                    badInputAlert.showAndWait();
                    fiberField.setStyle("-fx-text-box-border: red;");
                    return;
                }

                try {
                    // Check if each field is empty. If left empty, the default value is 0
                    if (proteinField.getText().trim() != "") {
                        // Nutrient value has to be greater than or equals 0
                        if (Double.parseDouble(proteinField.getText().trim()) < 0) {
                            throw new NumberFormatException();
                        }
                        newFood.addNutrient("protein", Double.parseDouble(proteinField.getText()));
                    } else {
                        newFood.addNutrient("protein", 0d);
                    }
                } catch (NumberFormatException e) {
                    badInput = true;
                    Alert badInputAlert = new Alert(AlertType.INFORMATION);
                    badInputAlert.setTitle("Invalid input!");
                    badInputAlert.setContentText(
                        "Invalid input on 'protein' field! Must be a non-negative and non-empty number!'");
                    badInputAlert.setResizable(true);
                    badInputAlert.showAndWait();
                    proteinField.setStyle("-fx-text-box-border: red;");
                    return;
                }

                if (!badInput) {
                    primaryGUI.food.addFoodItem(newFood);
                    primaryGUI.addFoodChangeView();
                }
            }
        });

        hBox = new HBox(addButton);
        
        gridPane.add(hBox, 1, 8 + 1);


        // creating a text object
//        Text title = new Text();
//        Text subTitle = new Text();
//
//        title.setText("Add New Food Information");
//        subTitle.setText("Add food items to the current food list");
//        title.setFont(Font.font("verdana", FontPosture.REGULAR, 20));
//        subTitle.setFont(Font.font("verdana", FontPosture.REGULAR, 10));

        gridPane.add(title, 0, 0);
        gridPane.add(subtitle, 0, 1);
        return gridPane;
    }
}


