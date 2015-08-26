package dtos.conversion.converters;

import dtos.LifecycleComponentDto;
import dtos.PaasageModelDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.DefaultConverter;
import models.LifecycleComponent;
import models.PaasageModel;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ec on 9/02/15.
 */
public class PaasageModelConverter extends DefaultConverter<PaasageModel, PaasageModelDto> {

    public PaasageModelConverter() {
        super(PaasageModel.class, PaasageModelDto.class);
    }

    /**
    * Sets the dto to the paasageModel model.
    *
    * @param paasageModel    the paasageModel model where the dto should be set.
    * @param paasageModelDto the dto to be set.
    * @return the merged paasageModel object.
    */

    protected PaasageModel setDto(PaasageModel paasageModel, PaasageModelDto paasageModelDto) {
        checkNotNull(paasageModelDto);
        checkNotNull(paasageModelDto.name);
        checkNotNull(paasageModelDto.getAction());
        checkNotNull(paasageModelDto.getState());
        checkNotNull(paasageModelDto.subState);
        checkNotNull(paasageModelDto.xmiModelEncoded);
        if (! paasageModelDto.name.equalsIgnoreCase("UNCHANGED") )
            paasageModel.setName(paasageModelDto.name);
        if (0 != paasageModelDto.action.compareTo(PaasageModel.Action.UNCHANGED) )
            paasageModel.setAction(paasageModelDto.action);
        if (0 != paasageModelDto.state.compareTo(PaasageModel.State.UNCHANGED) )
            paasageModel.setState(paasageModelDto.state);
        if (! paasageModelDto.subState.equalsIgnoreCase("UNCHANGED"))
            paasageModel.setSubState(paasageModelDto.subState);
        if (! paasageModelDto.xmiModelEncoded.equalsIgnoreCase("UNCHANGED"))
            paasageModel.setXmiModelEncoded(paasageModelDto.xmiModelEncoded);

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
