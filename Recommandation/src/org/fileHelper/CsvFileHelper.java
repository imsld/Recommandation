import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHelper {

	static final String USER_FILE_PATH = "Dataset/dataset1/userlist.txt";
	static final String SERVICE_FILE_PATH = "Dataset/dataset1/wslist.txt";
	static final String RESPONSE_TIME_PATH = "Dataset/dataset2/rtdata.txt";

	private List<Long> _userList;
	private List<Long> _serviceList;
	private List<String[]> _info_userList;
	private List<String[]> _info_serviceList;

	public CsvFileHelper() {
		init(RESPONSE_TIME_PATH);
		_info_userList = getData(read_lists(USER_FILE_PATH));
		_info_serviceList = getData(read_lists(SERVICE_FILE_PATH));
	}

	////////////////////////////
	////////////////////////////
	public List<Float> getAllUserServiceResponseTimesFile(int user, int service) {

		List<Float> result = new ArrayList<Float>(64);
		String user1 = Integer.toString(user);
		String service1 = Integer.toString(service);
		try {
			InputStream input = new FileInputStream(RESPONSE_TIME_PATH);
			byte[] buffer = new byte[1024];
			StringBuilder chaine = new StringBuilder();
			int nbRead = 0;
			int count = 0;
			boolean nouveauUser = true;
			boolean nouveauServ = true;
			long nbEsp = 0;

			while ((nbRead = input.read(buffer)) != -1) {
				for (int i = 0; i < nbRead; i++)
					if (((char) buffer[i]) == '\n') {
						if ((nouveauUser) && (nouveauServ)) {
							chaine.delete(0, chaine.length());
							nbEsp = 0;
							continue;
						}
						String[] val = chaine.toString().split(" ");
						result.add(Float.parseFloat(val[2]));
						count++;
						if (count == 64)
							break;
						chaine.delete(0, chaine.length());
						nouveauUser = true;
						nouveauServ = true;
						nbEsp = 0;
					} else {
						if ((char) buffer[i] == ' ') {
							if (nbEsp == 0) {
								nbEsp++;
								if (nouveauUser)
									if (chaine.toString().trim().compareTo(user1) != 0) {
										chaine.delete(0, chaine.length());
										continue;
									} else {
										nouveauUser = false;
										chaine.delete(0, chaine.length());
									}
							}
							if (nbEsp == 1) {
								nbEsp++;
								if (nouveauServ)
									if (chaine.toString().trim().compareTo(service1) != 0) {
										chaine.delete(0, chaine.length());
										continue;
									} else {
										nouveauUser = false;
										nbEsp++;
										chaine.delete(0, chaine.length());
									}
							}
							/*if (nbEsp == 2) {
								nbEsp++;
								continue;
							}*/
						}

						chaine.append((char) buffer[i]);
					}
				if (count == 64)
					break;
			}
			input.close();
			System.gc();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	////////////////////////////
	private List<String[]> getData(List<String> lines) {
		List<String[]> data = new ArrayList<String[]>(lines.size());
		String sep = "\t";
		for (String line : lines) {
			String[] oneData = line.split(sep);
			data.add(oneData);
		}
		return data;
	}

	private List<String> read_lists(String filePath) {
		List<String> result = new ArrayList<String>();
		File file = new File(filePath);

		try {
			FileReader fr = new FileReader(file);
			InputStream ips = new FileInputStream(filePath);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			br.readLine();
			br.readLine();
			while ((line = br.readLine()) != null) {
				result.add(line);
			}

			br.close();
			fr.close();
			ipsr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void init(String filePath) {
		_userList = new ArrayList<Long>();
		_serviceList = new ArrayList<Long>();
		try {
			InputStream input = new FileInputStream(filePath);
			byte[] buffer = new byte[1024 * 4];
			StringBuilder chaine = new StringBuilder();
			int nbRead = 0;
			long k;
			String[] val;
			long last_k = -1;
			while ((nbRead = input.read(buffer)) != -1) {
				for (int i = 0; i < nbRead; i++) {
					if (((char) buffer[i]) == '\n') {
						val = chaine.toString().split(" ");
						k = Long.parseLong(val[0]);
						if (last_k != k)
							if (!_userList.contains(k)) {
								_userList.add(k);
								last_k = k;
							}
						if (_serviceList.size() == 4500) {
							chaine.delete(0, chaine.length());
							continue;
						}
						k = Long.parseLong(val[1]);
						if (!_serviceList.contains(k))
							_serviceList.add(k);
						chaine.delete(0, chaine.length());
					} else
						chaine.append((char) buffer[i]);
				}
				if ((_userList.size() == 142) && (_serviceList.size() == 4500))
					break;
			}
			input.close();
		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

	public List<Long> getUserList() {
		return _userList;
	}

	public List<Long> getServiceList() {
		return _serviceList;
	}

	public List<String[]> getInfoUserList() {
		return _info_userList;
	}

	public List<String[]> getInfoServiceList() {
		return _info_serviceList;
	}
}
