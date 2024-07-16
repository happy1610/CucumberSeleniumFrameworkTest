package awesomecucumber.utils;

import awesomecucumber.constants.EnvType;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        String env = System.getProperty("env", String.valueOf(EnvType.TEST));
        switch (EnvType.valueOf(env)) {
            case DEV -> properties = PropertyUtils.propertyLoader("src/test/resources/dev_env_config.properties");
            case TEST -> properties = PropertyUtils.propertyLoader("src/test/resources/test_env_config.properties");
            default -> throw new IllegalStateException("Invalid environment" + env);
        }
    }

    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getBaseUrl(){
        String prop = properties.getProperty("baseUrl");
        if(prop != null) return prop;
        else throw new RuntimeException("property baseUrl is not specified in the stage_config.properties file");
    }

    public String getReportConfigPath(){
        String reportConfigPath = properties.getProperty("extendReport");
        if(reportConfigPath!= null) return reportConfigPath;
        else throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
    }
}
