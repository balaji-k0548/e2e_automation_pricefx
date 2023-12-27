package com.syscolab.qe.core.kafka;

import com.syscolab.qe.core.common.LoggerUtil;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.testng.Assert;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * This is the util class for Producer
 * @author Sandeep Perera
 */
public class ProducerUtil {
    /**
     * Default constructor for ProducerUtil class
     */
    private ProducerUtil(){}

    /**
     * This will send the message
     * @param configFilePath configured file path for the message to be sent
     * @param topic message topic
     * @param message message body to send
     * @throws IOException throwing IOException
     */
    public static void sendMessage(String configFilePath, String topic, String message) throws IOException {
        final Properties props = loadConfig(configFilePath);
        Producer<String, String> producer = new KafkaProducer<>(props);
        producer.send(
                new ProducerRecord<>(topic, message),
                (event, ex) -> {
                    if (ex != null) {
                        ex.printStackTrace();
                        Assert.fail(ex.getMessage());
                    }
                    else
                        LoggerUtil.logINFO("Produced event to topic "+topic+": message = "+ message);
                });
        producer.flush();
        producer.close();
    }

    /**
     * This will load the Configuration file path and return its properties
     * @param configFile configured file path for the message
     * @return its Properties
     * @throws IOException throwing IOException
     */
    public static Properties loadConfig(final String configFile) throws IOException {
        if (!Files.exists(Paths.get(configFile))) {
            throw new IOException(configFile + " not found.");
        }
        final Properties cfg = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            cfg.load(inputStream);
        }
        return cfg;
    }

    /**
     * This will send the message in key-value pair format
     * @param configFilePath configured file path for the message to be sent
     * @param topic topic value
     * @param key key value
     * @param avroRecordMap Producer Record Hash map
     * @throws IOException throwing IOException
     */
    public static void sendMessage(String configFilePath, String topic, String key,  HashMap<String, String> avroRecordMap) throws IOException {
        final Properties props = loadConfig(configFilePath);
        KafkaProducer producer = new KafkaProducer(props);
        ProducerRecord<Object, Object> record = new ProducerRecord<>(topic, key, avroRecordMap);

        try {
            producer.send(record);
            LoggerUtil.logINFO("Message sent " + record.key());
        } catch(SerializationException e) {
            System.out.println(e.getCause());
            Assert.fail(e.getCause().toString());
        }catch (Exception e){
            LoggerUtil.logINFO(e.getMessage());
            Assert.fail(e.getCause().toString());
        }
        finally {
            producer.flush();
            producer.close();
        }
    }

    /**
     * This will send the message for Performance evaluation
     * @param configFilePath configured file path for the message to be sent
     * @param topic message topic
     * @param userSchema User Schema
     * @param key key value
     * @param avroRecordMap Producer Record Hash map
     * @throws IOException throwing IOException
     */
    public static void sendMessagePerf(String configFilePath, String topic, String userSchema, String key,  HashMap<String, String> avroRecordMap) throws IOException {
        final Properties props = loadConfig(configFilePath);

        KafkaProducer<String,GenericRecord> producer = new KafkaProducer<>(props);

        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(userSchema);
        GenericRecord avroRecord = new GenericData.Record(schema);
        String produceTime = String.valueOf(System.currentTimeMillis());

        for (Map.Entry<String, String> avroRecordEntry : avroRecordMap.entrySet()){
            avroRecord.put(avroRecordEntry.getKey(), avroRecordEntry.getValue());
        }

        ProducerRecord<String, GenericRecord> producerRecord = new ProducerRecord<>(topic, key, avroRecord);
        producerRecord.headers().add(new RecordHeader("createTime", produceTime.getBytes()));

        try {
            producer.send(producerRecord);
        } catch(SerializationException e) {
            LoggerUtil.logINFO(e.getCause().toString());
            Assert.fail(e.getCause().toString());
        }catch (Exception e){
            LoggerUtil.logINFO(e.getMessage());
            Assert.fail(e.getCause().toString());
        }
        finally {
            producer.flush();
            producer.close();
        }
    }

}
