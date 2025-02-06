package nert.javaguides.sprintboot.model.user;

public class UserKey {
    private Long id;
    private String username;
    private String linkedInLink;
    private String instagramLink;
    private String role;

    public UserKey(Long id, String username, String linkedInLink, String instagramLink, String role) {
        this.id = id;
        this.username = username;
        this.linkedInLink = linkedInLink;
        this.instagramLink = instagramLink;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
