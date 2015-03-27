package dtos.convert.converters.impl;

import dtos.PaasageModelDto;
import dtos.convert.impl.BaseConverter;
import models.PaasageModel;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ec on 9/02/15.
 */
public class PaasageModelConverter extends BaseConverter<PaasageModel, PaasageModelDto> {
    /**
     * Sets the dto to the paasageModel model.
     *
     * @param paasageModel    the paasageModel model where the dto should be set.
     * @param paasageModelDto the dto to be set.
     * @return the merged paasageModel object.
     */
    protected PaasageModel setDto(PaasageModel paasageModel, PaasageModelDto paasageModelDto) {
        checkNotNull(paasageModelDto);
        checkNotNull(paasageModelDto.getName());
        checkNotNull(paasageModelDto.getAction());
        checkNotNull(paasageModelDto.getState());
        checkNotNull(paasageModelDto.subState);
        checkNotNull(paasageModelDto.xmiModelEncoded);

        if (! paasageModelDto.getName().equalsIgnoreCase("UNCHANGED") )     paasageModel.setName(paasageModelDto.getName());
        if (! paasageModelDto.action.equalsIgnoreCase(PaasageModel.Action.UNCHANGED.toString()))   paasageModel.setAction(paasageModelDto.action);
        if (! paasageModelDto.state.equalsIgnoreCase( PaasageModel.State.UNCHANGED.toString() ) )    paasageModel.setState(paasageModelDto.state);
        if (! paasageModelDto.subState.equalsIgnoreCase("UNCHANGED")) paasageModel.setSubState(paasageModelDto.subState);
        if (! paasageModelDto.xmiModelEncoded.equalsIgnoreCase("UNCHANGED")) paasageModel.setXmiModelEncoded(paasageModelDto.xmiModelEncoded);

        return paasageModel;
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
        return new PaasageModelDto(paasageModel.getName(), paasageModel.getState(), paasageModel.getSubState(), paasageModel.getAction(),paasageModel.getXmiModelEncoded());
    }
}
