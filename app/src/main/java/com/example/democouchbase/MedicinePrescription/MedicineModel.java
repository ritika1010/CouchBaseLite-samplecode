package com.example.democouchbase.MedicinePrescription;

class MedicineModel {
    String name;
    String frequency;
    String power;

    public MedicineModel(String name, String frequency, String power) {
        this.name = name;
        this.frequency = frequency;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getPower() {
        return power;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setPower(String power) {
        this.power = power;
    }
}
