package dtos.convert.api;

import com.google.inject.ImplementedBy;
import dtos.convert.impl.DefaultModelDtoConversionService;
import dtos.generic.Dto;
import models.generic.Model;

import java.util.List;

/**
 * Interface for the Model to DTO Conversion service.
 * <p>
 * Defines methods for converting model object to dto objects
 * and vice versa.
 *
 * If no converter is registered, an exception is thrown.
 */
@ImplementedBy(DefaultModelDtoConversionService.class)
public interface ModelDtoConversionService {

    /**
     * Converts the given dto to the expected model class.
     *
     * @param dto        the dto which will be converted.
     * @param modelClass the target model class.
     * @param <T>        type of the model.
     * @param <S>        type of the dto.
     * @return the resulting model of the conversion.
     */
    public <T extends Model, S extends Dto> T toModel(S dto, Class<T> modelClass);

    /**
     * Converts the given model to the expected dto class.
     *
     * @param model    the model which will be converted.
     * @param dtoClass the target dto class.
     * @param <T>      type of the model.
     * @param <S>      type of the dto.
     * @return the resulting dto of the conversion.
     */
    public <T extends Model, S extends Dto> S toDto(T model, Class<S> dtoClass);

    /**
     * Converts the given dto to the model, by using the given model as
     * template. Only sets the fields provided by the dto.
     *
     * @param dto        the dto which should be merged into the model.
     * @param model      the model in which the dto is merged.
     * @param modelClass the expected model class.
     * @param <T>        type of the model.
     * @param <S>        type of the dto.
     * @return the resulting model object.
     */
    public <T extends Model, S extends Dto> T toModel(S dto, T model, Class<T> modelClass);

    /**
     * Helper for converting a list of dtos to a list of models.
     *
     * @param dtos       a list of dtos.
     * @param modelClass the expected model class.
     * @param <T>        type of the model.
     * @param <S>        type of the dto.
     * @return a list of resulting model objects.
     */
    public <T extends Model, S extends Dto> List<T> toModels(List<S> dtos, Class<T> modelClass);

    /**
     * Helper for converting a list of models to a list of dtos.
     *
     * @param models   a list of models.
     * @param dtoClass the expected dto class.
     * @param <T>      type of the model.
     * @param <S>      type of the dto.
     * @return a list of resulting dto objects.
     */
    public <T extends Model, S extends Dto> List<S> toDtos(List<T> models, Class<S> dtoClass);
}