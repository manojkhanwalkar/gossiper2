package services.subject;


import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import persistence.DynamoDBManager;


public class SubjectApplication extends Application<SubjectConfiguration> {
    public static void main(String[] args) throws Exception {
        new SubjectApplication().run(args);
    }

    @Override
    public String getName() {
        return "Mitek Application";
    }

    @Override
    public void initialize(Bootstrap<SubjectConfiguration> bootstrap) {

    //    IDStatusPollManager.getInstance().start();

        // nothing to do yet
    }

    @Override
    public void run(SubjectConfiguration configuration,
                    Environment environment) {
        final SubjectResource resource = new SubjectResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
   //     DiscoveryLifeCycle myManagedObject = new DiscoveryLifeCycle(Constants.BureauServiceUrl,Constants.BureauServiceType, Constants.BureauServiceHealthUrl);
     //   environment.lifecycle().manage(myManagedObject);


        DynamoDBManager dynamoDBManager = new DynamoDBManager();

        dynamoDBManager.recover();


        environment.jersey().register(resource);
       // environment.healthChecks().register("APIHealthCheck", new AppHealthCheck());

    }

}