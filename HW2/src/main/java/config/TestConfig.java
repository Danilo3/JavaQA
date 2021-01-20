package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:TestConfig.properties")
public interface TestConfig extends Config {

    @Key("yandex_url")
    String yaUrl();

    @Key("yandex_title")
    String getYandexTitle();

    @Key("tele2_url")
    String tele2Url();

    @Key("tele2_field_id")
    String getTele2FieldId();

    @Key("tele2_number_begin")
    String getNumberBegin();

}
