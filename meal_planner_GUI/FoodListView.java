package application;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * An abstract TableView of food. Used for food list and meal list.
 * @author YW
 *
 */
public class FoodListView {
	public TableView<FoodItemView> foodTableView;	//A view of the foodItemList
	public List<FoodItem> foodItemList;		//Stores the foodItems
	private List<FoodItem> selectedList;	//Stores the selected foodItems
	private ObservableList<FoodItemView> viewList; 	//Stores the foodItemViews of each item
	private boolean isFoodList; 			// Indicates if the object is foodList(true) or a mealList(false)
	private FoodItemView total;				// The total bar
	
	/* ----Constructors---- */
	public FoodListView(List<FoodItem> foodItemList, boolean isFoodList) {
		foodTableView = new TableView<>();
		viewList = FXCollections.observableArrayList();
		this.foodItemList = foodItemList;
		this.isFoodList = isFoodList;
		total = new FoodItemView();
		
		//Initialize the viewList with the foodItemList given
		for (FoodItem foodItem : foodItemList) {
			viewList.add(new FoodItemView(foodItem));
		}
		//Set up the table view
		//CheckBox Column
		TableColumn<FoodItemView, CheckBox> checkCol = new TableColumn<>("Select");
		checkCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
		//Name Column
		TableColumn<FoodItemView, String> nameCol = new TableColumn<>("Name");
		nameCol.setMinWidth(200);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		//Calories Column
		TableColumn<FoodItemView, Double> caloriesCol = new TableColumn<>("Calories");
		caloriesCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
		//Carbohydrate Column
		TableColumn<FoodItemView, Double> carbsCol = new TableColumn<>("Carbohydrate");
		carbsCol.setCellValueFactory(new PropertyValueFactory<>("carbohydrate"));
		//Fat Column
		TableColumn<FoodItemView, Double> fatCol = new TableColumn<>("Fat");
		fatCol.setCellValueFactory(new PropertyValueFactory<>("fat"));
		//Protein Column
		TableColumn<FoodItemView, Double> proteinCol = new TableColumn<>("Protein");
		proteinCol.setCellValueFactory(new PropertyValueFactory<>("protein"));
		//Fiber Column
		TableColumn<FoodItemView, Double> fiberCol = new TableColumn<>("Fiber");
		fiberCol.setCellValueFactory(new PropertyValueFactory<>("fiber"));
		
		//Set up columns and items
		foodTableView.getColumns().addAll(checkCol, nameCol, caloriesCol, carbsCol, fatCol, proteinCol, fiberCol);
		
		if(!isFoodList) {
			total = new FoodItemView();
			total.setName("Total");
			total.setCheckBox(null);
			Double cal = 0d, carbs = 0d, fiber = 0d, protein = 0d, fat = 0d;
			// Calculate the total value of nutrients
			for (int i = 1; i < viewList.size(); i++) {
				cal += viewList.get(i).getCalories();
				carbs += viewList.get(i).getCarbohydrate();
				protein += viewList.get(i).getProtein();
				fat += viewList.get(i).getFat();
				fiber += viewList.get(i).getFiber();
			}
			total.setCalories(cal);
			total.setCarbohydrate(carbs);
			total.setFat(fat);
			total.setFiber(fiber);
			total.setProtein(protein);
		}
		foodTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}	
	
	/**
	 * Get the food selected in the list
	 * @return a list of food that is selected
	 */
	public List<FoodItem> getSelection() {
		// Each time checking selected items, add all checked foodItems to a new list
		selectedList = new ArrayList<>();
		for (FoodItemView fdv : viewList) {
			if (fdv.isSelected())
				selectedList.add(fdv.getFoodItem());
		}
		return selectedList;
	}
	
	/**
	 * Add food to the list
	 * @param food
	 */
	public void addFoodItem(FoodItem food) {
		foodItemList.add(food);  
		FoodItemView fIV = new FoodItemView(food);
		viewList.add(fIV);
		foodTableView.setItems(viewList);
		total.setCalories(total.getCalories() + fIV.getCalories());
		total.setCarbohydrate(total.getCarbohydrate() + fIV.getCarbohydrate());
		total.setFat(total.getFat()+ fIV.getFat());
		total.setProtein(total.getProtein() + fIV.getProtein());
		total.setFiber(total.getFiber()+ fIV.getFiber());
	}
	
	/**
	 * delete food from the list
	 * @param food
	 */
	public void deleteFoodItem(FoodItem food) {
		foodItemList.remove(food);
		FoodItemView fIV = new FoodItemView(food);
		viewList.remove(fIV);
		foodTableView.setItems(viewList);
		total.setCalories(total.getCalories() - fIV.getCalories());
		total.setCarbohydrate(total.getCarbohydrate() - fIV.getCarbohydrate());
		total.setFat(total.getFat() - fIV.getFat());
		total.setProtein(total.getProtein() - fIV.getProtein());
		total.setFiber(total.getFiber() - fIV.getFiber());
	}
	
	/**
	 * clear the selectedList
	 */
	public void reset() {
		// Iterate through all food items and set each checkbox to false
		for (FoodItemView foodView : viewList) {
			foodView.setSelected(false);
		}
	}

	/**
	 * Select all items in the foodListView
	 */
	public void selectAll() {
		// Iterate through all food items and set each checkbox to false
		for (FoodItemView foodView : viewList) {
			foodView.setSelected(true);
		}
	}
	
	/**
	 * Get the ListView of the foodItemList 
	 * @return a view of food list
	 */
	public TableView<FoodItemView> getView(){
		foodTableView.setItems(viewList);
		foodTableView.getSortOrder().add(foodTableView.getColumns().get(1));
		return foodTableView;
	}
	
	/**
	 * Get the list of food
	 * @return
	 */
	public List<FoodItem> getFoodItemList(){
		return foodItemList;
	}
	
	public Double getCalories() {
		return total.getCalories();
	}
	public Double getCarbohydrate() {
		return total.getCarbohydrate();
	}
	public Double getFat() {
		return total.getFat();
	}
	public Double getProtein() {
		return total.getProtein();
	}
	public Double getFiber(){
		return total.getFiber();
	}
}
