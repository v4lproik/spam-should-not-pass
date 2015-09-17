package net.v4lproik.googlanime.service.api.utils;

import net.v4lproik.googlanime.service.api.entities.AnimeIdModel;
import net.v4lproik.googlanime.service.api.entities.Entry;
import org.modelmapper.ModelMapper;

public class TransformAbstractMapper {

    public AnimeIdModel transformEntryToEntryId(Entry entry) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entry, AnimeIdModel.class);
    }
}
