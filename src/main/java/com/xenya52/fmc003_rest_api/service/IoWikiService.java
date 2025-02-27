package com.xenya52.fmc003_rest_api.service;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.repository.IoWikiRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IoWikiService {

    // Attributes
    @Autowired
    private IoWikiRepository ioWikiRepository;

    // Constructors
    public IoWikiService() {}

    // Methods
    public List<GetResponseDto> getIoWikiListById(List<String> ids) {
        List<GetResponseDto> getResponseDtos = new ArrayList<>();

        for (String id : ids) {
            GetResponseDto getResponseDto = getIoWikiById(id);

            if (getResponseDto == null) {
                // TODO
            } else {
                getResponseDtos.add(getResponseDto);
            }
        }
        return getResponseDtos;
    }

    private GetResponseDto getIoWikiById(String id) {
        IoWikiModel wikiModel = ioWikiRepository.findByWikiId(id).orElse(null);
        try {
            Map<String, String> links = Map.of(
                "prev",
                getPrevId(id),
                "next",
                getNextId(id)
            );
            return wikiModel == null
                ? null
                : new GetResponseDto(wikiModel, links);
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(
                "IllegalArgumentException: The id is not valid. Please provide a valid id."
            );
        } catch (NullPointerException npe) {
            throw new NullPointerException(
                "NullPointerException: The id is not valid. Please provide a valid id."
            );
        }
    }

    public GetResponseDto getIoWikiByName(String name) {
        IoWikiModel wikiModel = ioWikiRepository
            .findByWikiName(name)
            .orElse(null);
        Map<String, String> links = Map.of(
            "prev",
            getPrevId(wikiModel.getWikiId()),
            "next",
            getNextId(wikiModel.getWikiId())
        );
        return wikiModel == null ? null : new GetResponseDto(wikiModel, links);
    }

    public List<GetResponseDto> getIoWikiList() {
        List<IoWikiModel> ioWikiModelList = ioWikiRepository.findAll();
        List<GetResponseDto> ioWikiDtoList = new ArrayList<>();
        try {
            for (IoWikiModel ioWikiModel : ioWikiModelList) {
                // Next and Previous id are unnecessary if I get the whole list. Furthermore it saves time
                // Todo: Idk... is "null" bad practice? In this case?
                ioWikiDtoList.add(new GetResponseDto(ioWikiModel, null));
            }
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(
                "IllegalArgumentException: The id is not valid. Please provide a valid id."
            );
        } catch (NullPointerException npe) {
            throw new NullPointerException(
                "NullPointerException: The id is not valid. Please provide a valid id."
            );
        }
        return ioWikiDtoList;
    }

    public List<IoWikiModel> fetchFromTeltonikaIoWiki() {
        return null;
    }

    public boolean saveIoWikiList(List<IoWikiModel> ioWikiList) {
        try {
            for (IoWikiModel ioWikiModel : ioWikiList) {
                if (!saveIoWiki(ioWikiModel)) {
                    return false;
                }
            }
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(
                "IllegalArgumentException: The id is not valid. Please provide a valid id."
            );
        } catch (NullPointerException npe) {
            throw new NullPointerException(
                "NullPointerException: The id is not valid. Please provide a valid id."
            );
        }
        return true;
    }

    // Todo sort the list by id
    private boolean saveIoWiki(IoWikiModel ioWikiModel) {
        try {
            ioWikiRepository.save(ioWikiModel);
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(
                "IllegalArgumentException: The given IoDongleModel is not valid."
            );
        } catch (OptimisticLockingFailureException npe) {
            throw new OptimisticLockingFailureException(
                "OptimisticLockingFailureException: The given IoDongleModel has already changed his state in the database."
            );
        }
        return true;
    }

    private String getPrevId(String id) {
        List<IoWikiModel> ioWikiModelList = ioWikiRepository.findAll();
        try {
            ioWikiModelList.sort(
                Comparator.comparing(IoWikiModel::getWikiIdAsInt)
            );
            int index = ioWikiModelList.indexOf(
                ioWikiModelList
                    .stream()
                    .filter(wikiModel -> wikiModel.getWikiId().equals(id))
                    .findFirst()
                    .orElse(null)
            );

            if (index > 0) {
                return ioWikiModelList.get(index - 1).getWikiId();
            } else {
                return "";
            }
        } catch (NullPointerException npe) {
            throw new NullPointerException(
                "NullPointerException: The id is not valid. Please provide a valid id."
            );
        }
    }

    private String getNextId(String id) {
        List<IoWikiModel> ioWikiModelList = ioWikiRepository.findAll();
        try {
            ioWikiModelList.sort(
                Comparator.comparing(IoWikiModel::getWikiIdAsInt)
            );
            int index = ioWikiModelList.indexOf(
                ioWikiModelList
                    .stream()
                    .filter(wikiModel -> wikiModel.getWikiId().equals(id))
                    .findFirst()
                    .orElse(null)
            );

            if (index >= 0 && index < ioWikiModelList.size() - 1) {
                return ioWikiModelList.get(index + 1).getWikiId();
            } else {
                return "";
            }
        } catch (NullPointerException npe) {
            throw new NullPointerException(
                "NullPointerException: The id is not valid. Please provide a valid id."
            );
        }
    }
}
