package com.example.testedittext.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.testedittext.entities.enums.ElementsOfElectricalInstallations;
import com.example.testedittext.entities.enums.GroundingSystem;
import com.example.testedittext.entities.enums.TypeOfWork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;


public class ReportEntity implements Serializable {

    private String name;
    private String numberReport;
    private String date;
    private String customer;
    private String object;
    private String address;
    private String characteristic;
    private String temperature;
    private String humidity;
    private String pressure;
    private String engineer;
    private String test_type;
    private Set<TypeOfWork> type_of_work;
    private Set<ElementsOfElectricalInstallations> elements;
    private GroundingSystem groundingSystem;
    private boolean newness;
    private ArrayList<Shield> shields;




    public ReportEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReportEntity{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", customer='" + customer + '\'' +
                ", object='" + object + '\'' +
                ", address='" + address + '\'' +
                ", characteristic='" + characteristic + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", engineer='" + engineer + '\'' +
                ", test_type='" + test_type + '\'' +
                ", type_of_work=" + type_of_work +
                ", elements=" + elements +
                ", groundingSystem=" + groundingSystem +
                ", newness=" + newness +
                ", shields=" + shields +
                '}';
    }


    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }

    public Set<TypeOfWork> getType_of_work() {
        return type_of_work;
    }

    public void setType_of_work(Set<TypeOfWork> type_of_work) {
        this.type_of_work = type_of_work;
    }

    public Set<ElementsOfElectricalInstallations> getElements() {
        return elements;
    }

    public void setElements(Set<ElementsOfElectricalInstallations> elements) {
        this.elements = elements;
    }

    public GroundingSystem getGroundingSystem() {
        return groundingSystem;
    }

    public void setGroundingSystem(GroundingSystem groundingSystem) {
        this.groundingSystem = groundingSystem;
    }

    public boolean isNewness() {
        return newness;
    }

    public void setNewness(boolean newness) {
        this.newness = newness;
    }

    public ArrayList<Shield> getShields() {
        return shields;
    }

    public void setShields(ArrayList<Shield> shields) {
        this.shields = shields;
    }

    public String getNumberReport() {
        return numberReport;
    }

    public void setNumberReport(String numberReport) {
        this.numberReport = numberReport;
    }
}
