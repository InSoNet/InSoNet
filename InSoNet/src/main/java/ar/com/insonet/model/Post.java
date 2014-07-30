package ar.com.insonet.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class Post {

	@NotEmpty
	private String message;
	@NotEmpty
	private String socialNetwork;
	private String privacy;
	private MultipartFile filePhoto;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSocialNetwork() {
		return socialNetwork;
	}
	public void setSocialNetwork(String socialNetwork) {
		this.socialNetwork = socialNetwork;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public MultipartFile getFilePhoto() {
		return filePhoto;
	}
	public void setFilePhoto(MultipartFile filePhoto) {
		this.filePhoto = filePhoto;
	}
	
	
}
