package application;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * This class provides a view of each food item in the food list/meal list
 * @author YW
 *
 */
public class FoodItemView {
	private FoodItem foodItem;       	// The foodItem
	private String name;				// The name of the foodItem
	private Double calories;			// The calories of foodItem
	private Double fat;					// The fat of foodItem
	private Double carbohydrate;		// The carbohydrate of foodItem
	private Double protein;				// The protein of foodItem
	private Double fiber;				// The fiber of foodItem
	private CheckBox checkBox;			// The checkbox of each row
	/* ----Constructors---- */
	public FoodItemView() {}
	
	public FoodItemView(FoodItem foodItem) {
		this(foodItem, false);
	}
	
	public FoodItemView(FoodItem foodItem, Boolean selected) {
		this.foodItem = foodItem;
		name = foodItem.getName();
		calories = foodItem.getNutrientValue("calories");
		carbohydrate = foodItem.getNutrientValue("carbohydrate");
		fat = foodItem.getNutrientValue("fat");
		protein = foodItem.getNutrientValue("protein");
		fiber = foodItem.getNutrientValue("fiber");
		checkBox = new CheckBox();
		checkBox.setSelected(false);
	}
	
	/**
	 * A horizontal view of the foodItem (each line of the listView)
	 * @return A view of the foodItem
	 */
	public Node getView() {
		HBox hBox = new HBox(69);
		Label name = new Label(foodItem.getName());
		Label calories = new Label(((Double)foodItem.getNutrientValue("calories")).toString());
		Label fat = new Label(((Double)foodItem.getNutrientValue("fat")).toString());
		Label carbs = new Label(((Double)foodItem.getNutrientValue("carbohydrate")).toString());
		Label protein = new Label(((Double)foodItem.getNutrientValue("protein")).toString());
		Label fiber = new Label(((Double)foodItem.getNutrientValue("fiber")).toString());
		hBox.getChildren().addAll(name, calories, fat, carbs, protein, fiber); 
		return hBox;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != FoodItemView.class) {
			return false;
		}
		return this.name.equals(((FoodItemView) obj).name);
	}
	
	public FoodItem getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
	}

	public boolean isSelected() {
		if (this.checkBox == null) {
			return false;
		}
		return this.checkBox.isSelected();
	}

	public void setSelected(boolean selected) {
		if (this.checkBox != null) {
			this.checkBox.setSelected(selected);
		}
	}

	public double getCalories() {
		return calories;
	}

	public double getFat() {
		return fat;
	}

	public double getCarbohydrate() {
		return carbohydrate;
	}

	public double getProtein() {
		return protein;
	}

	public double getFiber() {
		return fiber;
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public void setCarbohydrate(double carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public void setFiber(double fiber) {
		this.fiber = fiber;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}
	
	
}
