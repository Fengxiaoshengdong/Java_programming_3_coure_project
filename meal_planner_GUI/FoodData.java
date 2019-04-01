package application;

import java.io.*;
import java.util.*;

/**
 * This class represents the backend for managing all
 * the operations associated with FoodItems
 *
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {

    //default delimiter
    private static final char DEFAULT_SEPARATOR = ',';
    // List of all the food items.
    private List<FoodItem> foodItemList;
    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;


    /**
     * Public constructor
     */
    public FoodData() {
        this.foodItemList = new ArrayList<FoodItem>();
        this.indexes = new HashMap<String, BPTree<Double, FoodItem>>();
    }


    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override public void loadFoodItems(String filePath) {
        BPTree<Double, FoodItem> caloriesTree = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> fatTree = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> carbonTree = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> fiberTree = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> proteinTree = new BPTree<Double, FoodItem>(3);

        try {
            Scanner scanner = new Scanner(new File(filePath));
            //s holds a line of the csv file
            String s = new String();
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();

                //if the line is not empty (the first char is not ',')
                if (s.charAt(0) != ',') {
                    //Delimiter = ","
                    String[] values = s.split(",");

                    //Use ID and name to initialize a FoodItem instance
                    FoodItem fdItem = new FoodItem(values[0], values[1]);

                    //Add nutrients and their value
                    for (int i = 2; i < 11; i = i + 2) {
                        fdItem.addNutrient(values[i], Double.parseDouble(values[i + 1]));
                    }
                    this.foodItemList.add(fdItem);
                    caloriesTree.insert(Double.parseDouble(values[3]), fdItem);
                    fatTree.insert(Double.parseDouble(values[5]), fdItem);
                    carbonTree.insert(Double.parseDouble(values[7]), fdItem);
                    fiberTree.insert(Double.parseDouble(values[9]), fdItem);
                    proteinTree.insert(Double.parseDouble(values[11]), fdItem);


                    indexes.put(values[2], caloriesTree);
                    indexes.put(values[4], fatTree);
                    indexes.put(values[6], carbonTree);
                    indexes.put(values[8], fiberTree);
                    indexes.put(values[10], proteinTree);
                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND!");
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override public List<FoodItem> filterByName(String substring) {
        List<FoodItem> returnList = new ArrayList<FoodItem>();

        // find the all of the data items that contain the word in substring
        for (int i = 0; i < foodItemList.size(); ++i) {
            if (foodItemList.get(i).getName().contains(substring)) {
                returnList.add(foodItemList.get(i));
            }
        }
        // sort list in alphabetical order
        //        FoodItem temp;
        //        if (!returnList.isEmpty()) {
        //
        //            for (int i = 0; i < returnList.size(); i++) {
        //                for (int j = i + 1; j < returnList.size(); j++) {
        //                    if (returnList.get(i).getName()
        //                        .compareTo(returnList.get(j).getName()) > 0) {
        //                        temp = returnList.get(i);
        //                        returnList.set(i, returnList.get(j));
        //                        returnList.set(j, temp);
        //                    }
        //                }
        //            }
        //        }
        return returnList;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override public List<FoodItem> filterByNutrients(List<String> rules) {
        // TODO : Complete
        List<FoodItem> returnList = new ArrayList<FoodItem>();
        String nutrient; //nutrient name
        String comparator;  //comparator name
        Double value; // user defined range value
        String[] nutrientArray =
            new String[] {"calories", "carbohydrate", "fat", "protein", "fiber"};
        Set finalSet = new HashSet(); // the set use to get the intersection of each nutrient filter
        finalSet.addAll(foodItemList);

        for (int i = 0; i < rules.size(); i++) {  // traverse through multiple rules
            String[] ruleArray = rules.get(i).split(" ");  // array of one rule
            nutrient = ruleArray[0];
            comparator = ruleArray[1];
            if (ruleArray[1].equals("null") || ruleArray.length < 3) {
                continue;
            }
            value = Double.parseDouble(ruleArray[2]);
            Set filtered = new HashSet();

            filtered.addAll(indexes.get(nutrient).rangeSearch(value, comparator));
            finalSet.retainAll(filtered);
            //            for (int j = 0; j < nutrientArray.length; j++) {
            //                if (comparator.equals("<=")) {
            //                    for (int k = 0; k < foodItemList.size(); k++) {
            //                        filtered.addAll(indexes.get(nutrient).rangeSearch(value, comparator));
            //
            //                        //                        if (foodItemList.get(k).getNutrients().get(nutrientArray[i]) <= Double
            //                        //                            .parseDouble(value)) {
            //                        //                            filteredNutrientsList.add(foodItemList.get(k));
            //                        //                        }
            //                    }
            //                } else if (comparator.equals(">=")) {
            //                    for (int k = 0; k < foodItemList.size(); k++) {
            //                        indexes.get(nutrient).rangeSearch(value, comparator);
            //                        //                        if (foodItemList.get(k).getNutrients().get(nutrientArray[i]) >= Double
            //                        //                            .parseDouble(value)) {
            //                        //                            filteredNutrientsList.add(foodItemList.get(k));
            //                        //                        }
            //                    }
            //                } else if (comparator.equals("==")) {
            //                    for (int k = 0; k < foodItemList.size(); k++) {
            //                        indexes.get(nutrient).rangeSearch(value, comparator);
            //                        //                        if (foodItemList.get(k).getNutrients().get(nutrientArray[i]) == Double
            //                        //                            .parseDouble(value)) {
            //                        //                            filteredNutrientsList.add(foodItemList.get(k));
            //                        //                        }
            //                    }
            //                }
            //            }
        }
        returnList.addAll(finalSet);
        return returnList;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override public void addFoodItem(FoodItem foodItem) {
        foodItemList.add(foodItem);
    }

    public void removeFoodItem(FoodItem foodItem) {
        this.foodItemList.remove(foodItem);
    }

    public void clearAllFoodItems() {
        this.foodItemList.clear();
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override public List<FoodItem> getAllFoodItems() {
        return this.foodItemList;
    }

    /************************************ util functions to write to csv **************************************/

    private void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    private void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    private String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;
    }

    //util function to generate csv file
    private void writeLine(Writer w, List<String> values, char separators, char customQuote)
        throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();

        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }

    /************************************************ saveFoodItems **************************************************/

    @Override public void saveFoodItems(String filename) {
        try {
            String csvFile = filename;
            FileWriter writer = new FileWriter(csvFile);
            List<FoodItem> copyFoodList = new ArrayList<FoodItem>(this.foodItemList);

            //sort the ArrayList with name
            Collections.sort(copyFoodList, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            List<String> s = new ArrayList<String>();

            //parsing csv file
            for (int i = 0; i < copyFoodList.size(); i++) {
                s.clear();
                FoodItem fd = copyFoodList.get(i);
                s.add(fd.getID());
                s.add(fd.getName());
                HashMap<String, Double> m = fd.getNutrients();
                for (Map.Entry<String, Double> entry : m.entrySet()) {
                    s.add(entry.getKey());
                    s.add(Double.toString(entry.getValue()));
                }
                this.writeLine(writer, s, ',');
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException!");
        }

    }

}


