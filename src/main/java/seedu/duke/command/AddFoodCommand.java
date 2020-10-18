package seedu.duke.command;

import seedu.duke.Food;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Add food.
 */
public class AddFoodCommand extends Command {
    protected Food food;
    protected LocalDate date;

    /**
     * Add food and it's respective calories.
     *
     * @param description food description
     * @param calories calories consumed
     */
    public AddFoodCommand(String description, int calories, boolean isFromFile) {
        this.food = new Food(description, calories, isFromFile);
        this.date = LocalDateTime.now().toLocalDate();
    }

    public AddFoodCommand(String description, int calories, boolean isFromFile, LocalDate date) {
        this.food = new Food(description, calories, isFromFile);
        this.date = date;
    }

    @Override
    public void execute() {
        dayMap.addActivity(date.atStartOfDay(), food);
    }


}
