package persistence;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.*;
import data.Subject;
import data.User;
import manager.SubjectManager;
import manager.UserManager;

import java.util.ArrayList;
import java.util.List;

public class DynamoDBManager {

    private AmazonDynamoDB getHandle()
    {
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

        return ddb;

    }

   final DynamoDBMapper mapper ;


    public DynamoDBManager() {
        final AmazonDynamoDB ddb = getHandle();
         mapper = new DynamoDBMapper(ddb);

    }


    public void createUserTable() {

        List<AttributeDefinition> definitions = new ArrayList<>();
        definitions.add(new AttributeDefinition(
                "UserId", ScalarAttributeType.S));

        KeySchemaElement keySchemaElement = new KeySchemaElement("UserId", KeyType.HASH);

        createTable("User",definitions,keySchemaElement);
    }


    public void createSubjectTable() {

        List<AttributeDefinition> definitions = new ArrayList<>();
        definitions.add(new AttributeDefinition(
                "SubjectId", ScalarAttributeType.S));

        KeySchemaElement keySchemaElement = new KeySchemaElement("SubjectId", KeyType.HASH);

        createTable("Subject",definitions,keySchemaElement);
    }


    public void deleteUserTable()
    {
        deleteTable("User");
    }

    public void deleteSubjectTable()
    {
        deleteTable("Subject");
    }


    public UserRecord getUser(String userId)
    {
        UserRecord userRecord = mapper.load(UserRecord.class, userId);
        return userRecord;
    }

    public SubjectRecord getSubject(String subjectId)
    {
        SubjectRecord subjectRecord = mapper.load(SubjectRecord.class, subjectId);
        return subjectRecord;
    }



    public void putUser(UserRecord userRecord)
    {
        mapper.save(userRecord);

    }

    public void putSubject(SubjectRecord subjectRecord)
    {
        mapper.save(subjectRecord);

    }

    public void removeUser(UserRecord userRecord)
    {
        mapper.delete(userRecord);
    }

    public void removeSubject(SubjectRecord subjectRecord)
    {
        mapper.delete(subjectRecord);
    }




    public void printUsers()
    {
            printAllRecords(UserRecord.class);
    }


    public void printSubjects()
    {
        printAllRecords(SubjectRecord.class);
    }

    public void recover()
    {

        UserManager userManager = UserManager.getInstance();
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
        });


    }

    public void cleanAllRecords(Class<?> clazz)
    {
        final AmazonDynamoDB ddb = getHandle();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        var scanResult = mapper.scan(clazz, scanExpression);

        scanResult.stream().forEach(r-> mapper.delete(r));
    }


    private  void printAllRecords(Class<?> clazz)
    {

        final AmazonDynamoDB ddb = getHandle();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        var scanResult = mapper.scan(clazz, scanExpression);

        scanResult.stream().forEach(r-> System.out.println(r));


    }

    private void deleteTable(String tableName)
    {

        final AmazonDynamoDB ddb = getHandle();


        DeleteTableResult result = ddb.deleteTable(tableName);

        System.out.println(result);

    }


    private void createTable(String tableName , List<AttributeDefinition> definitions,KeySchemaElement keySchemaElement )
    {



       /* definitions.add(new AttributeDefinition(
                "Identity", ScalarAttributeType.S)); */

        CreateTableRequest request = new CreateTableRequest()
                .withAttributeDefinitions(definitions)
                .withKeySchema(keySchemaElement)
                .withProvisionedThroughput(new ProvisionedThroughput(
                        new Long(10), new Long(10)))
                .withTableName(tableName);

        final AmazonDynamoDB ddb = getHandle();




        try {
            CreateTableResult result = ddb.createTable(request);
            System.out.println(result);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }


    public static void main(String[] args) {

        DynamoDBManager manager = new DynamoDBManager();

      //  manager.createSubjectTable();

      //  manager.deleteUserTable();

       // manager.createUserTable();

        manager.printSubjects();

        manager.printUsers();

     //   manager.cleanAllRecords(UserRecord.class);
       // manager.cleanAllRecords(SubjectRecord.class);

    }
}




