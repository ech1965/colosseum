package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullValidator;
import models.Application;
import models.service.impl.generic.BaseModelService;

/**
 * Created by daniel on 11.05.15.
 */
public class ApplicationInstanceDto extends ValidatableDto {

    protected Long application;

    public ApplicationInstanceDto() {
    }

    @Override public void validation() {
        validator(Long.class).validate(this.application).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.applicationService.get()));
    }

    public ApplicationInstanceDto(Long application) {
        this.application = application;
    }

    public Long getApplication() {
        return application;
    }

    public void setApplication(Long application) {
        this.application = application;
    }

    public static class References {
        @Inject public static Provider<BaseModelService<Application>> applicationService;
    }

}
