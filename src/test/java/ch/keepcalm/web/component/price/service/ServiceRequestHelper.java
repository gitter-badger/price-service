package ch.keepcalm.web.component.price.service;

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
 * Created by Marcel Widmer  on 25/12/15.
 */
public class ServiceRequestHelper {


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


    // TODO: 08/07/16 finish BuilderPattern request object.
    public static BerechnePraemieRequest berechnePraemieRequestWithTwoProducts(){
        BerechnePraemieRequest request = new BerechnePraemieRequest();
        request.withCorrelationId("12341");

        return request;
    }
}
