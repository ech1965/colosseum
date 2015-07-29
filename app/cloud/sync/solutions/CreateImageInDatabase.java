package cloud.sync.solutions;

import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.ImageProblems;
import com.google.inject.Inject;
import models.Cloud;
import models.Image;
import models.OperatingSystem;
import models.service.ModelService;
import models.service.OperatingSystemService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class CreateImageInDatabase implements Solution {

    private final ModelService<Image> imageModelService;
    private final ModelService<Cloud> cloudModelService;
    private final OperatingSystemService operatingSystemService;

    @Inject public CreateImageInDatabase(ModelService<Image> imageModelService,
        ModelService<Cloud> cloudModelService, OperatingSystemService operatingSystemService) {
        this.imageModelService = imageModelService;
        this.cloudModelService = cloudModelService;
        this.operatingSystemService = operatingSystemService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof ImageProblems.ImageNotInDatabase;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));

        ImageProblems.ImageNotInDatabase imageNotInDatabase =
            (ImageProblems.ImageNotInDatabase) problem;

        Cloud cloud = cloudModelService.getByUuid(imageNotInDatabase.getImageInLocation().cloud());
        if (cloud == null) {
            throw new SolutionException();
        }

        OperatingSystem operatingSystem =
            operatingSystemService.findByImageName(imageNotInDatabase.getImageInLocation().name());

        Image image = new Image(imageNotInDatabase.getImageInLocation().id(),
            imageNotInDatabase.getImageInLocation().name(), cloud, operatingSystem);
        image.setCloudProviderId(imageNotInDatabase.getImageInLocation().cloudProviderId());
        imageModelService.save(image);
    }
}
