package WebParser;

import java.net.URL;
import java.util.LinkedHashSet;

public class RecordOfSoftUpdate{

    private String name;
    private URL urlLink;
    private String deviceAvailability;
    private String releaseDate;
    private LinkedHashSet<String> deviceCategoryTags;


    public RecordOfSoftUpdate(String name, URL urlLink, String deviceAvailability, String releaseDate){
        this.name = name;
        this.urlLink = urlLink;
        this.deviceAvailability = deviceAvailability;
        this.releaseDate = releaseDate;

        deviceCategoryTags = new LinkedHashSet<>();

        if (deviceAvailability.contains("iPhone")){
            this.deviceCategoryTags.add("iPhone");
            this.deviceCategoryTags.add("iOS");
        }
        if (deviceAvailability.contains("iPad")){
            this.deviceCategoryTags.add("iPadOS");
        }
        if (deviceAvailability.contains("iPod")){
            this.deviceCategoryTags.add("iPod");
            this.deviceCategoryTags.add("iOS");
        }
        if (deviceAvailability.contains("Mac") || deviceAvailability.contains("mac")){
            this.deviceCategoryTags.add("macOS");
        }
        if (deviceAvailability.contains("Windows")){
            this.deviceCategoryTags.add("Win");
        }
        if (deviceAvailability.contains("Apple Watch")){
            this.deviceCategoryTags.add("watchOS");
        }
        if (deviceAvailability.contains("Apple TV")){
            this.deviceCategoryTags.add("tvOS");
        }
        if (deviceAvailability.contains("Swift")){
            this.deviceCategoryTags.add("Swift");
        }
        if (deviceAvailability.contains("Ubuntu")){
            this.deviceCategoryTags.add("Ubuntu");
        }
        if (deviceAvailability.contains("iOS")){
            this.deviceCategoryTags.add("iOS");
        }
    }

    public String getName() {
        return name;
    }

    public URL getUrlLink() {
        return urlLink;
    }

    public String getDeviceAvailability() {
        return deviceAvailability;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public LinkedHashSet<String> getDeviceCategoryTags() {
        return deviceCategoryTags;
    }

}
