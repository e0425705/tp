package seedu.duke.logic.preparecommand;

import seedu.duke.Trakcal;
import seedu.duke.command.Command;
import seedu.duke.exception.CalorieCountException;
import seedu.duke.exception.DateLimitException;
import seedu.duke.exception.DescriptionLengthExceedException;
import seedu.duke.exception.EmptyDescriptionException;
import seedu.duke.exception.InvalidCalorieException;
import seedu.duke.exception.InvalidNumberOfArguments;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public abstract class PrepareCommand {
    public String[] description;
    public LocalDateTime date;
    public static final int MAXIMUM_DESCRIPTION_LENGTH = 40;
    public static final int MAXIMUM_CALORIE_COUNT = 3000;
    public static final int MINIMUM_CALORIE_COUNT = 0;
    public static final String APPLICATION_LAUNCH_DATE = "2020-11-01";
    protected static final int ALPHABET_WITH_SLASH_LENGTH = 2;

    public PrepareCommand(String[] description) {
        this.description = description;
        setDate();
    }


    private void setDate() {
        this.date = LocalDateTime.now();
    }


    public abstract Command prepareCommand() throws Exception;

    /**
     * Checks for index of the delete command.
     *
     * @param index index of the delete command
     * @throws IndexOutOfBoundsException if index is below 0
     */
    protected void checkIndex(int index) throws IndexOutOfBoundsException {
        if (index > Trakcal.calList.getActivityList(date).getNumberOfActivities() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Process date input by user.
     *
     * @param dateInput date input by user
     * @return date
     * @throws DateTimeParseException if the string is in the incorrect format
     */
    protected LocalDate processDate(String dateInput) throws DateTimeParseException {
        return LocalDate.parse(dateInput);
    }


    /**
     * Returns current date.
     *
     * @return current date
     */
    protected LocalDate currentDate() {
        LocalDate date = LocalDate.now();

        return date;
    }

    /**
     * Checks if the date is valid.
     *
     * @param dateInput is date input by user
     * @param dateIndex index date is at
     * @return true if date is valid
     * @throws DateLimitException if date exceeds limit
     */
    protected LocalDate checkDateValidity(String dateInput, int dateIndex) throws DateLimitException {
        LocalDate date = processDate(dateInput.substring(dateIndex + ALPHABET_WITH_SLASH_LENGTH).trim());
        LocalDate current = LocalDate.now();
        LocalDate past = processDate(APPLICATION_LAUNCH_DATE);
        if (date.isAfter(current)) {
            throw new DateLimitException();
        } else if (date.isBefore(past)) {
            throw new DateLimitException();
        } else {
            return date;
        }
    }

    /**
     * Checks if calorie input by user is empty.
     *
     * @param calorieInput calorie input by user
     * @return calorie count if it is not empty
     * @throws InvalidCalorieException if the calorie count is empty
     */
    protected int parseCalorie(String calorieInput) throws InvalidCalorieException {
        if (!calorieInput.equals("")) {
            return Integer.parseInt(calorieInput);
        } else {
            throw new InvalidCalorieException();
        }
    }

    /**
     * Checks if the description is filled.
     *
     * @param description description input by user
     * @return true if no error in description input
     * @throws EmptyDescriptionException if description input has error
     */
    protected boolean isDescriptionNotEmpty(String description) throws EmptyDescriptionException {
        if (description.equals(" ") || description.equals("")) {
            throw new EmptyDescriptionException();
        } else {
            return true;
        }
    }

    /**
     * Checks if the calorie input is within accepted range.
     *
     * @param calorie calories input by user
     * @return true is calorie is within range
     * @throws CalorieCountException if calorie not within range
     */
    protected boolean isCaloriesWithinRange(int calorie) throws CalorieCountException {
        if (calorie <= MINIMUM_CALORIE_COUNT || calorie > MAXIMUM_CALORIE_COUNT) {
            throw new CalorieCountException();
        } else {
            return true;
        }
    }

    /**
     * Checks if the description character counts is within accepted range.
     *
     * @param description description input by user
     * @return true if description length is within range
     * @throws DescriptionLengthExceedException if description exceeds range
     */
    protected boolean isDescriptionLengthWithinRange(String description) throws DescriptionLengthExceedException {
        if (description.length() >= MAXIMUM_DESCRIPTION_LENGTH) {
            throw new DescriptionLengthExceedException();
        } else {
            return true;
        }
    }

    protected boolean isNumberOfArgumentsValid(int limit) throws InvalidNumberOfArguments {
        if (description.length != limit) {
            throw new InvalidNumberOfArguments();
        }
        return true;
    }

}
