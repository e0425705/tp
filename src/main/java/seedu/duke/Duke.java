package seedu.duke;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Scanner;

import static seedu.duke.ActivityList.INITIALISE;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");

        /**
         * Create user profile for first time user
         * Edit user profile
         */

        Scanner input = new Scanner(System.in);
        System.out.println("Hello " + input.nextLine());

        /**
         * Calorie List and List
         */
        // Example code to use calorie list.
        //DayMap calList = new DayMap();

       // LocalDateTime adatetime = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40);

//        calList.addActivity(adatetime, 10);
//        calList.addActivity(adatetime, 11);
//        calList.addActivity(adatetime, 12);

        //System.out.println(calList.toString(aDateTime));
        //System.out.println("Size of activity list: " + calList.getSizeOfActivityList(aDateTime));


        /**
         * Add exercise/food with their respective calories
         */
        DayMap calList = new DayMap();
        LocalDateTime date = LocalDateTime.now();
        while (true) {
            Scanner in = new Scanner(System.in);
            String userInput = in.nextLine();
            try {
                if (userInput.startsWith("add f/")) {
                    int calorieIndex = userInput.indexOf("c/");
                    int calories = Integer.parseInt(userInput.substring(calorieIndex + 2).trim());
                    userInput = userInput.substring(6, calorieIndex - 1).trim();
                    calList.addActivity(date, userInput, calories, "food"); //daymap equivalent
                    //used method inside daymap to get size of the activitylist instead
                    System.out.println("Current number of activities is: " + calList.getSizeOfActivityList(date));
                } else if (userInput.startsWith("add e/")) {
                    int calorieIndex = userInput.indexOf("c/");
                    int calories = Integer.parseInt(userInput.substring(calorieIndex + 2).trim());
                    userInput = userInput.substring(6, calorieIndex - 1).trim();
//                    numOfActivities = actList.addExercise(userInput, calories);
                    calList.addActivity(date, userInput, calories, "exercise"); //daymap equivalent
                    System.out.println("Current number of activities is: " + calList.getSizeOfActivityList(date));
                } else if (userInput.startsWith("find d/")) {
                    userInput = userInput.substring(7).trim();
                    System.out.println("Here are the matching descriptions: ");
                    //used method inside daymap to get size of the activitylist instead
                    for (int i = 0; i < calList.getSizeOfActivityList(date); i++) {
                        //created new method to get the arraylist inside the activity list from the daymap
                        //maybe can find a better implementation of this later on
                        String description = calList.getArrayList(date).toArray()[i].toString().substring(
                                0, calList.getArrayList(date).toArray()[i].toString().indexOf(",")).trim();
                        if (description.contains(userInput)) {
                            System.out.println(calList.getArrayList(date).toArray()[i]);
                        }
                    }
                } else if (userInput.startsWith("find c/")) {
                    userInput = userInput.substring(7).trim();
                    System.out.println("Here are the matching descriptions: ");
                    //used method inside daymap to get size of the activitylist instead
                    for (int i = 0; i < calList.getSizeOfActivityList(date); i++) {
                        //created new method to get the arraylist inside the activity list from the daymap
                        //maybe can find a better implementation of this later on
                        String calories = calList.getArrayList(date).toArray()[i].toString().substring(
                                calList.getArrayList(date).toArray()[i].toString().indexOf(",") + 1).trim();
                        if (calories.equals(userInput)) {
                            System.out.println(calList.getArrayList(date).toArray()[i]);
                        }
                    }
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Something went wrong!! I do not understand what you mean.\n"
                        + "There could be an error in the way of input.\n"
                        + "Please do input 'help' for the commands and their respective input format.");
            } catch (Exception e) {
                System.out.println("Something went wrong!! I do not understand what you mean.\n"
                        + "There could be an error in the way of input.\n"
                        + "Please do input 'help' for the commands and their respective input format.");
            }
            /**
             * Remove/Delete index from current list
             * Remove/Delete whole list
             */

            /**
             * Find exercise/food description
             * Find calorie count
             */
        }
    }
}