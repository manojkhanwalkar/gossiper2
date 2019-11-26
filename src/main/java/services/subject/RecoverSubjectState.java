package services.subject;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import data.Subject;
import data.User;
import manager.SubjectManager;
import manager.UserManager;
import persistence.DynamoDBManager;
import persistence.SubjectRecord;
import persistence.UserRecord;

import static persistence.DynamoDBManager.getHandle;

public class RecoverSubjectState {

    DynamoDBManager dynamoDBManager = new DynamoDBManager();

    String instance = System.getProperty("instance");



    public void recover()
    {

        SubjectManager subjectManager = SubjectManager.getInstance();


        final AmazonDynamoDB ddb = getHandle();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);



        DynamoDBScanExpression  scanExpression = new DynamoDBScanExpression();
        var scanResult = mapper.scan(SubjectRecord.class, scanExpression);

        scanResult.stream().filter(r->
                (instance.equalsIgnoreCase("subject1") && (Math.abs(r.getSubjectId().hashCode()%2)==0))
                        || (instance.equalsIgnoreCase("subject2") && (Math.abs(r.getSubjectId().hashCode()%2)==1))



        ).forEach(r->{
            subjectManager.recover(r);
        });



 /*


        scanResult1.stream().forEach(r -> {
            Subject subject = new Subject(r.getName());
            //user.setName(r.getName());
            subjectManager.recoverSubject(subject);
        });






        scanResult1.stream().forEach(r -> {
            Subject subject = new Subject(r.getName());
            //user.setName(r.getName());
            subjectManager.recoverFollowers(r.getSubjectId(),r.getFollowedBy());
        });*/


    }




}
