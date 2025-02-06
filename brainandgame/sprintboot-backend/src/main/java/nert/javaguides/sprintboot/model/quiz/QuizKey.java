package nert.javaguides.sprintboot.model.quiz;

public class QuizKey {
    private Long id;

    private String title;

    private String description;

    private boolean open;

    public QuizKey(Long id, String title, String description, boolean open) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.open = open;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
