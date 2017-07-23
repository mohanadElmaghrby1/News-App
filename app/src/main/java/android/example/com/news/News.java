package android.example.com.news;

/**
 * Created by mohanad on 22/07/17.
 */

public class News {

    /*the news title*/
    private String mTitle;

    /*the news section*/
    private String mSection;

    /*the news web url */
    private String mWebURL;

    /**
     *
     * @param mTitle : news title
     * @param mSection : news section
     * @param mWebURL : news web url
     */
    public News(String mTitle, String mSection, String mWebURL) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mWebURL = mWebURL;
    }

    /**
     *
     * @return the news title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     *
     * @return the news section
     */
    public String getSection() {
        return mSection;
    }

    /**
     *
     * @return the news web url
     */
    public String getWebURL() {
        return mWebURL;
    }
}
