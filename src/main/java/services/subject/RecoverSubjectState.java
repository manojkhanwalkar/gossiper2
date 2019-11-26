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

public class RecoverSubjectState {

    DynamoDBManager dynamoDBManager = new DynamoDBManager();


    public void recover()
    {

 /*       UserManager userManager = UserManager.getInstance();
        SubjectManager subjectManager = SubjectManager.getInstance();

        final AmazonDynamoDB ddb = getHandle();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);



        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        var scanResult = mapper.scan(UserRecord.class, scanExpression);

        scanResult.stream().forEach(r -> {
            User user = new User(r.getName());
            //user.setName(r.getName());
            userManager.recoverUser(user);
        });



        scanExpression = new DynamoDBScanExpression();
        var scanResult1 = mapper.scan(SubjectRecord.class, scanExpression);

        scanResult1.stream().forEach(r -> {
            Subject subject = new Subject(r.getName());
            //user.setName(r.getName());
            subjectManager.recoverSubject(subject);
        });




        scanResult.stream().forEach(r->{


            userManager.recoverFollowersAndFollows(r.getUserId(),r.getFollowedBy(),r.getFollows(),r.getFollowsSubject());});

        //TODO - recover user -> subject DAG

        scanResult1.stream().forEach(r -> {
            Subject subject = new Subject(r.getName());
            //user.setName(r.getName());
            subjectManager.recoverFollowers(r.getSubjectId(),r.getFollowedBy());
        });*/


    }




}
