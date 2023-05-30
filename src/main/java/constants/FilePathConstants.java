package constants;

public class FilePathConstants {
	public static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/main/resources/";
	public static final String TESTDATA_HOME = RESOURCES_PATH + "testdata/";
	public static final String SCHEMA_HOME = RESOURCES_PATH + "schema/";
	public static final String REPORT_FILE_PATH = System.getProperty("user.dir") + "/reports/output.html";
	public static final String CREATE_REPO_SCHEMA_PATH = SCHEMA_HOME+"create-repo-schema.json";
	public static final String BAD_CREDENTIALS_SCHEMA_PATH = SCHEMA_HOME+"bad-credentials-schema.json";
	public static final String TAG_PROTECTION_SCHEMA_PATH = SCHEMA_HOME+"tag-protection-schema.json";
	public static final String REPOSITORY_TESTDATA_PATH =TESTDATA_HOME+"repository.properties";
	public static final String LOG_FILE_PATH ="logs/request_response_logs.txt";






}
