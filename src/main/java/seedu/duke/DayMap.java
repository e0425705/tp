package seedu.duke;

import seedu.duke.exception.KeywordNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static seedu.duke.Ui.displayEmptyActivityCounterMessage;
import static seedu.duke.Ui.displaySavedMessage;


/**
 * Use hashmap of to store all the data.
 * The key of the hashmap would be the date and the value would be the activityList for that day.
 */
public class DayMap {

    private HashMap<LocalDate, ActivityList> dayMap;
    private ActivityList lastSeenList;

    public DayMap() {
        this.dayMap = new HashMap<>();
        this.lastSeenList = new ActivityList();
    }

    public void setLastSeenList(ActivityList activityList) {
        this.lastSeenList = new ActivityList();
        this.lastSeenList = activityList;
    }

    public ActivityList getLastSeenList() {
        return this.lastSeenList;
    }

    /**
     * Adds activity into activityList under the corresponding dateTime.
     * Creates a new activityList if there are none under the specified date.
     * @param dateTime Specified date to extract activitylist from the dayMap.
     * @param activity Description of the activity, the userinput
     */
    public void addActivity(LocalDateTime dateTime, Activity activity) {
        ActivityList alist = this.getActivityList(dateTime);
        if (alist == null) {
            dayMap.put(dateTime.toLocalDate(), new ActivityList());
            alist = this.getActivityList(dateTime);
        }
        alist.addActivity(activity);
        //System.out.println("Total calorie count for "
        // + dateTime.toLocalDate().toString() + " = " + alist.getNetCalorie());
    }




    public void insertActivity(int index, Activity activity) {
        lastSeenList.insertActivity(index, activity);
    }

    public ArrayList getArrayList(LocalDateTime dateTime) {
        return getActivityList(dateTime).getArrayList();
    }

    //returns the activityList for the given dateTime
    public ActivityList getActivityList(LocalDateTime dateTime) {
        return dayMap.get(dateTime.toLocalDate());
    }

    //returns the number of activities for the given day
    public int getSizeOfActivityList(LocalDateTime dateTime) {
        ActivityList alist = this.getActivityList(dateTime);
        if (alist == null) {
            return 0;
        } else {
            return alist.getNumberOfActivities();
        }
    }

    public int getNetCalorieOfDay(LocalDate date) {
        ActivityList alist = getActivityList(date.atStartOfDay());
        return alist.getNetCalorie();
    }

    public HashMap<LocalDate, ActivityList> getHashMap() {
        return dayMap;
    }

    /**
     * Prints the activities for the given date.
     * @param date specified date to print the list
     */
    public void printActivityList(LocalDate date) {
        if (!dayMap.containsKey(date)) {
            displayEmptyActivityCounterMessage();
        } else {
            getActivityList(date.atStartOfDay()).printList();
        }
    }

    /**
     * Finds the activities containing a keyword.
     * @param description is the keyword where the activity should contain
     * @throws KeywordNotFoundException when the keyword is not found in any activity
     */
    public void listActivitiesContaining(String description) throws KeywordNotFoundException {

        setLastSeenList(new ActivityList());

        Iterator it = dayMap.entrySet().iterator();
        int activityFindCounter = 0;
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry) it.next();
            String date = pair.getKey().toString();
            ActivityList activities = (ActivityList) pair.getValue();
            int activityCounter = activities.getNumberOfActivities();

            if (activityCounter > 0) {
                for (int i = 0; i < activityCounter; i++) {
                    String currentLine = activities.getActivity(i).toString();
                    String descriptionToCheck = currentLine.substring(currentLine.indexOf("|") + 1);
                    descriptionToCheck = descriptionToCheck.substring(0, descriptionToCheck.indexOf("|")).trim();
                    if (descriptionToCheck.contains(description)) {
                        //System.out.println((activityFindCounter + 1) + ". " + date + " " + currentLine);
                        lastSeenList.addActivity(activities.getActivity(i));
                        activityFindCounter++;
                    }
                }
            }
        }
        if (activityFindCounter == 0) {
            throw new KeywordNotFoundException();
        }
        //displaySavedMessage();
    }

    /**
     * Deletes the activity with a given index.
     * @param index is the index of the activity to be deleted
     * @throws IndexOutOfBoundsException if the index provided is out of range
     */
    public void deleteActivity(int index) throws IndexOutOfBoundsException {

        if (lastSeenList.isValidIndex(index)) {
            Activity activityToMatch = lastSeenList.getActivity(index);
            //if previous command was the list command then this will straight away delete the activity
            // from the list in the daymap
            lastSeenList.removeActivity(index);

            //if all the activities in a date is deleted, this is the key to be removed from the daymap
            LocalDate keyToDelete = null;

            //iterating through the entire daymap to find the activity to delete
            Iterator it = dayMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                ActivityList activities = (ActivityList) pair.getValue();
                int activityCounter = activities.getNumberOfActivities();

                if (activityCounter > 0) {
                    for (int i = 0; i < activityCounter; i++) {
                        if (activityToMatch.equals(activities.getActivity(i))) {
                            activities.removeActivity(i);
                            //if deleted the last item in the ActivityList then obtain the key to be deleted from daymap
                            if (activities.getNumberOfActivities() == 0) {
                                keyToDelete = (LocalDate) pair.getKey();
                            }
                            break;
                        }
                    }
                    //If encountered a activitylist with a count of 0,
                    // which will be resulted if deleted the last item of ActivityList from a list command
                } else if (activityCounter == 0) {
                    keyToDelete = (LocalDate)pair.getKey();
                }
            }
            //removes key from the daymap
            dayMap.remove(keyToDelete);
            //displaySavedMessage();
        } else {
            throw new IndexOutOfBoundsException();
        }

    }

    public void move(int indexToBeMovedFrom, int indexToBeInsertedBelow) throws IndexOutOfBoundsException {
        lastSeenList.moveActivity(indexToBeMovedFrom - 1, indexToBeInsertedBelow);
    }

    /**
     * Sets the activities at a given date as a string.
     * For e.g, 2020-10-11: [F] | apple | 50, [F] | banana | 100, [E] | pushup | 10, [E] | jogging | 60.
     * @param dateTime is the specified date
     * @return activities as a string for the given date
     */
    public String toString(LocalDateTime dateTime) {
        ActivityList alist = this.getActivityList(dateTime);
        return dateTime.toLocalDate().toString() + ", " + alist.toString();
    }

    public void printList(LocalDate date) {
        System.out.println(date.toString());
        getActivityList(date.atStartOfDay()).printList();
    }
}
