package services.user;

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

public class RecoverUserState {

    DynamoDBManager dynamoDBManager = new DynamoDBManager();

    String instance = System.getProperty("instance");


    public void recover()
    {

        UserManager userManager = UserManager.getInstance();

        final AmazonDynamoDB ddb = getHandle();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);



        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        var scanResult = mapper.scan(UserRecord.class, scanExpression);

        scanResult.stream().filter(r->
            (instance.equalsIgnoreCase("user1") && (Math.abs(r.getUserId().hashCode()%2)==0))
                    || (instance.equalsIgnoreCase("user2") && (Math.abs(r.getUserId().hashCode()%2)==1))



        ).forEach(r->{
            userManager.recover(r);
        });




    }




}
