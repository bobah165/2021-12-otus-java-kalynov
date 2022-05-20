package ru.homework16.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.homework16.api.SensorDataProcessor;
import ru.homework16.api.model.SensorData;
import ru.homework16.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final PriorityBlockingQueue<SensorData> sensorsDataQueue;
    private final AtomicBoolean isRead = new AtomicBoolean(true);

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        sensorsDataQueue = new PriorityBlockingQueue<>(bufferSize, Comparator.comparing(SensorData::getMeasurementTime));
    }

    @Override
    public synchronized void process(SensorData data) {
        if (sensorsDataQueue.stream().toList().size() >= bufferSize) {
            isRead.set(true);
            flush();
        }
        sensorsDataQueue.offer(data);
    }


    public synchronized void flush() {
        if (isRead.get()) {
            try {
                List<SensorData> sensorDataList = getSensorDataList();
                writer.writeBufferedData(sensorDataList);
                sensorsDataQueue.clear();
                isRead.set(false);
            } catch (Exception e) {
                log.error("Ошибка в процессе записи буфера", e);
            }
        }
    }

    @Override
    public synchronized void onProcessingEnd() {
        isRead.set(true);
        flush();
    }

    private List<SensorData> getSensorDataList() {
        List<SensorData> sensorDataList = new ArrayList<>();
        while (!sensorsDataQueue.isEmpty()) {
            sensorDataList.add(sensorsDataQueue.poll());
        }
        return sensorDataList;
    }
}
