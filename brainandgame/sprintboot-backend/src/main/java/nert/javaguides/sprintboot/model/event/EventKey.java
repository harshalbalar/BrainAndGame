package nert.javaguides.sprintboot.model.event;

public class EventKey {
    private Long id;
    private String name;
    // Set isOpen to false by default
    private boolean open;

    public EventKey(Long id, String name, boolean open) {
        this.id = id;
        this.name = name;
        this.open = open;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean open() {
        return open;
    }

    public void setOpen(boolean open) {
        open = open;
    }
}
