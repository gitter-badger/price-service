package ch.helsana.web.component.service.price.service;

import ch.helsana.web.component.service.price.exception.BusinessException;
import ch.helsana.web.component.service.price.exception.SystemException;
import ch.sbi.services.system.productengine.tarif.v2.BerechneMarkenPreiseBusinessFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.BerechneMarkenPreiseSystemFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.FiltereProdukteBusinessFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.FiltereProdukteSystemFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.TarifPortType;
import ch.sbi.services.system.productengine.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.sbi.services.system.productengine.tarif.v2.berechnebesterpreisresponse.BerechneBesterPreisResponse;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.sbi.services.system.productengine.tarif.v2.filtereprodukterequest.FiltereProdukteRequest;
import ch.sbi.services.system.productengine.tarif.v2.filtereprodukteresponse.FiltereProdukteResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hkesq on 23.11.2015.
 */
// tag::PriceService[]
@Service
public class PriceService implements TarifPortType {
// end::PriceService[]

    // tag::portType[]
    @Resource
    TarifPortType tarifPortType;
    // end::portType[]

    // tag::implementPortTypeMethod[]
    @Override
    public BerechneBesterPreisResponse berechneBesterPreis(BerechneBesterPreisRequest parameters) throws BusinessException, SystemException {
        BerechneBesterPreisResponse response = null;
        try {
            response = tarifPortType.berechneBesterPreis(parameters);
        } catch (BerechneMarkenPreiseBusinessFaultMessage e) {
            throw new BusinessException(e.getMessage());
        }catch (BerechneMarkenPreiseSystemFaultMessage e2) {
            throw new SystemException(e2.getMessage());
        }
        return response;
    }

    @Override
    public BerechnePraemieResponse berechnePraemie(BerechnePraemieRequest parameters) throws BusinessException, SystemException {
        BerechnePraemieResponse response = null;
       try {
            response = tarifPortType.berechnePraemie(parameters);
        } catch (BerechnePraemieBusinessFaultMessage e) {
           throw new BusinessException(e.getMessage());
        } catch (BerechnePraemieSystemFaultMessage e2) {
           throw new SystemException(e2.getMessage());
        }
        return response;
    }

    @Override
    public FiltereProdukteResponse filtereProdukte(FiltereProdukteRequest parameters) throws BusinessException, SystemException {
        FiltereProdukteResponse response = null;
        try {
            response = tarifPortType.filtereProdukte(parameters);
        } catch (FiltereProdukteBusinessFaultMessage e) {
            throw new BusinessException(e.getMessage());
        } catch (FiltereProdukteSystemFaultMessage e2) {
            throw new SystemException(e2.getMessage());
        }
        return response;

    }
    // end::implementPortTypeMethod[]

    // Used For Mockito
    void setTarifPortType(TarifPortType tarifPortType) {
        this.tarifPortType = tarifPortType;
    }
}
