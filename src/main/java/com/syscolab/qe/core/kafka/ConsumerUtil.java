package com.syscolab.qe.core.kafka;

import com.syscolab.qe.core.common.LoggerUtil;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.header.Header;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Properties;
/**
 * This is the util class for Consumer
 * @author Malsha Udani
 */
public class ConsumerUtil {

    static Consumer<String, GenericRecord> consumer = null;
    /**
     * Default constructor for ConsumerUtil class
     */
    private ConsumerUtil(){}

    /**
     * This method will create a LinkedHashMap and will write the message to it
     * @param configFilePath configured file path for receiving the message
     * @param topic message topic
     * @param millisecondsToWait wait time in milliseconds
     * @return message
     * @throws IOException throwing IOException
     */
    public static LinkedHashMap<String, String> receiveMessage(String configFilePath, String topic, int millisecondsToWait) throws IOException {
        LinkedHashMap<String, String> message = new LinkedHashMap<>();
        final Properties props = ProducerUtil.loadConfig(configFilePath);

        final Consumer<String, GenericRecord> consumer = new KafkaConsumer<String, GenericRecord>(props);
        consumer.subscribe(Arrays.asList(topic));

        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            while (new Timestamp(System.currentTimeMillis()).getTime() - timestamp.getTime() <= millisecondsToWait ) {
                ConsumerRecords<String, GenericRecord> records = consumer.poll(100);
                for (ConsumerRecord<String, GenericRecord> consumerRecord : records) {
                    message.put(consumerRecord.key(), consumerRecord.value().toString());
                    LoggerUtil.logINFO("offset = "+consumerRecord.offset()+", key = "+consumerRecord.key()+", value = "+consumerRecord.value()+" \n");
                }
            }
        } finally {
            consumer.close();
        }
        return message;
    }

    /**
     * This method will receive the message response
     * @param configFilePath configured file path for receiving the message
     * @param topic message topic
     * @param millisecondsToWait wait time in milliseconds
     * @throws IOException throwing IOException
     */
    public static void receiveMessageResponse(String configFilePath, String topic, int millisecondsToWait) throws IOException {

        String producedTime;
        long latency;
        FileOutputStream file = new FileOutputStream(System.getProperty("user.dir")+"/Results.csv", true);
        try (PrintStream print = new PrintStream(file)) {
            final Properties props = ProducerUtil.loadConfig(configFilePath);

            final Consumer<String, GenericRecord> consumer = new KafkaConsumer<String, GenericRecord>(props);
            consumer.subscribe(Arrays.asList(topic));

        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            while (new Timestamp(System.currentTimeMillis()).getTime() - timestamp.getTime() <= millisecondsToWait ) {
                ConsumerRecords<String, GenericRecord> records = consumer.poll(100);
                for (ConsumerRecord<String, GenericRecord> consumerRecord : records) {
                    for (Header header : consumerRecord.headers()) {
                        producedTime =  new String(header.value());
                        latency = consumerRecord.timestamp() - Long.parseLong(producedTime);
                        print.println("Latency: " + latency +" createdTime: " + new String(header.value()) +" offset: " + consumerRecord.offset() + " processedTime: " + consumerRecord.timestamp() +" value: " + consumerRecord.value());
                    }
                }
            }
        } finally {
            consumer.close();
            print.close();
            file.close();
        }
    }
    }
    }

