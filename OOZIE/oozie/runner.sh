hadoop fs -mkdir /user/esakkipillai/hadoop/oozie_demo/cordfile
hadoop fs -mkdir /user/esakkipillai/hadoop/oozie_demo/cordfile/retail_db
hadoop fs -mkdir /user/esakkipillai/hadoop/oozie_demo/cordfile/src
hadoop fs -mkdir /user/esakkipillai/hadoop/oozie_demo/cordfile/coordinatorconf
hadoop fs -mkdir /user/esakkipillai/hadoop/oozie_demo/cordfile/wfconf

hadoop fs -put /home/esakkipillai/hadoop/oozie_demo/cordfile/workflow.xml /user/esakkipillai/hadoop/oozie_demo/cordfile/wfconf/
hadoop fs -put /home/esakkipillai/hadoop/oozie_demo/cordfile/coordinator.xml /user/esakkipillai/hadoop/oozie_demo/cordfile/coordinatorconf/
hadoop fs -put /home/esakkipillai/hadoop/oozie_demo/cordfile/job.properties /user/esakkipillai/hadoop/oozie_demo/cordfile/

oozie job -oozie http://nn01.itversity.com:11000/oozie -config /home/esakkipillai/hadoop/oozie_demo/cmfile/job.properties -run

oozie job -oozie http://nn01.itversity.com:11000/oozie -info

hadoop fs -rm -r  /user/esakkipillai/hadoop/oozie_demo/cordfile


hadoop fs -put  /home/esakkipillai/hadoop/oozie_demo/cordfile/trigger /user/esakkipillai/hadoop/oozie_demo/cmfile/src

hadoop fs -rm -r /user/esakkipillai/hadoop/oozie_demo/cmfile/src/trigger