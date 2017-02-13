
public class Service {
	int service_ID;
	String ip_address;
	String country;
	String ip_no;
	float latitude;
	float longitude;

	public Service(String[] list) {
		super();
		this.service_ID = Integer.parseInt(list[0]);
		this.ip_address = list[3];
		this.country = list[4];
		this.ip_no = list[5];
		if (list[7].equals("null"))
			this.latitude = 0;
		else
			this.latitude = Float.parseFloat(list[7]);
		;
		if (list[8].equals("null"))
			this.longitude = 0;
		else
			this.longitude = Float.parseFloat(list[8]);
		;
	}

	public Service(int service_ID, String iP_Address, String country, String iP_No, float latitude, float longitude) {
		super();
		this.service_ID = service_ID;
		this.ip_address = iP_Address;
		this.country = country;
		this.ip_no = iP_No;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getService_ID() {
		return service_ID;
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
