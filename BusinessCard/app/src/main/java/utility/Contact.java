package utility;

/**
 * Created by evenyan on 16/7/29.
 */
public class Contact {
    private int imageId;
    private String name;
    private String position;
    private String company_name;

    public Contact(int imageId, String name, String position, String company_name) {
        this.imageId = imageId;
        this.name = name;
        this.position = position;
        this.company_name = company_name;

    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getCompany_name() {
        return company_name;
    }
}
