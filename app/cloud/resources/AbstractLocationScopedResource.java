package cloud.resources;

import cloud.DecoratedId;
import de.uniulm.omi.cloudiator.sword.api.domain.Resource;
import de.uniulm.omi.cloudiator.sword.api.util.IdScopedByLocation;
import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

/**
 * Created by daniel on 20.05.15.
 */
public class AbstractLocationScopedResource<T extends Resource>
    extends AbstractCredentialScopedResource<T>
    implements CredentialScoped, LocationScoped, RemoteResource {

    private final String location;
    private final String id;

    public AbstractLocationScopedResource(T resource, String cloud, String credential) {
        super(resource, cloud, credential);
        IdScopedByLocation idScopedByLocation = IdScopeByLocations.from(resource.id());
        this.id = idScopedByLocation.getId();
        this.location = idScopedByLocation.getLocationId();
    }

    @Override public String id() {
        return DecoratedId.of(credential(), cloud(), location, id).colosseumId();
    }

    @Override public String location() {
        return DecoratedId.of(credential(), cloud(), location, id).colosseumLocation();
    }
}
