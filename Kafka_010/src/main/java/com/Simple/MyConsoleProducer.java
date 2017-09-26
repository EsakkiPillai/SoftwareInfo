package com.Simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/*
* Create a Properties Files and add BootStrap Server key value serializer
* Create Producer Object with Properties
* Producer Record Object
* send the record
* */
public class MyConsoleProducer {

    public static void main(String[] args){
        System.out.println("Starting The my own Console Producer....");
        Properties prop = new Properties();
        String topicName = "Myownkafka";
        String key = "Key--";
        String value = "Value--";

        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"nn01.itversity.com:6667,nn02.itversity.com:6667,rm01.itversity.com:9092");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

        Producer<String,String> producer = new KafkaProducer<String,String>(prop);
        for(int i =0;i<=100;i++){
            ProducerRecord record = new ProducerRecord(topicName,key+i,value+i);
            producer.send(record);
        }

        System.out.println("My own Console Producer Sends all the Message....");
    }







}

