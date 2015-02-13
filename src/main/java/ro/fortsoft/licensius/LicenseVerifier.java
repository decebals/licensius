package ro.fortsoft.licensius;

/**
 * @author Decebal Suiu
 */
public class LicenseVerifier {

	public static void main(String[] args) {
		LicenseManager licenseManager = LicenseManager.getInstance();
		try {
			License license = licenseManager.getLicense();
			System.out.println("license = " + license);
			boolean valid = licenseManager.isValidLicense(license);
			System.out.println("valid = " + valid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
