package dtos.convert.converters.impl;

import dtos.CredentialDto;
import dtos.convert.converters.api.ModelDtoConverter;
import models.Credential;

import static com.google.inject.internal.util.$Preconditions.checkNotNull;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class CredentialConverter implements ModelDtoConverter<Credential, CredentialDto> {

    /**
     * Sets the dto to the hardware model.
     *
     * @param credential    the credential model where the dto should be set.
     * @param credentialDto the dto to be set.
     * @return the merged credential object.
     */
    protected Credential setDto(Credential credential, CredentialDto credentialDto){
        credential.setUser(credentialDto.user);
        credential.setSecret(credentialDto.secret);

        return credential;
    }

    @Override
    public Credential toModel(CredentialDto dto) {
        checkNotNull(dto);
        return setDto(new Credential(), dto);
    }

    @Override
    public Credential toModel(CredentialDto dto, Credential model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public CredentialDto toDto(Credential model) {
        checkNotNull(model);
        return new CredentialDto(model.getUser(), model.getSecret());
    }
}
