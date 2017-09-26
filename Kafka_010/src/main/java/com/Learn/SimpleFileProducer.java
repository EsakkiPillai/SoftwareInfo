package com.Learn;

import java.io.*;
import java.util.Scanner;

/*
* We will Create A Simple Producer  that will keep on reading the Tex File and Send the record
* it will ask the user to resent the file Once it has finish it up
* */
public class SimpleFileProducer {

    public static void main(String[] args){

        MyownKafka mok = new MyownKafka();
        mok.setBootstrapserver("localhost:9092");
        mok.setKeydeserializer("org.apache.kafka.common.serialization.StringSerializer");
        mok.setValueserializer("org.apache.kafka.common.serialization.StringSerializer");
        mok.setTopicName("readBook");

        mok.setProducerProperties();
        mok.createProducer();

        /** Reading the File until user types exit */

        System.out.println("Do u wish to read the File \n if Yes type YES or type EXIT \n\n");
        Scanner  in = new Scanner(System.in);
        String inpline = in.nextLine();

        while(!inpline.equals("exit")){
            BufferedReader reader = null;
            int key = 1;
            try {
                reader = new BufferedReader(new FileReader("C:\\Users\\1532894\\Documents\\Spark\\Kafka\\Offsetmanagement.txt"));
                String line = reader.readLine();
                while(line!=null){
                    mok.createProducerRecords("Line:--"+key,line);
                    mok.sentMessage_fandf();
                    line = reader.readLine();
                    key++;
                }

            }catch(FileNotFoundException f){
                f.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally{
                try{
                    reader.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
            System.out.println("Do u wish to read the File again \n if Yes type YES or type EXIT \n\n");
            inpline = in.nextLine();
        }

        System.out.println("My own Console Producer Sends all the Message....");
        mok.closeProducer();
    }
}
