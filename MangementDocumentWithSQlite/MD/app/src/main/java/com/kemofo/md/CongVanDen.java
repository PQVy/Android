package com.kemofo.md;

public class CongVanDen {
    private int id;
    private String number;
    private String releaseUnit;
    private String description;
    private byte[] image;

    public CongVanDen() {
    }

    public CongVanDen(String number, String releaseUnit, String description, byte[] image) {
        this.number = number;
        this.releaseUnit = releaseUnit;
        this.description = description;
        this.image = image;
    }

    public CongVanDen(String number, String releaseUnit, String description, byte[] image, int id) {
        this.number = number;
        this.releaseUnit = releaseUnit;
        this.description = description;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseUnit() {
        return releaseUnit;
    }

    public void setReleaseUnit(String releaseUnit) {
        this.releaseUnit = releaseUnit;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
