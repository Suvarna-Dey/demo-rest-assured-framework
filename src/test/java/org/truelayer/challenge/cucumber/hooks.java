package org.truelayer.challenge.cucumber;

import io.cucumber.java.Scenario;
import org.junit.Before;

public class hooks {
    String scenario_name;
    @Before
    public void before(Scenario scenario)
    {
        scenario_name = scenario.getName();
    }
}
