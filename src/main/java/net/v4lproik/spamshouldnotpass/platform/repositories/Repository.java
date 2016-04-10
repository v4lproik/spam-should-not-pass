package net.v4lproik.spamshouldnotpass.platform.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class Repository <MODEL extends Object>{
    public abstract Optional<MODEL> findById(UUID id);
    public abstract List<MODEL> list();
    public abstract Optional<UUID> save(MODEL model);
    public abstract void update(MODEL model);
    public abstract void delete(UUID id);
}
