package constants;

public class UrlConstants {

	public static String BASE_URL ="https://api.github.com";
	public static String CREATE_REPO_URL=BASE_URL+"/user/repos";
	public static String REPOSITORY_URL =BASE_URL+"/repos/{owner_name}/{repo_name}";
	public static String VULNERABLITY_URL=REPOSITORY_URL+"/vulnerability-alerts";
	public static String TAG_PROTECTION_URL= REPOSITORY_URL +"/tags/protection";

}
