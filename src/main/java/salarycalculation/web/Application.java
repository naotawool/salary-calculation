package salarycalculation.web;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import salarycalculation.web.configuration.SalaryCalculationConfiguration;
import salarycalculation.web.providers.RecordNotFoundExceptionMapper;
import salarycalculation.web.resources.EmployeeResource;

/**
 * Dropwizard のエントリポイント。
 *
 * @author naotake
 */
public class Application extends io.dropwizard.Application<SalaryCalculationConfiguration> {

    public static void main(String[] args) throws Exception {
        new Application().run(args);
    }

    @Override
    public String getName() {
        return "salary";
    }

    @Override
    public void initialize(Bootstrap<SalaryCalculationConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(SalaryCalculationConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new RecordNotFoundExceptionMapper());

        environment.jersey().register(new EmployeeResource());
    }
}
