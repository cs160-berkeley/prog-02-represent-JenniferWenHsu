/**
 * Created by apple on 2/26/16.
 */
public class Representative {

    private String name;
    private String party;
    private String tweetMessage;
    private String emailLink;
    private String websiteLink;

    public Representative(){
    }

    public void setName(String nm){
        name = nm;
    }
    public String getName(){
        return name;
    }

    public void setParty(String p){
        party = p;
    }

    public String getParty(){
        return party;
    }

    public void setTweetMessage(String tweet){
        tweetMessage = tweet;
    }

    public String getTweet(){return tweetMessage;}

    public void setEmail(String email){
        emailLink = email;
    }

    public String getEmail(){
        return emailLink;
    }

    public void setWebsiteLink(String weblink){websiteLink = weblink;}

    public String getWebsiteLink(){
        return websiteLink;
    }

}
