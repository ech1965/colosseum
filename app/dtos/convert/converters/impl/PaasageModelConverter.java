package dtos.convert.converters.impl;

import dtos.PaasageModelDto;
import models.PaasageModel;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ec on 9/02/15.
 */
public class PaasageModelConverter extends BaseConverter<PaasageModel, PaasageModelDto>{
    /**
     * Sets the dto to the paasageModel model.
     *
     * @param paasageModel    the paasageModel model where the dto should be set.
     * @param paasageModelDto the dto to be set.
     * @return the merged paasageModel object.
     */
    protected PaasageModel setDto(PaasageModel paasageModel, PaasageModelDto paasageModelDto) {
        paasageModel.setName(paasageModelDto.name);
        return paasageModel;
    }

    @Override
    public PaasageModel toModel(PaasageModelDto paasageModelDto) {
        checkNotNull(paasageModelDto);
        return setDto(new PaasageModel(), paasageModelDto);
    }

    @Override
    public PaasageModel toModel(PaasageModelDto paasageModelDto, PaasageModel model) {
        checkNotNull(paasageModelDto);
        checkNotNull(model);
        return setDto(model, paasageModelDto);
    }

    @Override
    public PaasageModelDto toDto(PaasageModel paasageModel) {
        checkNotNull(paasageModel);
        return new PaasageModelDto(paasageModel.getName());
    }
}
