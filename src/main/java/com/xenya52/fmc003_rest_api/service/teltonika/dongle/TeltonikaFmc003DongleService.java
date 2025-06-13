package com.xenya52.fmc003_rest_api.service.teltonika.dongle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import com.xenya52.fmc003_rest_api.entity.builder.Director;
import com.xenya52.fmc003_rest_api.entity.builder.TeltonikaDongleDataBuilder;
import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.teltonika.dongle.TeltonikaFmc003DongleModel;
import com.xenya52.fmc003_rest_api.repository.IoDongleRepository;

/**
 * Service class for managing IoDongle entities.
 */
@Component
public class TeltonikaFmc003DongleService {

    private static final Logger LOGGER = Logger.getLogger(
        TeltonikaFmc003DongleService.class.getName()
    );

    // Attributes
    @Autowired
    private IoDongleRepository ioDongleRepository;

    @Autowired
    private Director director;

    // Constructors
    public TeltonikaFmc003DongleService() {}

    // Methods

    /**
     * Generates a specified number of IoDongleModel entities with 'None' values and saves them.
     * @param dongleAmount The number of dongles to generate.
     * @param wikiAmount The number of wiki entries to associate with each dongle.
     * @return true if all entities are saved successfully.
     */
    public boolean generateDonglesWithNoneValuesAndSaveIntoDB(
        int dongleAmount,
        int wikiAmount
    ) {
        List<TeltonikaFmc003DongleModel> dongleModels = new ArrayList<>();

        for (int i = 0; i < dongleAmount; i++) {
            TeltonikaDongleDataBuilder builder =
                new TeltonikaDongleDataBuilder();

            director.constructRandomDongleWithSpecificAmountIoWikiValues(
                builder,
                wikiAmount
            );
            dongleModels.add(builder.getResult());
        }
        return saveIoDongleList(dongleModels);
    }

    /**
     * Saves a list of IoDongleModel entities.
     * @param teltonikaFmc003DongleModels List of IoDongleModel entities to be saved.
     * @return true if all entities are saved successfully.
     */
    public boolean saveIoDongleList(List<TeltonikaFmc003DongleModel> teltonikaFmc003DongleModels) {
        for (TeltonikaFmc003DongleModel teltonikaFmc003DongleModel : teltonikaFmc003DongleModels) {
            saveIoDongle(teltonikaFmc003DongleModel);
        }
        return true;
    }

    /**
     * Retrieves a list of all IoDongle entities.
     * @return List of GetResponseDto containing all IoDongle entities.
     */
    public List<GetResponseDto> getIoDongleList() {
        List<TeltonikaFmc003DongleModel> teltonikaFmc003DongleModelList = ioDongleRepository.findAll();
        List<GetResponseDto> ioDongleDtoList = new ArrayList<>();

        for (TeltonikaFmc003DongleModel teltonikaFmc003DongleModel : teltonikaFmc003DongleModelList) {
            // Next and Previous id are unnecessary if I get the whole list. Furthermore it saves time
            ioDongleDtoList.add(new GetResponseDto(teltonikaFmc003DongleModel, null));
        }
        return ioDongleDtoList;
    }

    /**
     * Retrieves a list of IoDongle entities by specific IDs.
     * @param ids List of IDs to retrieve IoDongle entities.
     * @return List of GetResponseDto containing the IoDongle entities with the specified IDs.
     */
    public List<GetResponseDto> getIoDongleListBySpecificId(List<String> ids) {
        List<GetResponseDto> responseDtos = new ArrayList<>();

        for (String id : ids) {
            GetResponseDto getResponseDto = getIoDongleById(id);

            if (getResponseDto == null) {
                LOGGER.log(Level.WARNING, "IoDongle with id {0} not found", id);
            } else {
                responseDtos.add(getResponseDto);
            }
        }
        return responseDtos;
    }

    /**
     * Retrieves an IoDongle entity by its ID.
     * @param id ID of the IoDongle entity to retrieve.
     * @return GetResponseDto containing the IoDongle entity and its links, or null if not found.
     */
    private GetResponseDto getIoDongleById(String id) {
        try {
            TeltonikaFmc003DongleModel dongleModel = ioDongleRepository
                .findByDeviceId(id)
                .orElse(null);
            Map<String, String> links = Map.of(
                "prev",
                getPrevId(id),
                "next",
                getNextId(id)
            );
            return dongleModel == null
                ? null
                : new GetResponseDto(dongleModel, links);
        } catch (IllegalArgumentException iae) {
            LOGGER.log(
                Level.SEVERE,
                "Invalid id provided: {0}",
                iae.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                iae
            );
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException for id: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
    }

    /**
     * Saves an IoDongle entity.
     * @param teltonikaFmc003DongleModel IoDongle entity to be saved.
     * @return The saved IoDongle entity.
     */
    public TeltonikaFmc003DongleModel saveIoDongle(TeltonikaFmc003DongleModel teltonikaFmc003DongleModel) {
        try {
            return ioDongleRepository.save(teltonikaFmc003DongleModel);
        } catch (IllegalArgumentException iae) {
            LOGGER.log(
                Level.SEVERE,
                "Invalid IoDongleModel provided: {0}",
                iae.getMessage()
            );
            throw new IllegalArgumentException(
                "The given IoDongleModel is not valid.",
                iae
            );
        } catch (OptimisticLockingFailureException olfe) {
            LOGGER.log(
                Level.SEVERE,
                "Optimistic locking failure for IoDongleModel: {0}",
                olfe.getMessage()
            );
            throw new OptimisticLockingFailureException(
                "The given IoDongleModel has already changed its state in the database.",
                olfe
            );
        }
    }

    /**
     * Updates an existing IoDongle entity.
     * @param teltonikaFmc003DongleModel IoDongle entity to be updated.
     * @return The updated IoDongle entity, or null if the entity does not exist.
     */
    public TeltonikaFmc003DongleModel updateIoDongle(TeltonikaFmc003DongleModel teltonikaFmc003DongleModel) {
        if (!ioDongleRepository.existsById(teltonikaFmc003DongleModel.getDeviceId())) {
            return null;
        }
        return saveIoDongle(teltonikaFmc003DongleModel);
    }

    /**
     * Deletes an IoDongle entity by its ID.
     * @param id ID of the IoDongle entity to delete.
     * @return true if the entity is deleted successfully, false if the entity does not exist.
     */
    public boolean deleteIoDongle(String id) {
        if (!ioDongleRepository.existsById(id)) {
            return false;
        }
        ioDongleRepository.deleteById(id);
        return true;
    }

    /**
     * Retrieves the previous ID in the list of IoDongle entities.
     * @param id Current ID to find the previous ID for.
     * @return The previous ID in the list.
     */
    private String getPrevId(String id) {
        List<TeltonikaFmc003DongleModel> teltonikaFmc003DongleModelList = ioDongleRepository.findAll();
        try {
            teltonikaFmc003DongleModelList.sort(
                Comparator.comparing(TeltonikaFmc003DongleModel::getDeviceId)
            );
            int index = 0;
            for (int i = 0; i < teltonikaFmc003DongleModelList.size(); i++) {
                if (teltonikaFmc003DongleModelList.get(i).getDeviceId().equals(id)) {
                    index = i;
                    break;
                }
            }
            if (index == 0) {
                return teltonikaFmc003DongleModelList
                    .get(teltonikaFmc003DongleModelList.size() - 1)
                    .getDeviceId();
            } else {
                return teltonikaFmc003DongleModelList.get(index - 1).getDeviceId();
            }
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException while getting previous id for: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
    }

    /**
     * Retrieves the next ID in the list of IoDongle entities.
     * @param id Current ID to find the next ID for.
     * @return The next ID in the list.
     */
    private String getNextId(String id) {
        List<TeltonikaFmc003DongleModel> teltonikaFmc003DongleModelList = ioDongleRepository.findAll();
        try {
            teltonikaFmc003DongleModelList.sort(
                Comparator.comparing(TeltonikaFmc003DongleModel::getDeviceId)
            );
            int index = 0;
            for (int i = 0; i < teltonikaFmc003DongleModelList.size(); i++) {
                if (teltonikaFmc003DongleModelList.get(i).getDeviceId().equals(id)) {
                    index = i;
                    break;
                }
            }
            if (index == teltonikaFmc003DongleModelList.size() - 1) {
                return teltonikaFmc003DongleModelList.get(0).getDeviceId();
            } else {
                return teltonikaFmc003DongleModelList.get(index + 1).getDeviceId();
            }
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException while getting next id for: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
    }
}
