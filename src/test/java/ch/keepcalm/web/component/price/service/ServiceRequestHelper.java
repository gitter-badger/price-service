package ch.keepcalm.web.component.price.service;

import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.Person;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.PersonListType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.ProduktListType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.Versicherungsvertrag;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.Vertragsbaustein;
import ch.keepcalm.web.component.price.converter.CalendarConverter;
import ch.keepcalm.web.component.price.converter.JsonConverter;
import org.joda.time.DateTime;

/**
 * Created by hkesq on 21.07.2016.
 */
public class ServiceRequestHelper {

    /**
     *
     * @param testRequestJsonFileName
     * @return
     */
    public static BerechnePraemieRequest berechnePraemieRequest(String testRequestJsonFileName) {
        BerechnePraemieRequest request = new JsonConverter<BerechnePraemieRequest>().convert("/sampleRequest/" + testRequestJsonFileName, BerechnePraemieRequest.class);
        return request;
    }
    /**
     *
     * @return BerechnePraemieRequest object.
     */
    public static BerechnePraemieRequest berechnePraemieRequest() {

        BerechnePraemieRequest request = new BerechnePraemieRequest();
        request.setPersonList(new PersonListType()
                .withPerson(new Person()
                        .withProduktList(new ProduktListType()
                                .withProdukt(new Vertragsbaustein()
                                        .withFranchise("COD_Franchise_KVG-O_Erwachsener_1500_HEL")
                                        .withProduktId("PRO_P0BEPH_HEL_IG")
                                        .withDrittesKind("Nein")
                                        .withUnfall("COD_ausgeschlossen_HEL")))
                        .withGeburtsdatum(CalendarConverter.dateToXMLGregorianCalendar(new DateTime(1975, 9, 27, 0, 0, 0, 0).toDate()))
                        .withGeschlecht("1")
                        .withId("1")));

        request.setCorrelationId("32424");
        request.setVersicherungsvertrag(new Versicherungsvertrag()
                .withGemeindeNummer("199")
                .withPostleitzahl("8307")
                .withPostleitzahlZusatz("00")
                .withVertragsbeginn(CalendarConverter.dateToXMLGregorianCalendar(new DateTime(2016, 1, 7, 0, 0, 0, 0).toDate())));

        return request;

    }



    public static BerechneBesterPreisRequest berechneBesterPreisRequest() {
        BerechneBesterPreisRequest request = new BerechneBesterPreisRequest();
        request.withAlleMarken(false);
        request.withPerson(
                new ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.Person()
                        .withGeburtsdatum(CalendarConverter.dateToXMLGregorianCalendar(new DateTime(1975, 9, 27, 0, 0, 0, 0).toDate()))
                        .withGeschlecht("1")
                        .withId("1")
                        .withProduktList(new ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.ProduktListType()
                                .withProdukt(new ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.Vertragsbaustein()
                                        .withFranchise("COD_Franchise_KVG-O_Erwachsener_300_HEL")
                                        .withProduktId("PRO_x0BAS__HEL_IG")
                                        .withUnfall("COD_ausgeschlossen_HEL"))))
                .withVertrag(new ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.Versicherungsvertrag()
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
