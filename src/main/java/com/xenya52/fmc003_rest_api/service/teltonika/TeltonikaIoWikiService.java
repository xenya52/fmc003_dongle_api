package com.xenya52.fmc003_rest_api.service.teltonika;

import com.xenya52.fmc003_rest_api.entity.model.teltonika.TeltonikaIoWikiModel;
import com.xenya52.fmc003_rest_api.repository.TeltonikaIoWikiRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;

@Service
public class TeltonikaIoWikiService {

    private static final Logger LOGGER = Logger.getLogger(
        TeltonikaIoWikiService.class.getName()
    );

    private final TeltonikaIoWikiRepository teltonikaIoWikiRepository;

    @Autowired
    public TeltonikaIoWikiService(TeltonikaIoWikiRepository teltonikaIoWikiRepository
    ) {
        this.teltonikaIoWikiRepository = teltonikaIoWikiRepository;
    }

    /**
     * Retrieves a list of IoWiki entries by their IDs.
     * @param ids List of IDs to retrieve.
     * @return List of TeltonikaIoWikiModel containing the IoWiki entries.
     */
    public List<TeltonikaIoWikiModel> getTeltonikaIoWikiModelsById(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return teltonikaIoWikiRepository.findAllById(ids);
    }

    /**
     * Retrieves a list of all IoWiki entries.
     * @return List of TeltonikaIoWikiModel containing all IoWiki entries.
     */
    public List<TeltonikaIoWikiModel> getAllTeltonikaIoWikiModels() {
        return teltonikaIoWikiRepository.findAll();
    }

    /**
     * Saves a list of IoWiki entries.
     * @param teltonikaIoWikiModels List of IoWiki entries to save.
     */
    public void saveTeltonikaIoWikiModels(List<TeltonikaIoWikiModel> teltonikaIoWikiModels) {
        if (teltonikaIoWikiModels == null || teltonikaIoWikiModels.isEmpty()) {
            LOGGER.log(Level.WARNING, "No IoWiki entries to save.");
            return;
        }
        teltonikaIoWikiRepository.saveAll(teltonikaIoWikiModels);
    }

    /**
     * Updates an existing IoWiki entry in the database.
     * @param teltonikaIoWikiModel The IoWiki entry to update.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateTeltonikaIoWikiModel(TeltonikaIoWikiModel teltonikaIoWikiModel) {
        if (teltonikaIoWikiModel == null) {
            LOGGER.log(Level.WARNING, "Update failed: IoWikiModel is null.");
            return false;
        }
        if (!teltonikaIoWikiRepository.existsById(Long.toString(teltonikaIoWikiModel.getId()))) {
            LOGGER.log(Level.WARNING, "Update failed: IoWikiModel with id {0} does not exist.", teltonikaIoWikiModel.getId());
            return false;
        }
        try {
            teltonikaIoWikiRepository.save(teltonikaIoWikiModel);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Update failed: {0}", e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a single IoWiki entry by its ID.
     * @param id The ID of the IoWiki entry to delete.
     * @return true if the entry was deleted successfully, false otherwise.
     */
    public boolean deleteTelonikaIoWikiModel(String id) {
        if (teltonikaIoWikiRepository.existsById(id)) {
            teltonikaIoWikiRepository.deleteById(id);
            return true;
        } else {
            LOGGER.log(
                Level.WARNING,
                "Delete failed: IoWikiModel with id {0} does not exist.",
                id
            );
            return false;
        }
    }

    /**
     * Returns the previous ID (as long) in the sorted list of IoWiki entries, or 0 if not found.
     */
    private Optional<Long> getPreviousTeltonikaIoWikiModelId(String id) {
        List<TeltonikaIoWikiModel> models = teltonikaIoWikiRepository.findAll();
        long currentId = Long.parseLong(id);
        return models.stream()
            .map(TeltonikaIoWikiModel::getId)
            .sorted()
            .takeWhile(i -> i != currentId)
            .reduce((prev, curr) -> curr);
    }

    /**
     * Retrieves the next ID (as long) in the sorted list of IoWiki entries, or empty if not found.
     */
    private Optional<Long> getNextTeltonikaIoWikiModelId(String id) {
        List<TeltonikaIoWikiModel> models = teltonikaIoWikiRepository.findAll();
        long currentId = Long.parseLong(id);
        return models.stream()
                .map(TeltonikaIoWikiModel::getId)
                .sorted()
                .dropWhile(i -> i != currentId)
                .skip(1)
                .findFirst();
    }
}
