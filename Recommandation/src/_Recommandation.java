import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class _Recommandation {

	public static void main(String[] args) {
		List<Long> _listUser;
		List<Long> _listService;
		List<User> userList = new ArrayList<User>();
		List<Service> serviceList = new ArrayList<Service>();

		long time = System.currentTimeMillis();

		CsvFileHelper csvFileHelper = new CsvFileHelper();

		_listUser = csvFileHelper.getUserList();
		_listService = csvFileHelper.getServiceList();

		userList = prepareUserList(_listUser, csvFileHelper.getInfoUserList());
		serviceList = prepareServiceList(_listService, csvFileHelper.getInfoServiceList());

		System.out.println("Total rt user initial : " + _listUser.size());
		System.out.println("Total rt service initial : " + _listService.size());

		System.out.println("Total user initial : " + csvFileHelper.getInfoUserList().size());
		System.out.println("Total service initial : " + csvFileHelper.getInfoServiceList().size());

		System.out.println("Total user reel : " + userList.size());
		System.out.println("Total service reel : " + serviceList.size());

		//////////////////////////////////////////////////////////////////////
		PrepareGeoDistance GeoDistance = new PrepareGeoDistance(userList, serviceList);
		CsvFileHelper fileHelper = new CsvFileHelper();
		Random rand = new Random();
		int randomUser = 10;// rand.nextInt(142);
		int randomServ = 817;// rand.nextInt(4500);
		System.out.println("random user : " + randomUser);
		System.out.println("random service : " + randomServ);
		List<Float> Sy = fileHelper.getAllUserServiceResponseTimesFile(randomUser, randomServ);
		if (Sy.size() != 0) {
			float somme = 0;
			float moyenne = 0;
			float normeSy = 0;
			float normeSx = 0;
			float Sxy;
			float distusers;
			float distservices;
			float dist;
			for (Float sy : Sy) {
				somme += sy;
			}
			moyenne = somme / Sy.size();
			for (int k = 0; k < Sy.size(); k++) {
				Sy.set(k, Sy.get(k) - moyenne);
				normeSy += Sy.get(k) * Sy.get(k);
			}
			normeSy = (float) Math.sqrt(normeSy);

			int i;
			int j;
			int size;
			String adres1;
			String adres2;
			String adres3;
			adres1 = userList.get(randomUser).getIP_Address();
			adres2 = serviceList.get(randomServ).getIP_Address();

			for (i = 0; i < 142/* i<randomUser */; i++) {
				adres3 = userList.get(i).getIP_Address();
				if (adres3.compareTo("null") == 0)
					continue;
				distusers = GeoDistance.getGeoDistance(adres1, adres3);
				for (j = 0; j < 4500/* j < randomServ */; j++) {
					adres3 = serviceList.get(j).getIP_Address();
					if (adres3.compareTo("null") == 0)
						continue;
					List<Float> Sx = fileHelper.getAllUserServiceResponseTimesFile(i, j);
					if (Sx.size() == 0)
						continue;
					somme = 0;
					for (Float sx : Sx) {
						somme += sx;
					}
					moyenne = somme / 64;
					for (int k = 0; k < Sx.size(); k++) {
						Sx.set(k, Sx.get(k) - moyenne);
						normeSx += Sx.get(k) * Sx.get(k);
					}
					normeSx = (float) Math.sqrt(normeSx);
					float p = 0;
					size = (Sy.size() > Sx.size()) ? Sx.size() : Sy.size();
					for (int l = 0; l < size; l++) {
						p += Sy.get(l) * Sx.get(l);
					}
					Sxy = p / (normeSx * normeSy);

					distservices = GeoDistance.getGeoDistance(adres2, adres3);

					dist = (float) (0.5 * (distusers + distservices));
					System.out
							.println("--- User : " + i + " --Service : " + j + " Sxy = " + Sxy + " distance = " + dist);
				}

			}

		}
		//////////////////////////////////////////////////////////////////////

		System.out.println("Total Time : " + (System.currentTimeMillis() - time));
	}

	private static List<User> prepareUserList(List<Long> _listUser, List<String[]> infoUserList) {
		List<User> userList = new ArrayList<User>();
		long id;
		String ip;
		for (String[] oneData : infoUserList) {
			ip = oneData[1];
			if (ip.compareTo("null") != 0) {
				id = Long.parseLong(oneData[0]);
				if (_listUser.contains(id))
					userList.add(new User(oneData));
			}
		}
		return userList;
	}

	private static List<Service> prepareServiceList(List<Long> _listService, List<String[]> infoServiceList) {
		List<Service> serviceList = new ArrayList<Service>();
		long id;
		String ip;
		for (String[] oneData : infoServiceList) {
			ip = oneData[3];
			if (ip.compareTo("null") != 0) {
				id = Long.parseLong(oneData[0]);
				if (_listService.contains(id))
					serviceList.add(new Service(oneData));
			}
		}
		return serviceList;
	}

}
