package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrimaryGUI {
    FoodItemAddForm foodAddForm = new FoodItemAddForm();
    FilterRulesForm filterRulesForm = new FilterRulesForm();
    FoodListView foodList = new FoodListView(new ArrayList<>(), true);
    FoodListView mealList = new FoodListView(new ArrayList<>(), false);
    FoodData food = new FoodData();
    FoodData meal = new FoodData();
    GridPane selectFoodGrid = new GridPane();
    public Label foodCount = new Label();		//Count label for food
    public Label mealCount = new Label();		//Count label for meal

    public PrimaryGUI() {
    }

    public PrimaryGUI(FoodData foodData, Stage stage) throws IOException {
        GridPane gridPane = new GridPane();  // the whole grid pane

        gridPane.setHgap(20);
        gridPane.setVgap(30);

        gridPane.add(selectFoodPane(stage), 2, 0, 1, 3);
        gridPane.add(foodAddForm.AddFoodPane(this), 1, 1);
        gridPane.add(filterRulesForm.filterPane(this), 1, 2);
        foodCount.setText("Count: " + foodList.getFoodItemList().size());
        mealCount.setText("Count: " + mealList.getFoodItemList().size());
        
        Scene primaryGUI = new Scene(gridPane, 1560, 950);

        stage.setTitle("Food Query and Meal Analysis");
        stage.setScene(primaryGUI);
    }

    public GridPane selectFoodPane(Stage stage) {
        // Add to meal button. Used to add food from foodList to mealList
    	foodCount.setText("Count: " + foodList.getFoodItemList().size());
    	mealCount.setText("Count: " + mealList.getFoodItemList().size());

    	Button addToMeal = new Button("Add to meal");
        addToMeal.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                for (FoodItem food : foodList.getSelection()) {
                    mealList.addFoodItem(food);
                    meal.addFoodItem(food);
                }
                selectFoodGrid.getChildren().remove(mealList.getView());
                selectFoodGrid.add(mealList.getView(), 0, 7);
                mealCount.setText("Count: " + mealList.getFoodItemList().size());
            }
        });

        //Delete food button. Used to delete selected food
        Button deleteFood = new Button("Remove food");
        deleteFood.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                //Remove the selected food
                for (FoodItem food : mealList.getSelection()) {
                    mealList.deleteFoodItem(food);
                    meal.removeFoodItem(food);
                }
                selectFoodGrid.getChildren().remove(mealList.getView());
                selectFoodGrid.add(mealList.getView(), 0, 7);
                mealCount.setText("Count: " + mealList.getFoodItemList().size());
            }
        });

        // The Food analysis button. Used to get a pie chart for meal nutrients 
        Button analyze = new Button("Analyze meal");
        analyze.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                PieChart mealAnalysis = FoodAnalysis.getPieChart(mealList); // TODO
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setResizable(true);
                alert.setTitle("Meal Information");
                alert.setGraphic(mealAnalysis);
                alert.showAndWait();
            }
        });

        // The load food button. Used to load a csv file to the food list
        Button loadFood = new Button("Load Food");
        loadFood.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open CSV File");
                    File file = fileChooser.showOpenDialog(stage);
                    String s = file.getAbsolutePath();
                    food.clearAllFoodItems();
                    food.loadFoodItems(s);
                    foodList = new FoodListView(food.getAllFoodItems(), true);
                    selectFoodGrid.getChildren().remove(foodList.getView());
                    selectFoodGrid.add(foodList.getView(), 0, 3);
                    foodCount.setText("Count: " + foodList.getFoodItemList().size());
                } catch (NullPointerException e) {
                }
            }
        });


        // The save food button. Used to save the current meal to a csv file
        Button saveFood = new Button("Save your food!");
        saveFood.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    File file = fileChooser.showSaveDialog(stage);
                    food.saveFoodItems(file.getAbsolutePath());
                } catch (NullPointerException e) {
                }
            }
        });

        // The toggle all button for food list. Used to toggle(select/unselect) all food
        Button toggleAllFood = new Button("Toggle all");
        toggleAllFood.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                if (foodList.getSelection().size() != 0) {
                    foodList.reset();
                } else {
                    foodList.selectAll();
                }
            }
        });

        // The toggle all button for meal list. Used to toggle(select/unselect) all food
        Button toggleAllMeal = new Button("Toggle all");
        toggleAllMeal.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                if (mealList.getSelection().size() != 0) {
                    mealList.reset();
                } else {
                    mealList.selectAll();
                }
            }
        });

  
        HBox foodButtonBox = new HBox(117, addToMeal, loadFood, saveFood, toggleAllFood);
        HBox mealButtonBox = new HBox(250, analyze, deleteFood, toggleAllMeal);
        Label selectFoodLabel = new Label("Select ingredients to add to your meal:");
        selectFoodLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        Label mealLabel = new Label("Your meal currently includes:");
        mealLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));        

        selectFoodGrid.add(selectFoodLabel, 0, 1);
        selectFoodGrid.add(foodCount, 0, 2);
        selectFoodGrid.add(foodList.getView(), 0, 3);
        selectFoodGrid.add(foodButtonBox, 0, 4);
        selectFoodGrid.add(mealLabel, 0, 5);
        selectFoodGrid.add(mealCount, 0, 6);
        selectFoodGrid.add(mealList.getView(), 0, 7);
        selectFoodGrid.add(mealButtonBox, 0, 8);

        return selectFoodGrid;
    }

    public void filterNameView(String inputName) {
        foodList = new FoodListView(food.filterByName(inputName), true);
        selectFoodGrid.getChildren().remove(foodList.getView());
        selectFoodGrid.add(foodList.getView(), 0, 3);
        foodCount.setText("Count: " + foodList.getFoodItemList().size());
    }

    public void filterNutrientView(List<String> inputNutrients) {
        foodList = new FoodListView(food.filterByNutrients(inputNutrients), true);
        selectFoodGrid.getChildren().remove(foodList.getView());
        selectFoodGrid.add(foodList.getView(), 0, 3);
        foodCount.setText("Count: " + foodList.getFoodItemList().size());
    }

    public void filterBothView(String inputName, List<String> inputNutrients) {
        Set<FoodItem> nutrientSet = new HashSet<>();
        Set<FoodItem> nameSet = new HashSet<>();
        Set<FoodItem> returnSet = new HashSet<>();

        nameSet.addAll(food.filterByName(inputName));
        nutrientSet.addAll(food.filterByNutrients(inputNutrients));

        returnSet = nameSet;
        returnSet.retainAll(nutrientSet);

        List<FoodItem> filteredList = new ArrayList<>();
        filteredList.addAll(returnSet);
        System.out.println(filteredList.size());

        foodList = new FoodListView(filteredList, true);

        selectFoodGrid.getChildren().remove(foodList.getView());
        selectFoodGrid.add(foodList.getView(), 0, 3);
        foodCount.setText("Count: " + foodList.getFoodItemList().size());
    }

    public void cancelFilterView() {
        foodList = new FoodListView(food.getAllFoodItems(), true);
        selectFoodGrid.getChildren().remove(foodList.getView());
        selectFoodGrid.add(foodList.getView(), 0, 3);
        foodCount.setText("Count: " + foodList.getFoodItemList().size());
    }

    public void addFoodChangeView() {
        foodList = new FoodListView(food.getAllFoodItems(), true);
        selectFoodGrid.getChildren().remove(foodList.getView());
        selectFoodGrid.add(foodList.getView(), 0, 3);
        foodCount.setText("Count: " + foodList.getFoodItemList().size());
        
    }

}
