package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.avaje.ebean.annotation.EnumMapping;

import play.db.ebean.Model;

@Entity
public class User extends Model {

	@Id
	private Integer userId;
	@Column(length=20)
	private String userName;
	private String userPassword;
	private String userFirstname;
	private String userLastname;
	private String userEmailAddress;
	@Column(length=20)
	private String additionalNumber;
	@Enumerated(EnumType.STRING)
	private Language userLanguage;
	@Lob
	private String userAdditionalDescription;
	@Column(length=8)
	private String userVerificationCode;
	private Boolean userVerified;
	private Date userRegisterDate;
	private Boolean userCommunicationSms;
	private Boolean userCommunicationEmail;
	private Integer userLostPasswordCount;
	private Boolean userStatus;
	
	public static Finder<Integer,User> find = new Finder<>(Integer.class,User.class);
	
	public static User getUserByUserNameAndPassword(String username,String password) throws NoSuchAlgorithmException {
		return find.where().eq("userName", username).eq("userPassword",User.md5Encryption(password)).findUnique();
	}
	
	public static User getUserByUserName(String username) {
		return find.where().eq("userName", username).findUnique();
	}
	
	public static User getUserByUserNameAndValidationCode(String username,String validationCode) {
		return find.where().eq("userName", username).eq("userVerificationCode", validationCode).findUnique();
	}
	
	public static User findById(Integer id) {
		return find.byId(id);
	}
	
	public static User findByUserEmail(String email) {
		return find.where().eq("userEmailAddress", email).findUnique();
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserFirstname() {
		return userFirstname;
	}
	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}
	public String getUserLastname() {
		return userLastname;
	}
	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}
	public String getUserEmailAddress() {
		return userEmailAddress;
	}
	public void setUserEmailAddress(String userEmailAddress) {
		this.userEmailAddress = userEmailAddress;
	}
	public Language getUserLanguage() {
		return userLanguage;
	}
	public void setUserLanguage(Language userLanguage) {
		this.userLanguage = userLanguage;
	}
	public String getUserAdditionalDescription() {
		return userAdditionalDescription;
	}
	public void setUserAdditionalDescription(String userAdditionalDescription) {
		this.userAdditionalDescription = userAdditionalDescription;
	}
	public String getUserVerificationCode() {
		return userVerificationCode;
	}
	public void setUserVerificationCode(String userVerificationCode) {
		this.userVerificationCode = userVerificationCode;
	}
	public Boolean getUserVerified() {
		return userVerified;
	}
	public void setUserVerified(Boolean userVerified) {
		this.userVerified = userVerified;
	}
	public Date getUserRegisterDate() {
		return userRegisterDate;
	}
	public void setUserRegisterDate(Date userRegisterDate) {
		this.userRegisterDate = userRegisterDate;
	}
	public Boolean getUserCommunicationSms() {
		return userCommunicationSms;
	}
	public void setUserCommunicationSms(Boolean userCommunicationSms) {
		this.userCommunicationSms = userCommunicationSms;
	}
	public Boolean getUserCommunicationEmail() {
		return userCommunicationEmail;
	}
	public void setUserCommunicationEmail(Boolean userCommunicationEmail) {
		this.userCommunicationEmail = userCommunicationEmail;
	}
	public Integer getUserLostPasswordCount() {
		return userLostPasswordCount;
	}
	public void setUserLostPasswordCount(Integer userLostPasswordCount) {
		this.userLostPasswordCount = userLostPasswordCount;
	}
	public Boolean getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Boolean userStatus) {
		this.userStatus = userStatus;
	}

	public String getAdditionalNumber() {
		return additionalNumber;
	}

	public void setAdditionalNumber(String additionalNumber) {
		this.additionalNumber = additionalNumber;
	}
	
	public static String md5Encryption(String password) throws NoSuchAlgorithmException { 
		 MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(password.getBytes());
	 
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	 
	        
	        return sb.toString();
	}

	
	
}
