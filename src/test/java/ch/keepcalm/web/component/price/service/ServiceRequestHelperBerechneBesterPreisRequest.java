package ch.keepcalm.web.component.price.service;

import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.Person;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.ProduktListType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.Versicherungsvertrag;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.Vertragsbaustein;
import ch.keepcalm.web.component.price.converter.CalendarConverter;
import ch.keepcalm.web.component.price.converter.JsonConverter;
import org.joda.time.DateTime;


/**
 * Created by Marcel Widmer  on 25/12/15.
 */
public class ServiceRequestHelperBerechneBesterPreisRequest {


    private static Person person;

    public static BerechneBesterPreisRequest berechneBesterPreisRequest(String testRequestJsonFileName) {
        BerechneBesterPreisRequest request = new JsonConverter<BerechneBesterPreisRequest>().convert("/sampleRequest/" + testRequestJsonFileName, BerechneBesterPreisRequest.class);
        return request;
    }


    public static BerechneBesterPreisRequest berechneBesterPreisRequest() {
        BerechneBesterPreisRequest request = new BerechneBesterPreisRequest();
        request.withAlleMarken(false);
        request.withPerson(
                new Person()
                        .withGeburtsdatum(CalendarConverter.dateToXMLGregorianCalendar(new DateTime(1975, 9, 27, 0, 0, 0, 0).toDate()))
                        .withGeschlecht("1")
                        .withId("1")
                        .withProduktList(new ProduktListType()
                                .withProdukt(new Vertragsbaustein()
                                        .withFranchise("COD_Franchise_KVG-O_Erwachsener_300_HEL")
                                        .withProduktId("PRO_x0BAS__HEL_IG")
                                        .withUnfall("COD_ausgeschlossen_HEL"))))
                .withVertrag(new Versicherungsvertrag()
                        .withGemeindeNummer("2773")
                        .withMarke("H")
                        .withMarke("P")
                        .withMarke("S")
                        .withMarke("A")
                        .withPostleitzahl("4153")
                        .withPostleitzahlZusatz("00")
                        .withVertragsbeginn(CalendarConverter.dateToXMLGregorianCalendar(new DateTime(2016, 1, 7, 0, 0, 0, 0).toDate())));

        return request;

    }


}
