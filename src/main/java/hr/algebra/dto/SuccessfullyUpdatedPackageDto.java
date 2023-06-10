package hr.algebra.dto;

public class SuccessfullyUpdatedPackageDto {

    private boolean successfullyUpdatedPackage;

    public SuccessfullyUpdatedPackageDto() {
    }

    public SuccessfullyUpdatedPackageDto(boolean successfullyUpdatedPackage) {
        this.successfullyUpdatedPackage = successfullyUpdatedPackage;
    }

    public void setSuccessfullyUpdatedPackage(boolean successfullyUpdatedPackage) {
        this.successfullyUpdatedPackage = successfullyUpdatedPackage;
    }

    public boolean isSuccessfullyUpdatedPackage() {
        return successfullyUpdatedPackage;
    }
}
