package org.example.Class;

public class Project {
    private String id;
    private String name;
    private String type;
    private String property;
    private String Number;
    private int status;
    private String  already;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }




    public Project() {
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

    public Project(String id, String name, String type, String property, String  Number, int  status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.property = property;
        this.Number = Number;
        this.status = status;
        this.already = Number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
