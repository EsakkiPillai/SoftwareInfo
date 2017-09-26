package com.Learn;

import java.util.Scanner;

/*
* Create a Topic name =" readLine" using CMD Window and give Partition Count  as 10
* we will Create a Simple Partition in our class
* */
public class SimplePartitionProducer {

    public void main(String[] args){

        MyownKafka mok = new MyownKafka();
        mok.setBootstrapserver("localhost:9092");
        mok.setKeydeserializer("org.apache.kafka.common.serialization.StringSerializer");
        mok.setValueserializer("org.apache.kafka.common.serialization.StringSerializer");
        mok.setTopicName("readLine");

        mok.setProducerProperties();
        mok.addProducerProperties("partitioner.class","myPartitioner");
        mok.addProducerProperties("speed.sensor.name","IND");

        mok.createProducer();

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        System.out.println("Please enter the Message like below \n KEY:VALUE \n Only IND key will move to a seperate Partition");

        while(!line.equals("exit")){
            String key = line.split(":")[0];
            String value = line.split(":")[1];

            mok.createProducerRecords(key,value);
            mok.sentMessage_sync();
        }

        mok.closeProducer();
        System.out.println("Partition Producer has been Completed");
    }
}
