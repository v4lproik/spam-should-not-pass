package net.v4lproik.googlanime.platform.service.api.utils;

import net.v4lproik.googlanime.platform.service.api.entities.AnimeId;
import net.v4lproik.googlanime.platform.service.api.entities.Entry;
import org.modelmapper.ModelMapper;

public class TransformAbstractMapper {

    public AnimeId transformEntryToEntryId(Entry entry) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entry, AnimeId.class);
    }
}
