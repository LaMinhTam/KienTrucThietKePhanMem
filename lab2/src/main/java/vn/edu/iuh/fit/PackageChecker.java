package vn.edu.iuh.fit;

public class PackageChecker {
    private static final String PACKAGE_PATTERN = "com.companyname.*";

    public void checkPackage(String path) {
        //remove the first / and .java of the path
        path = path.substring(1, path.length() - 5);
        String[] packageParts = path.split("/");
        String packageName = String.join(".", packageParts);

        if (!packageName.matches(PACKAGE_PATTERN)) {
            System.out.println("1. Invalid package name: " + packageName);
        }
    }
}
