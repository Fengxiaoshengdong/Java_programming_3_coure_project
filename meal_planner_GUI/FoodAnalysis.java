package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;

public class FoodAnalysis {
    public static PieChart getPieChart(FoodListView mealList) {


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Calories: " + mealList.getCalories() + "g", mealList.getCalories()),
            new PieChart.Data("Fat: " + mealList.getFat()+ "g", mealList.getFat()),
            new PieChart.Data("Carbohydrate: " + mealList.getCarbohydrate()+ "g", mealList.getCarbohydrate()),
            new PieChart.Data("Fiber: " + mealList.getFiber()+ "g", mealList.getFiber()),
            new PieChart.Data("Protein: " + mealList.getProtein()+ "g", mealList.getProtein()));



        PieChart mealChart = new PieChart(pieChartData);

    
        mealChart.setLabelsVisible(true);
        mealChart.setTitle("Analysis for the meal");
        return mealChart;
    }
}
