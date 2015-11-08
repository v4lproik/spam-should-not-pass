package net.v4lproik.spamshouldnotpass.platform.dao.api;

import net.v4lproik.spamshouldnotpass.platform.models.SchemeType;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.Scheme;

import java.util.List;
import java.util.UUID;

public interface SchemeDao {
    Scheme findById(UUID id);

    UUID save(Scheme scheme);

    void delete(UUID id);

    List<Scheme> list();
    List<Scheme> listByUserId(UUID userId);
    Scheme listByUserIdAndType(UUID userId, SchemeType type);
}
