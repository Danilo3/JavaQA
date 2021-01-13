package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")
public interface CheckTitleConfig  extends Config {

    @Key("url")
    String url();

    @Key("title_content")
    String getTitle();

}
