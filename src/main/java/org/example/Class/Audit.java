package org.example.Class;

public class Audit {
    private String id;
    private String projectName;
    private String username;
    private String type;
    private String property;
    private String Number;
    private String already;

    public Audit() {
    }

    public Audit(String id, String projectName, String username, String type, String property, String number, String already) {
        this.id = id;
        this.projectName = projectName;
        this.username = username;
        this.type = type;
        this.property = property;
        this.Number = number;
        this.already = already;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getAlready() {
        return already;
    }

    public void setAlready(String already) {
        this.already = already;
    }
}
