package ch.keepcalm.web.component.price.service;

import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.Leistungserbringer;
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

        request.setCorrelationId("42342");

        PersonListType personList = new PersonListType();

        Person person = new Person(); // TODO: 08.07.2016  UI
        person.setId("0");
        DateTime birtday = new DateTime(1975, 9, 27, 0, 0, 0, 0); // TODO: 08.07.2016  UI
        person.setGeburtsdatum(CalendarConverter.dateToXMLGregorianCalendar(birtday.toDate()));
        person.setGeschlecht("1");  // TODO: 08.07.2016  UI

        ProduktListType produktList = new ProduktListType();

        // Produkt 1
        Vertragsbaustein produkt1 = new Vertragsbaustein();
        Leistungserbringer leistungserbringer = new Leistungserbringer();
        leistungserbringer.setAvmNetz("AVN_N_1AH_HEL");
        produkt1.setArzt(leistungserbringer);
        produkt1.setDrittesKind("Nein");
        produkt1.setFranchise("COD_Franchise_KVG-O_Erwachsener_1500_HEL");
        produkt1.setProduktId("PRO_P0BEPH_HEL_IG");
        produkt1.setUnfall("COD_ausgeschlossen_HEL");
        produktList.getProdukt().add(produkt1);
        person.setProduktList(produktList);
        personList.getPerson().add(person);

        request.setPersonList(personList);

        // TODO: 08.07.2016  UI
        Versicherungsvertrag versicherungsvertrag = new Versicherungsvertrag();
        versicherungsvertrag.setGemeindeNummer("199");  // TODO: 08.07.2016 UI
        versicherungsvertrag.setPostleitzahl("8307");  // TODO: 08.07.2016 UI
        versicherungsvertrag.setPostleitzahlZusatz("00");  // TODO: 08.07.2016 UI

        DateTime vertragsbeginn = new DateTime(2016, 1, 7, 0, 0, 0, 0); //2016-07-01
        versicherungsvertrag.setVertragsbeginn(CalendarConverter.dateToXMLGregorianCalendar(vertragsbeginn.toDate()));

        request.setVersicherungsvertrag(versicherungsvertrag);

        return request;
    }


    // TODO: 08/07/16 finish BuilderPattern request object.
    public static BerechnePraemieRequest berechnePraemieRequestWithTwoProducts(){
        BerechnePraemieRequest request = new BerechnePraemieRequest();
        request.withCorrelationId("12341");

        return request;
    }
}
