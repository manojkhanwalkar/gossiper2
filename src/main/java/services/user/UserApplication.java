package services.user;


import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import persistence.DynamoDBManager;


public class UserApplication extends Application<UserConfiguration> {
    public static void main(String[] args) throws Exception {
        new UserApplication().run(args);
    }

    @Override
    public String getName() {
        return "Mitek Application";
    }

    @Override
    public void initialize(Bootstrap<UserConfiguration> bootstrap) {
        // Resolve ${ENV_VAR} placeholders in the YAML config (secrets stay out of the repo).
        // Strict mode: startup fails if a referenced variable is not set.
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(true)));

    //    IDStatusPollManager.getInstance().start();

        // nothing to do yet
    }

    @Override
    public void run(UserConfiguration configuration,
                    Environment environment) {
        final UserResource resource = new UserResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
   //     DiscoveryLifeCycle myManagedObject = new DiscoveryLifeCycle(Constants.BureauServiceUrl,Constants.BureauServiceType, Constants.BureauServiceHealthUrl);
     //   environment.lifecycle().manage(myManagedObject);

        RecoverUserState recoverUserState = new RecoverUserState();
        recoverUserState.recover();



        environment.jersey().register(resource);
       // environment.healthChecks().register("APIHealthCheck", new AppHealthCheck());

    }

}