package cloud.sync.solutions;

import cloud.CloudCredentialLocationId;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.ImageProblems;
import com.google.inject.Inject;
import models.Cloud;
import models.Image;
import models.service.api.generic.ModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class CreateImageInDatabase implements Solution {

    private final ModelService<Image> imageModelService;
    private final ModelService<Cloud> cloudModelService;

    @Inject public CreateImageInDatabase(ModelService<Image> imageModelService,
        ModelService<Cloud> cloudModelService) {
        this.imageModelService = imageModelService;
        this.cloudModelService = cloudModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof ImageProblems.BaseImageNotInDatabase;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));

        ImageProblems.BaseImageNotInDatabase baseImageNotInDatabase =
            (ImageProblems.BaseImageNotInDatabase) problem;

        CloudCredentialLocationId cloudCredentialLocationId =
            CloudCredentialLocationId.of(baseImageNotInDatabase.getImageInCloudAndLocation().id());

        Cloud cloud = cloudModelService.getByUuid(cloudCredentialLocationId.cloud());
        if (cloud == null) {
            throw new SolutionException();
        }

        Image image = new Image(cloudCredentialLocationId.baseId(),
            baseImageNotInDatabase.getImageInCloudAndLocation().name(), cloud, null);
        imageModelService.save(image);
    }
}
