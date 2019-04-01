package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class FilterRulesForm {
    String userinput_cal;
    String userinput_cal2;
    String userinput_carbo;
    String userinput_carbo2;
    String userinput_fat;
    String userinput_fat2;
    String userinput_pro;
    String userinput_pro2;
    String userinput_fib2;
    String userinput_fib;

    String userInput_name;

    List<String> nutrientValList = new ArrayList<String>();
    List<FoodItem> filteredNutrientList = new ArrayList<FoodItem>();
    List<String> namelList = new ArrayList<String>();
    List<FoodItem> filteredNameList = new ArrayList<FoodItem>();
    int list_index = 0;

    public GridPane filterPane(PrimaryGUI primaryGUI) {
        ComboBox<String> comparatorBox = new ComboBox<String>();
        Label calories = new Label("calories");
        calories.setFont(Font.font("Trebuchet MS", FontWeight.NORMAL, 18));
        Label carbohydrate = new Label("carbohydrate");
        carbohydrate.setFont(Font.font("Trebuchet MS", FontWeight.NORMAL, 18));
        Label fat = new Label("fat");
        fat.setFont(Font.font("Trebuchet MS", FontWeight.NORMAL, 18));
        Label protein = new Label("protein");
        protein.setFont(Font.font("Trebuchet MS", FontWeight.NORMAL, 18));
        Label fiber = new Label("fiber");
        fiber.setFont(Font.font("Trebuchet MS", FontWeight.NORMAL, 18));

        //        Button applyButton = new Button(" Apply Rule ");
        GridPane gridpane = new GridPane();
        Label title = new Label("Add Filter(s)");
        title.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 30));
        gridpane.add(title, 0, 0);

        Label empty2 = new Label();
        gridpane.add(empty2, 0, 0 + 1);
        Text subTitle = new Text("Filter By Nutrient");
        subTitle.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        gridpane.add(subTitle, 0, 2);
        Label nutrientLabel = new Label("Nutrient");
        nutrientLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 18));
        gridpane.add(nutrientLabel, 0, 2 + 1);
        gridpane.add(calories, 0, 3 + 1);
        gridpane.add(carbohydrate, 0, 4 + 1);
        gridpane.add(fat, 0, 5 + 1);
        gridpane.add(protein, 0, 6 + 1);
        gridpane.add(fiber, 0, 7 + 1);

        // first comparator
        Label comparatorLabel = new Label("Range");
        comparatorLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 18));
        gridpane.add(comparatorLabel, 1, 2 + 1);
        comparatorBox.getItems().addAll("","==", ">=");
        ComboBox<String> comparatorBox1 = new ComboBox<String>();
        comparatorBox1.getItems().addAll("","==", ">=");

        ComboBox<String> comparatorBox2 = new ComboBox<String>();
        comparatorBox2.getItems().addAll("","==", ">=");
        ComboBox<String> comparatorBox3 = new ComboBox<String>();
        comparatorBox3.getItems().addAll("","==", ">=");
        ComboBox<String> comparatorBox4 = new ComboBox<String>();
        comparatorBox4.getItems().addAll("","==", ">=");

        comparatorBox.setLayoutY(10);
        comparatorBox1.setLayoutY(10);
        comparatorBox2.setLayoutY(10);
        comparatorBox3.setLayoutY(10);
        comparatorBox4.setLayoutY(10);

        VBox vBox = new VBox(comparatorBox);
        vBox.setFillWidth(true);
        gridpane.add(vBox, 1, 4);

        VBox vBox1 = new VBox(comparatorBox1);
        vBox1.setFillWidth(true);
        gridpane.add(vBox1, 1, 5);

        VBox vBox2 = new VBox(comparatorBox2);
        vBox2.setFillWidth(true);
        gridpane.add(vBox2, 1, 6);

        VBox vBox3 = new VBox(comparatorBox3);
        vBox3.setFillWidth(true);
        gridpane.add(vBox3, 1, 7);

        VBox vBox4 = new VBox(comparatorBox4);
        vBox4.setFillWidth(true);
        gridpane.add(vBox4, 1, 8);


        Label valueLabel = new Label("Value");
        valueLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 18));
        gridpane.add(valueLabel, 2, 2 + 1);

        TextField inputDouble = new TextField();
        TextField inputDouble1 = new TextField();
        TextField inputDouble2 = new TextField();
        TextField inputDouble3 = new TextField();
        TextField inputDouble4 = new TextField();

        inputDouble.setLayoutY(3);
        inputDouble1.setLayoutY(3);
        inputDouble2.setLayoutY(3);
        inputDouble3.setLayoutY(3);
        inputDouble4.setLayoutY(3);

        gridpane.add(inputDouble, 2, 3 + 1);
        gridpane.add(inputDouble1, 2, 4 + 1);
        gridpane.add(inputDouble2, 2, 5 + 1);
        gridpane.add(inputDouble3, 2, 6 + 1);
        gridpane.add(inputDouble4, 2, 7 + 1);



        // second comparator
        Label comparatorLabel2 = new Label("Range");
        comparatorLabel2.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 18));
        gridpane.add(comparatorLabel2, 3, 2 + 1);
        //comparatorBox.getItems().addAll("equals ==", "(default)", "less than <=");
        ComboBox<String> comparatorBox6 = new ComboBox<String>();
        comparatorBox6.getItems().addAll( "","==", "<=");
        ComboBox<String> comparatorBox7 = new ComboBox<String>();
        comparatorBox7.getItems().addAll( "","==", "<=");
        ComboBox<String> comparatorBox8 = new ComboBox<String>();
        comparatorBox8.getItems().addAll(  "","==","<=");
        ComboBox<String> comparatorBox9 = new ComboBox<String>();
        comparatorBox9.getItems().addAll(  "","==","<=");
        ComboBox<String> comparatorBox10 = new ComboBox<String>();
        comparatorBox10.getItems().addAll( "","==","<=");


        gridpane.add(comparatorBox6, 3, 3 + 1);
        gridpane.add(comparatorBox7, 3, 4 + 1);
        gridpane.add(comparatorBox8, 3, 5 + 1);
        gridpane.add(comparatorBox9, 3, 6 + 1);
        gridpane.add(comparatorBox10, 3, 7 + 1);

        comparatorBox6.setLayoutY(10);
        comparatorBox7.setLayoutY(10);
        comparatorBox8.setLayoutY(10);
        comparatorBox9.setLayoutY(10);
        comparatorBox10.setLayoutY(10);


        Label valueLabel2 = new Label("Value");
        valueLabel2.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 18));
        gridpane.add(valueLabel2, 4, 2 + 1);
        TextField inputDouble6 = new TextField();
        TextField inputDouble7 = new TextField();
        TextField inputDouble8 = new TextField();
        TextField inputDouble9 = new TextField();
        TextField inputDouble10 = new TextField();
        gridpane.add(inputDouble6, 4, 3 + 1);
        gridpane.add(inputDouble7, 4, 4 + 1);
        gridpane.add(inputDouble8, 4, 5 + 1);
        gridpane.add(inputDouble9, 4, 6 + 1);
        gridpane.add(inputDouble10, 4, 7 + 1);



        inputDouble6.setLayoutY(3);
        inputDouble7.setLayoutY(3);
        inputDouble8.setLayoutY(3);
        inputDouble9.setLayoutY(3);
        inputDouble10.setLayoutY(3);
        // set the button to filter by nutrients
        Button filterNutrients = new Button("Apply nutrients filter");
        gridpane.add(filterNutrients, 0, 8 + 1);
        filterNutrients.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                if (comparatorBox.getValue() != "") {
                    userinput_cal =
                        "calories " + comparatorBox.getValue() + " " + inputDouble.getText();
                    nutrientValList.add(list_index, userinput_cal);
                    ++list_index;
                }
                if (comparatorBox1.getValue() != "") {
                    userinput_carbo =
                        "carbohydrate " + comparatorBox1.getValue() + " " + inputDouble1.getText();
                    nutrientValList.add(list_index, userinput_carbo);
                    ++list_index;
                }
                if (comparatorBox2.getValue() != "") {
                    userinput_fat =
                        "fat " + comparatorBox2.getValue() + " " + inputDouble2.getText();
                    nutrientValList.add(list_index, userinput_fat);
                    ++list_index;
                }
                if (comparatorBox3.getValue() != "") {
                    userinput_pro =
                        "protein " + comparatorBox3.getValue() + " " + inputDouble3.getText();
                    nutrientValList.add(list_index, userinput_pro);
                    ++list_index;
                }
                if (comparatorBox4.getValue() != "") {
                    userinput_fib =
                        "fiber " + comparatorBox4.getValue() + " " + inputDouble4.getText();
                    nutrientValList.add(list_index, userinput_fib);
                    ++list_index;
                }

                if (comparatorBox6.getValue() != "") {
                    userinput_cal2 =
                        "calories " + comparatorBox6.getValue() + " " + inputDouble6.getText();
                    nutrientValList.add(list_index, userinput_cal2);
                    ++list_index;
                }
                if (comparatorBox7.getValue() != "") {
                    userinput_carbo2 =
                        "carbohydrate " + comparatorBox7.getValue() + " " + inputDouble7.getText();
                    nutrientValList.add(list_index, userinput_carbo2);
                    ++list_index;
                }
                if (comparatorBox8.getValue() != "") {
                    userinput_fat2 =
                        "fat " + comparatorBox8.getValue() + " " + inputDouble8.getText();
                    nutrientValList.add(list_index, userinput_fat2);
                    ++list_index;
                }
                if (comparatorBox9.getValue() != "") {
                    userinput_pro2 =
                        "protein " + comparatorBox9.getValue() + " " + inputDouble9.getText();
                    nutrientValList.add(list_index, userinput_pro2);
                    ++list_index;
                }
                if (comparatorBox10.getValue() != "") {
                    userinput_fib2 =
                        "fiber " + comparatorBox10.getValue() + " " + inputDouble10.getText();
                    nutrientValList.add(list_index, userinput_fib2);
                    ++list_index;
                }
                try {
                    primaryGUI.filterNutrientView(nutrientValList);
                } catch (Exception e) {
                    Alert badInputAlert = new Alert(Alert.AlertType.WARNING);
                    badInputAlert.setResizable(true);
                    badInputAlert.setTitle("Invalid input!");
                    badInputAlert.setContentText(
                        "Invalid input on filter by nutrients field! Must be a non-negative number!'");
                    badInputAlert.showAndWait();
                }

            }
        });


        Label empty = new Label();
        gridpane.add(empty, 0, 9 + 1);
        //gridpane.add(applyButton, 2, 8+1);
        // Label empty1 = new Label();
        //gridpane.add(empty1, 2, 8+1);
        Label filterbyname = new Label("Filter by Name");
        filterbyname.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        gridpane.add(filterbyname, 0, 10 + 1);
        Label entername = new Label("Please Enter Food Name");
        gridpane.add(entername, 0, 11 + 1);
        entername.setFont(Font.font("verdana", FontPosture.REGULAR, 12));
        TextField name = new TextField();
        gridpane.add(name, 0, 12 + 1);
        name.setPrefWidth(8);
        // set the filter ny name button
        Button filterName = new Button("Apply name filter");
        gridpane.add(filterName, 0, 13 + 1);
        filterName.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                userInput_name = name.getText();
                primaryGUI.filterNameView(userInput_name);
            }
        });

        //        Label empty1 = new Label();
        //        gridpane.add(empty1, 0, 14 + 1);

        // set the button to cancel all filters and back to original list
        Button cancelButton = new Button("Cancel all filters");
        gridpane.add(cancelButton, 4, 13);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                primaryGUI.cancelFilterView();
            }
        });
        // set the button to apply all filters
        Button applyButton = new Button("Apply both filters");
        gridpane.add(applyButton, 4, 10 + 1);
        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                // get the user input name
                userInput_name = name.getText();
                // get the user input nutrients list
                if (comparatorBox.getValue() != "") {
                    userinput_cal =
                        "calories " + comparatorBox.getValue() + " " + inputDouble.getText();
                    nutrientValList.add(list_index, userinput_cal);
                    ++list_index;
                }
                if (comparatorBox1.getValue() != "") {
                    userinput_carbo =
                        "carbohydrate " + comparatorBox1.getValue() + " " + inputDouble1.getText();
                    nutrientValList.add(list_index, userinput_carbo);
                    ++list_index;
                }
                if (comparatorBox2.getValue() != "") {
                    userinput_fat =
                        "fat " + comparatorBox2.getValue() + " " + inputDouble2.getText();
                    nutrientValList.add(list_index, userinput_fat);
                    ++list_index;
                }
                if (comparatorBox3.getValue() != "") {
                    userinput_pro =
                        "protein " + comparatorBox3.getValue() + " " + inputDouble3.getText();
                    nutrientValList.add(list_index, userinput_pro);
                    ++list_index;
                }
                if (comparatorBox4.getValue() != "") {
                    userinput_fib =
                        "fiber " + comparatorBox4.getValue() + " " + inputDouble4.getText();
                    nutrientValList.add(list_index, userinput_fib);
                    ++list_index;
                }

                if (comparatorBox6.getValue() != "") {
                    userinput_cal2 =
                        "calories " + comparatorBox6.getValue() + " " + inputDouble6.getText();
                    nutrientValList.add(list_index, userinput_cal2);
                    ++list_index;
                }
                if (comparatorBox7.getValue() != "") {
                    userinput_carbo2 =
                        "carbohydrate " + comparatorBox7.getValue() + " " + inputDouble7.getText();
                    nutrientValList.add(list_index, userinput_carbo2);
                    ++list_index;
                }
                if (comparatorBox8.getValue() != "") {
                    userinput_fat2 =
                        "fat " + comparatorBox8.getValue() + " " + inputDouble8.getText();
                    nutrientValList.add(list_index, userinput_fat2);
                    ++list_index;
                }
                if (comparatorBox9.getValue() != "") {
                    userinput_pro2 =
                        "protein " + comparatorBox9.getValue() + " " + inputDouble9.getText();
                    nutrientValList.add(list_index, userinput_pro2);
                    ++list_index;
                }
                if (comparatorBox10.getValue() != "") {
                    userinput_fib2 =
                        "fiber " + comparatorBox10.getValue() + " " + inputDouble10.getText();
                    nutrientValList.add(list_index, userinput_fib2);
                    ++list_index;
                }
                try {
                    primaryGUI.filterBothView(userInput_name, nutrientValList);
                } catch (Exception e) {
                    Alert badInputAlert = new Alert(Alert.AlertType.WARNING);
                    badInputAlert.setTitle("Invalid input!");
                    badInputAlert.setResizable(true);
                    badInputAlert.setContentText(
                        "Invalid input on filter by nutrients field! Must be a non-negative number!'");
                    badInputAlert.showAndWait();
                }
            }
        });
        return gridpane;
    }

}
