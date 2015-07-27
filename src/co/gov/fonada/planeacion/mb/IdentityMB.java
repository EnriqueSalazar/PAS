package co.gov.fonada.planeacion.mb;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@RequestScoped
public class IdentityMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6906185822389980782L;

    private String Nickname;
    private String Email;
    private String userImage;
    private String userProfile;

    public IdentityMB() {

    }

    public String getLoginUrl() {
        ExternalContext ctx = FacesContext.getCurrentInstance()
                .getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest();
        HttpServletResponse response = (HttpServletResponse) ctx.getResponse();
        UserService userService = UserServiceFactory.getUserService();

//		System.out.println("IdentityMB getLoginUrl "
//				+ userService.createLoginURL(response.encodeURL(request
//						.getRequestURI())));
        return userService.createLoginURL(response.encodeURL(request
                .getRequestURI()));
    }

    public String getLogoutUrl() {
        UserService userService = UserServiceFactory.getUserService();

//		//System.out.println("IdentityMB getLogoutUrl "
//				+ userService.createLogoutURL("/"));
        return userService.createLogoutURL("/");
    }

    public boolean isLoggedIn() {
        UserService userService = UserServiceFactory.getUserService();
        if (userService.getCurrentUser() == null) {
            try{
                FacesContext.getCurrentInstance().getExternalContext().redirect(getLoginUrl());
            }catch(IOException ioe){

            }

        }
        return userService.getCurrentUser() != null;
    }

    public User currentUser() {
        UserService userService = UserServiceFactory.getUserService();

//		System.out.println("IdentityMB currentUser "
//				+ userService.getCurrentUser().getEmail());
        return userService.getCurrentUser();
    }

    /**
     * @return the name
     */
    public String getNickname() {
        try {
            UserService userService = UserServiceFactory.getUserService();
            Nickname = userService.getCurrentUser().getNickname();
        } catch (Exception ex) {

        }
        return Nickname;
    }

    /**
     * @param name the name to set
     */
    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        try {
            UserService userService = UserServiceFactory.getUserService();
            Email = userService.getCurrentUser().getEmail();
        } catch (Exception ex) {

        }
        return Email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        Email = email;
    }

    /**
     * @return the userImage
     */
    public String getUserImage() {
        try {
            UserService userService = UserServiceFactory.getUserService();
            String userId = userService.getCurrentUser().getUserId();
            userImage = "https://www.googleapis.com/plus/v1/people/"
                    + userId
                    + "?fields=image&key=AIzaSyCTNZufNv65EsvPB0ABljPIPqf0oc4s7zw";
        } catch (Exception ex) {

        }
        return userImage;
    }

    /**
     * @param userImage the userImage to set
     */
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    /**
     * @return the userProfile
     */
    public String getUserProfile() {
        // System.out.println("");
        // System.out.println("IdentityMB getUserProfile");

        try {

            UserService userService = UserServiceFactory.getUserService();
            String userId = userService.getCurrentUser().getUserId();
            String serverApiKey = "AIzaSyAALln_EXK0jOsbot8fUFXiiYeMWey3qTE";
            URL userProfileURL = new URL(
                    "https://www.googleapis.com/plus/v1/people/" + userId
                            + "?key=" + serverApiKey);
            HttpURLConnection connection = (HttpURLConnection) userProfileURL
                    .openConnection();
//			int responseCode = connection.getResponseCode();
            StringBuffer buffer;
            String line;
            // //System.out.println("IdentityMB userProfileURL " +
            // userProfileURL);
            // //System.out.println("IdentityMB getResponseCode " +
            // responseCode);
            // //System.out.println("IdentityMB getResponseMessage "
            // + connection.getResponseMessage());
            // //System.out.println("IdentityMB getErrorStream "
            // + connection.getErrorStream());

            // if (responseCode != HttpURLConnection.HTTP_OK) {
            // throw new Exception("HTTP response code: " +
            // String.valueOf(responseCode));
            // }
            try {
                buffer = new StringBuffer();
                InputStream input = connection.getInputStream();
                BufferedReader dataInput = new BufferedReader(
                        new InputStreamReader(input));
                while ((line = dataInput.readLine()) != null) {
                    // System.out.println(line);
                    buffer.append(line);
                    buffer.append("\r\n");
                }
                input.close();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
                return null;
            }
            userProfile = buffer.toString();

        } catch (Exception ex) {

        }
        return userProfile;

    }

    /**
     * @param userProfile the userProfile to set
     */
    public void setUserProfile(String userProfile) {

        this.userProfile = userProfile;
    }

}