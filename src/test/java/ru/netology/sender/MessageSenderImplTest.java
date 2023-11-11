package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;


class MessageSenderImplTest {

    @Test
    void sendWithRu() {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("172."))).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER,"172.163.34.69");

        String expected = "Добро пожаловать";
        String actual = messageSender.send(headers);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void sendWithEng(){
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("96."))).thenReturn(new Location("New York", Country.USA, null,  0));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String > headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.174.16.32");

        String expected = "Welcome";
        String actual = messageSender.send(headers);

        Assertions.assertEquals(expected, actual);

    }
}