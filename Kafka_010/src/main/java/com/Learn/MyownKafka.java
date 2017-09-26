package com.Learn;

import com.oracle.jrockit.jfr.Producer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;
import java.util.*;

/*
*we will create all the required Method we use in this Tutorials
* Simple File Producer
* Simple Partitioner
* Simple Consumer
* Simple Consumer manual offset management
* */
public class MyownKafka {
    private String topicName;
    private String bootstrapserver;
    private String keyserializer;
    private String keydeserializer;
    private String valueserializer;
    private String valuedeserializer;
    private String groupId;
    private KafkaConsumer<String,String> consumer;
    private KafkaProducer<String,String> producer;
    private ProducerRecord<String,String> record;
    private Properties prop;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getBootstrapserver() {
        return bootstrapserver;
    }

    public void setBootstrapserver(String bootstrapserver) {
        this.bootstrapserver = bootstrapserver;
    }

    public String getKeyserializer() {
        return keyserializer;
    }

    public void setKeyserializer(String keyserializer) {
        this.keyserializer = keyserializer;
    }

    public String getKeydeserializer() {
        return keydeserializer;
    }

    public void setKeydeserializer(String keydeserializer) {
        this.keydeserializer = keydeserializer;
    }

    public String getValueserializer() {
        return valueserializer;
    }

    public void setValueserializer(String valueserializer) {
        this.valueserializer = valueserializer;
    }

    public String getValuedeserializer() {
        return valuedeserializer;
    }

    public void setValuedeserializer(String valuedeserializer) {
        this.valuedeserializer = valuedeserializer;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setProducerProperties(){
        prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,getBootstrapserver());
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,getKeyserializer());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,getValueserializer());

    }
    public void addProducerProperties(String key,String value){
        prop.put("ProducerConfig."+key,value);
    }
    public void setConsumerProperties(){
        prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,getBootstrapserver());
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,getKeydeserializer());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,getValuedeserializer());
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,getGroupId());

    }
    public void addConsumerProperties(String key,String value){
        prop.put("ConsumerConfig."+key,value);
    }
    public void createProducer(){
        producer = new KafkaProducer<String, String>(prop);

    }
    public void createConsumer(){
        consumer = new KafkaConsumer<String, String>(prop);

    }

    public void subscribeConsumer(){
        consumer.subscribe(Arrays.asList(getTopicName()));
    }

    public void createProducerRecords(String key , String value) {
         record = new ProducerRecord<String, String>(getTopicName(), key , value);
    }

    public void sentMessage_fandf(){
        producer.send(record);
    }

    public void sentMessage_sync(){
        try{
            producer.send(record).get();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Exception Occured While Sending the Message");
        }
    }

    public void sentmessage_async(){

        producer.send(record, new Mycallback());
    }

    public void createConsumerrecords(){
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {

                    System.out.println("record is " + record.value());

                }
                consumer.commitAsync();
            }
        }catch(Exception e){
            e.printStackTrace();
            consumer.commitSync();
        }

    }

    public void rebalanceConsumer(){
        rebalanceListner listnerobj = new rebalanceListner(consumer);
        consumer.subscribe(Arrays.asList(getTopicName()), listnerobj);
    }

    public void rebalanceConsumerrecords(){

        try{
            while(true){
                rebalanceListner listnerobj = new rebalanceListner(consumer);
                ConsumerRecords<String,String> records = consumer.poll(100);
                for (ConsumerRecord<String,String> record : records){
                    System.out.println("record is " + record.topic());
                    System.out.print("record is " + record.partition());
                    System.out.print("record is " + record.value());
                    listnerobj.addOffset (record.topic(), record.partition(),record.offset());

                }
                consumer.commitSync(listnerobj.getCurrentOffset());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void closeProducer(){
        producer.close();
    }
    public void closeConsumer(){
        consumer.close();
    }


}

/*
 * we are creating a new Class "MYcallBack" for Asynchronous Send Method
 *  */
 class Mycallback implements Callback {

    public void onCompletion(RecordMetadata recordMetadata, Exception e) {

        if(e != null){
            System.out.println("Asynchronous sent failed with exception ");

        }else{

            System.out.printf("record : " + "has the topic Name " + recordMetadata.topic() + "\t record has the offset \t" +recordMetadata.offset() + "records has the value \t" +recordMetadata.toString());
        }


    }
}

/*
* Creating the Custom Partitioner Class
* */

class myPartitioner implements Partitioner{

    private String speedSensorName;

    public void configure(Map<String, ?> map) {
       speedSensorName = map.get("speed.sensor.name").toString();
    }

    public void close() {

    }

    /*
    * We have to Write the Partitioner Logic in Here
    * */
    public int partition(String topicName, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int p =0;

        List<PartitionInfo> partitions = cluster.partitionsForTopic(topicName);
        int numPartitions = partitions.size();

        int sp = (int)Math.abs(numPartitions * 0.3);
        if( (keyBytes==null) || (!(key instanceof  String) )){
            throw new InvalidRecordException("All messages must have sensor name as key");
        }
        if ( ((String)key).equals(speedSensorName) ) {
            p = (Utils.murmur2(valueBytes)) % sp;
        } else {
            p = (Utils.murmur2(keyBytes)) % (numPartitions - sp) + sp;
        }

        System.out.println("Key = " + (String)key + " Partition = " + p );
        return p;

    }

}


/*
* Creating the Rebalance Listner
*
* */

class rebalanceListner implements ConsumerRebalanceListener{

/*
*  just before u take away ur permission to the partition
*  */
/*topic name and offset are the key */
private KafkaConsumer<String,String> consumer ;
private Map<TopicPartition,OffsetAndMetadata> currentOffset = new HashMap();


    public rebalanceListner(KafkaConsumer consumer){
        this.consumer = consumer;
    }

    public void onPartitionsRevoked(Collection<TopicPartition> collection) {

        System.out.println("Following Partitions were Revoked ");
        for(TopicPartition partition :collection){
            System.out.println(partition.topic());
            System.out.println(partition.partition()+",");
        }

        System.out.println("Following Partitions are commited  ");
        for(TopicPartition partition :currentOffset.keySet()){
            System.out.println(partition.topic());
            System.out.println(partition.partition()+",");
        }

        consumer.commitSync(getCurrentOffset());
        currentOffset.clear();

    }

/*
* after rebalancing is complete before u consuming the records from new Partitions
* */
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

        System.out.println("Following Partitions were Assigned ");
        for(TopicPartition partition :partitions){
            System.out.println(partition.topic());
            System.out.println(partition.partition());
        }
    }

    public  void addOffset(String topic, int partition, long offset) {

        currentOffset.put(new TopicPartition(topic,partition),new OffsetAndMetadata(offset,"Commit"));
    }

    public Map<TopicPartition,OffsetAndMetadata> getCurrentOffset()
    {
        return currentOffset;
    }
}