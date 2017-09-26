package com.Learn;
/*
* Will have a Sample Consumer which reads from the Producer
* it uses Auto Commit and CommitAsync method to commit the Offset
* */
public class simpleFileConsumer {

    public static void main(String[] args){
        MyownKafka mok = new MyownKafka();
        mok.setBootstrapserver("localhost:9092");
        mok.setKeydeserializer("org.apache.kafka.common.serialization.StringSerializer");
        mok.setValuedeserializer("org.apache.kafka.common.serialization.StringSerializer");
        mok.setGroupId("MyOwnKafka");
        mok.setTopicName("readBook");

        mok.setConsumerProperties();
        mok.createConsumer();
        mok.createConsumerrecords();
        mok.closeConsumer();

    }
}
