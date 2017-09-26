package com.Learn;

/*
* we are using Manual Offset Method
*
* */

public class ManualFileConsumer {

    public static void main(String[] args){

        MyownKafka mok = new MyownKafka();
        mok.setBootstrapserver("localhost:9092");
        mok.setKeydeserializer("org.apache.kafka.common.serialization.StringSerializer");
        mok.setValuedeserializer("org.apache.kafka.common.serialization.StringSerializer");
        mok.setGroupId("MyOwnKafka");
        mok.setTopicName("readBook");
        mok.setConsumerProperties();
        mok.addConsumerProperties("AUTO_OFFSET_RESET_CONFIG","false");
        mok.createConsumer();
        mok.rebalanceConsumer();
        mok.rebalanceConsumerrecords();
        System.out.println("we are closing the Manaula Offset File Consumer");
        mok.closeConsumer();
    }
}
