package ru.homework16.api;


import ru.homework16.api.model.SensorData;

public interface SensorsDataServer {
    void onReceive(SensorData sensorData);
}
