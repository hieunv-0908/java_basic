package bai01;

public class EditAction {
    private String description;
    private String time;

    // Constructor
    public EditAction(String description, String time) {
        this.description = description;
        this.time = time;
    }

    // Getter
    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    // Setter
    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Mô tả: " + description + " | Thời gian: " + time;
    }
}