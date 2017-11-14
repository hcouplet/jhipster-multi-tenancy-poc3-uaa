package eu.creativeone.tenancy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages  = "eu.creativeone.tenancy.repository")
@EnableTransactionManagement
public class TenancyConfiguration
{

}
