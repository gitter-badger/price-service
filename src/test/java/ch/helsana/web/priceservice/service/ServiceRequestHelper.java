package ch.helsana.web.priceservice.service;

import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.Leistungserbringer;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.Person;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.PersonListType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.ProduktListType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.Versicherungsvertrag;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.Vertragsbaustein;
import ch.helsana.web.priceservice.converter.CalendarConverter;
import org.joda.time.DateTime;

/**
 * Created by hkesq on 05.07.2016.
 */
public class ServiceRequestHelper {



    public static BerechnePraemieRequest berechnePraemieRequest() {
        BerechnePraemieRequest request = new BerechnePraemieRequest();

        request.setCorrelationId("42342");

        PersonListType personList = new PersonListType();

        Person person = new Person();
        person.setId("0");
        DateTime birtday = new DateTime(1975, 9, 27, 0, 0, 0, 0);
        person.setGeburtsdatum(CalendarConverter.dateToXMLGregorianCalendar(birtday.toDate()));
        person.setGeschlecht("1");

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

        // Produkt 2
        Vertragsbaustein produkt2 = new Vertragsbaustein();
        produkt2.setErgaenzungsmodul("Ja");
        produkt2.setProduktId("PRO_Z2FLEX_HEL_IG");
        produkt2.setUnfall("COD_eingeschlossen_HEL");
        produkt2.setVariante("COD_Z2FLEX_Variante_FLEX2_HEL");
        produktList.getProdukt().add(produkt2);

        person.setProduktList(produktList);
        personList.getPerson().add(person);

        request.setPersonList(personList);

        Versicherungsvertrag versicherungsvertrag = new Versicherungsvertrag();
        versicherungsvertrag.setGemeindeNummer("199");
        versicherungsvertrag.setPostleitzahl("8307");
        versicherungsvertrag.setPostleitzahlZusatz("00");
        DateTime vertragsbeginn = new DateTime(2016, 1, 7, 0, 0, 0, 0); //2016-07-01
        versicherungsvertrag.setVertragsbeginn(CalendarConverter.dateToXMLGregorianCalendar(vertragsbeginn.toDate()));

        request.setVersicherungsvertrag(versicherungsvertrag);

        return request;
    }
}
