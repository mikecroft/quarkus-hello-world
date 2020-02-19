package org.acme.rest.json;

import org.microshed.testing.SharedContainerConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

/*  
*   MicroShed example from Andy Guibert:
*   https://github.com/MicroShed/microshed-testing/tree/master/sample-apps/quarkus-app  
*/
public class QuarkusTestEnvironment implements SharedContainerConfiguration {
    
    // No need for an ApplicationContainer because we let the 
    // quarkus-maven-plugin handle starting Quarkus
    
    @Container
    public static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:10.5")
            .withReuse(true)
            .withUsername("quarkus")
            .withPassword("quarkus")
            .withDatabaseName("quarkus");

}