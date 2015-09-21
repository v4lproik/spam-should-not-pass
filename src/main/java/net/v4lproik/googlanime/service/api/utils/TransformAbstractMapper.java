package net.v4lproik.googlanime.service.api.utils;

import net.v4lproik.googlanime.service.api.entities.AnimeId;
import net.v4lproik.googlanime.service.api.entities.Entry;
import org.modelmapper.ModelMapper;

public class TransformAbstractMapper {

    public AnimeId transformEntryToEntryId(Entry entry) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entry, AnimeId.class);
    }
}
