package payload.data;

import com.github.javafaker.Faker;
import payload.pojo.RepositoryPayload;

public class RepositoryData {
    public static Faker faker = new Faker();
    static RepositoryPayload payload = new RepositoryPayload();

    public static RepositoryPayload createRepositoryData() {
        payload.setName(faker.name().username());
        payload.setDescription(faker.name().title());
        payload.setHomepage("https://github.com");
        payload.setIs_private(false);
        payload.setIs_template(true);
        return payload;
    }

    public static RepositoryPayload updateRepoData() {
        payload.setDescription(faker.name().title());
        payload.setName(faker.name().username());
        return payload;
    }


}
