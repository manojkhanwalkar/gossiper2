package services.proxy;


import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import persistence.DynamoDBManager;
import util.ConnectionManager;


public class GossiperProxyApplication extends Application<GossiperProxyConfiguration> {
    public static void main(String[] args) throws Exception {
        new GossiperProxyApplication().run(args);
    }

    @Override
    public String getName() {
        return "Mitek Application";
    }

    @Override
    public void initialize(Bootstrap<GossiperProxyConfiguration> bootstrap) {

    //    IDStatusPollManager.getInstance().start();

        // nothing to do yet
    }

    @Override
    public void run(GossiperProxyConfiguration configuration,
                    Environment environment) {
        final GossiperProxyResource resource = new GossiperProxyResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
   //     DiscoveryLifeCycle myManagedObject = new DiscoveryLifeCycle(Constants.BureauServiceUrl,Constants.BureauServiceType, Constants.BureauServiceHealthUrl);
     //   environment.lifecycle().manage(myManagedObject);


        ConnectionManager connectionManager = ConnectionManager.getInstance();
        connectionManager.init();



        environment.jersey().register(resource);
       // environment.healthChecks().register("APIHealthCheck", new AppHealthCheck());

    }

}