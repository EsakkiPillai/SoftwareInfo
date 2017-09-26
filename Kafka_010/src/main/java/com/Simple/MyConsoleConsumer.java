package com.Simple;

import org.apache.kafka.clients.consumer.*;

import java.util.Arrays;
import java.util.Properties;

/*
* Add the Properties for the Consumer
* */
public class MyConsoleConsumer {
    public static void main(String[] args){
        System.out.println("My Own Consumer Started ....");

        Properties prop = new Properties();
        String topicName="Myownkafka";
        String groupName="myOwnKafkaGroup";

        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, groupName);

        Consumer consumer = new KafkaConsumer(prop);
        consumer.subscribe(Arrays.asList(topicName));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> rec : records) {
                    System.out.println(rec);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            consumer.close();
        }

    }
}
