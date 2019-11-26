package services.post;


import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import persistence.DynamoDBManager;


public class PostApplication extends Application<PostConfiguration> {
    public static void main(String[] args) throws Exception {
        new PostApplication().run(args);
    }

    @Override
    public String getName() {
        return "Mitek Application";
    }

    @Override
    public void initialize(Bootstrap<PostConfiguration> bootstrap) {

    //    IDStatusPollManager.getInstance().start();

        // nothing to do yet
    }

    @Override
    public void run(PostConfiguration configuration,
                    Environment environment) {
        final PostResource resource = new PostResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
   //     DiscoveryLifeCycle myManagedObject = new DiscoveryLifeCycle(Constants.BureauServiceUrl,Constants.BureauServiceType, Constants.BureauServiceHealthUrl);
     //   environment.lifecycle().manage(myManagedObject);



        environment.jersey().register(resource);
       // environment.healthChecks().register("APIHealthCheck", new AppHealthCheck());

    }

}