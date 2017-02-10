import java.io.IOException;
import java.util.List;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

public class PrepareGeoDistance {
	private List<User> userList;
	private List<Service> serviceList;

	private double[][] usersDistanceMatrix;
	private double[][] servicesDistanceMatrix;

	private LookupService cl;

	public PrepareGeoDistance(List<User> userList, List<Service> serviceList) {
		super();
		try {
			cl = new LookupService("GeoLiteCity/GeoLiteCity.dat", LookupService.GEOIP_MEMORY_CACHE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * this.userList = userList; this.serviceList = serviceList;
		 * setUsersDistanceMatrix(userList.size());
		 * setServicesDistanceMatrix(serviceList.size());
		 */

	}

	private void setServicesDistanceMatrix(int size) {
		servicesDistanceMatrix = new double[size][size];
		for (int i = 0; i < size; i++) {
			servicesDistanceMatrix[i][i] = 0;
			for (int j = i + 1; j < size; j++) {
				servicesDistanceMatrix[i][j] = getGeoDistance(serviceList.get(i).getIP_Address(),
						serviceList.get(j).getIP_Address());
				servicesDistanceMatrix[j][i] = servicesDistanceMatrix[i][j];
				System.out.println(serviceList.get(i).getService_ID() + "[" + i + "]--->"
						+ serviceList.get(j).getService_ID() + "[" + j + " = " + servicesDistanceMatrix[i][j]);
			}
		}
	}

	private void setUsersDistanceMatrix(int size) {
		usersDistanceMatrix = new double[size][size];
		for (int i = 0; i < size; i++) {
			usersDistanceMatrix[i][i] = 0;
			for (int j = i + 1; j < size; j++) {
				usersDistanceMatrix[i][j] = getGeoDistance(userList.get(i).getIP_Address(),
						userList.get(j).getIP_Address());
				usersDistanceMatrix[j][i] = usersDistanceMatrix[i][j];
				System.out.println(userList.get(i).getUser_ID() + "[" + i + "]--->" + userList.get(j).getUser_ID() + "["
						+ j + " = " + usersDistanceMatrix[i][j]);
			}
		}
	}

	public float getGeoDistance(String ip_Address1, String ip_Address2) {
		float distance = -1;
		Location l1 = cl.getLocation(ip_Address1);
		Location l2 = cl.getLocation(ip_Address2);
		if ((l1 == null) || (l2 == null))
			return distance;
		distance = (float) l1.distance(l2);

		return distance;
	}

	/*
	 * try { LookupService cl = new LookupService("GeoLiteCity/GeoLiteCity.dat",
	 * LookupService.GEOIP_MEMORY_CACHE);
	 * 
	 * Location l1 = cl.getLocation("213.52.50.8"); Location l2 =
	 * cl.getLocation("100.152.100.18"); System.out.println(
	 * "	countryCode (l1/l2): " + l1.countryCode + "/" + l2.countryCode +
	 * "\n countryName (l1/l2): " + l1.countryName + "/" + l2.countryName +
	 * "\n region (l1/l2): " + l1.region + "/" + l2.region +
	 * "\n regionName (l1/l2): " + regionName.regionNameByCode(l1.countryCode,
	 * l1.region) + "/" + regionName.regionNameByCode(l2.countryCode, l2.region)
	 * + "\n city (l1/l2): " + l1.city + "/" + l2.city +
	 * "\n postalCode (l1/l2): " + l1.postalCode + "/" + l2.postalCode +
	 * "\n latitude (l1/l2): " + l1.latitude + "/" + l2.latitude +
	 * "\n longitude (l1/l2): " + l1.longitude + "/" + l2.longitude +
	 * "\n distance (l1--l2): " + l1.distance(l2) + "\n distance (l2--l1): " +
	 * l2.distance(l1) + "\n metro code (l1/l2): " + l1.metro_code + "/" +
	 * l2.metro_code + "\n area code (l1/l2): " + l1.area_code + "/" +
	 * l2.area_code + "\n timezone (l1/l2): " +
	 * timeZone.timeZoneByCountryAndRegion(l1.countryCode, l1.region) + "/" +
	 * timeZone.timeZoneByCountryAndRegion(l2.countryCode, l2.region));
	 * cl.close(); } catch (IOException e) { 
	 * e.printStackTrace(); }
	 */

}
