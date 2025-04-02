package com.xenya52.fmc003_rest_api.entity.builder;

import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import java.util.List;

public interface IBuilder {
    public void reset();

    public void setId(String id);

    public void setName(String name);

    public void setIoWikiList(List<IoWikiModel> ioWikis);
}
