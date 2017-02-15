
public class User {
	
	private int user_ID;
	private String ip_address;
	private String country;
	private String ip_no;
	private float latitude;
	private float longitude;

	public User(String[] list){
		super();
		this.user_ID = Integer.parseInt(list[0]);
		this.ip_address = list[1];
		this.country = list[2];
		this.ip_no = list[3];
		this.latitude = Float.parseFloat(list[5]);
		this.longitude = Float.parseFloat(list[6]);
	}
	
	public User(int user_ID, String iP_Address, String country, String iP_No, float latitude, float longitude) {
		super();
		this.user_ID = user_ID;
		this.ip_address = iP_Address;
		this.country = country;
		this.ip_no = iP_No;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	public int getUser_ID() {
		return user_ID;
	}

	public String getIP_Address() {
		return ip_address;
	}

	public String getCountry() {
		return country;
	}

	public String getIP_No() {
		return ip_no;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

}
