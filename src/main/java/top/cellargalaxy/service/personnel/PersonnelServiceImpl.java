package top.cellargalaxy.service.personnel;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.bean.personnel.Authorized;
import top.cellargalaxy.bean.personnel.Permission;
import top.cellargalaxy.bean.personnel.Person;
import top.cellargalaxy.configuration.PersonneConfiguration;
import top.cellargalaxy.dao.personnel.AuthorizedMapper;
import top.cellargalaxy.dao.personnel.PermissionMapper;
import top.cellargalaxy.dao.personnel.PersonMapper;
import top.cellargalaxy.util.CsvDeal;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by cellargalaxy on 17-12-8.
 */
@Service
@Transactional
public class PersonnelServiceImpl implements PersonnelService {
	@Autowired
	private PersonMapper personMapper;
	@Autowired
	private AuthorizedMapper authorizedMapper;
	@Autowired
	private PermissionMapper permissionMapper;

	private final String token;
	private final int listPersonLength;

	private volatile int personPageCount;

	public PersonnelServiceImpl(PersonneConfiguration personneConfiguration) {
		token = personneConfiguration.getToken();
		listPersonLength = personneConfiguration.getListPersonLength();
		personPageCount = -1;
	}

	@Override
	public boolean addPerson(Person person) {
		try {
			if (person.getPassword() == null) {
				return false;
			}
			person.setPassword(DigestUtils.sha256Hex(person.getPassword()));
			if (person.getTeam() != null && person.getTeam().length() == 0) {
				person.setTeam(null);
			}
			if (personMapper.insertPerson(person) > 0 && authorizedMapper.insertAuthorized(new Authorized(person.getId(), -1)) > 0) {
				personPageCount = -1;
				return true;
			} else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return false;
			}
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public LinkedList<Person> addPersons(File file) {
		try {
			Iterable<CSVRecord> records = CsvDeal.createCSVRecords(file);
			if (records == null) {
				return null;
			}
			Iterator<CSVRecord> iterator = records.iterator();
			iterator.next();
			LinkedList<Person> persons = new LinkedList<>();
			LinkedList<Person> fail = new LinkedList<>();
			while (iterator.hasNext()) {
				CSVRecord record = iterator.next();
				Map<String, String> map = record.toMap();
				Integer grade = CsvDeal.string2Int(map.get("grade"));
				Date birthday = CsvDeal.string2Date(map.get("birthday"));
				if (grade == null || birthday == null) {
					fail.add(new Person(map.get("id"), map.get("name"), map.get("sex"), map.get("college"), -1, map.get("professionClass"), map.get("phone"), map.get("cornet"), map.get("qq"), null, map.get("introduction"), map.get("team"), map.get("password")));
				} else {
					persons.add(new Person(map.get("id"), map.get("name"), map.get("sex"), map.get("college"), grade.intValue(), map.get("professionClass"), map.get("phone"), map.get("cornet"), map.get("qq"), birthday, map.get("introduction"), map.get("team"), map.get("password")));
				}

			}
			for (Person person : persons) {
				if (!addPerson(person)) {
					fail.add(person);
				}
			}
			return fail;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean writePersonFile(File file) {
		try (CSVPrinter csvPrinter = CsvDeal.createCSVPrinter(file)) {
			if (csvPrinter == null) {
				return false;
			}
			csvPrinter.printRecord("id", "name", "sex", "college", "grade", "professionClass", "phone", "cornet", "qq", "birthday", "introduction", "team", "password");
			csvPrinter.printRecord("学号(字符串)", "名字(字符串)", "性别(字符串)", "学院(字符串)", "年级(数字)", "专业班级(字符串)", "手机(字符串)", "短号(字符串)", "qq(字符串)", "生日(" + CsvDeal.DATE_FORMAT_STRING + ")", "简介(字符串)", "组别(字符串)", "密码(字符串)");
			Person[] persons = personMapper.selectAllPerson();
			for (Person person : persons) {
				String birthday = CsvDeal.date2String(person.getBirthday());
				csvPrinter.printRecord(person.getId(), person.getName(), person.getSex(), person.getCollege(),
						person.getGrade(), person.getProfessionClass(), person.getPhone(), person.getCornet(),
						person.getQq(), birthday, person.getIntroduction(), person.getTeam(), person.getPassword());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removePerson(String id) {
		try {
			authorizedMapper.deleteAuthorizedByPersonId(id);
			if (personMapper.deletePerson(id) > 0) {
				return true;
			} else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return false;
			}
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public Person findPersonById(String id) {
		try {
			return personMapper.selectPersonById(id);
		} catch (Exception e) {
			dealException(e);
		}
		return null;
	}

	@Override
	public Person[] findPersons(int page) {
		try {
			int len = listPersonLength;
			int off = (page - 1) * len;
			return personMapper.selectPersons(off, len);
		} catch (Exception e) {
			dealException(e);
		}
		return new Person[0];
	}

	@Override
	public boolean changePerson(Person person) {
		try {
			if (person.getPassword() == null) {
				return false;
			}
			person.setPassword(DigestUtils.sha256Hex(person.getPassword()));
			if (person.getTeam() != null && person.getTeam().length() == 0) {
				person.setTeam(null);
			}
			return personMapper.updatePerson(person) > 0;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public int getPersonPageCount() {
		try {
			if (personPageCount == -1) {
				int count = personMapper.selectPersonCount();
				int len = listPersonLength;
				personPageCount = countPageCount(count, len);
			}
			return personPageCount;
		} catch (Exception e) {
			dealException(e);
		}
		return 0;
	}

	/////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean addPermission(Permission permission) {
		try {
			return permissionMapper.insertPermission(permission) > 0;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public boolean removePermission(int permission) {
		try {
			authorizedMapper.deleteAuthorizedByPermission(permission);
			if (permissionMapper.deletePermission(permission) > 0) {
				return true;
			} else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return false;
			}
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public Permission findPermission(int permission) {
		try {
			return permissionMapper.selectPermission(permission);
		} catch (Exception e) {
			dealException(e);
		}
		return null;
	}

	@Override
	public Permission[] findAllPermission() {
		try {
			return permissionMapper.selectAllPermission();
		} catch (Exception e) {
			dealException(e);
		}
		return new Permission[0];
	}

	@Override
	public boolean changePermission(Permission permission) {
		try {
			return permissionMapper.updatePermission(permission) > 0;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	/////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean addAuthorized(Authorized authorized) {
		try {
			return authorizedMapper.insertAuthorized(authorized) > 0;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public boolean removeAuthorized(Authorized authorized) {
		try {
			return authorizedMapper.deleteAuthorized(authorized) > 0;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public Authorized[] findAuthorizedById(String id) {
		try {
			return authorizedMapper.selectAuthorizedByPersonId(id);
		} catch (Exception e) {
			dealException(e);
		}
		return new Authorized[0];
	}

	@Override
	public Authorized[] findAuthorizedByPermission(int permission) {
		try {
			return authorizedMapper.selectAuthorizedByPermission(permission);
		} catch (Exception e) {
			dealException(e);
		}
		return new Authorized[0];
	}

	@Override
	public Authorized[] findAllAuthorized() {
		try {
			return authorizedMapper.selectAllAuthorized();
		} catch (Exception e) {
			dealException(e);
		}
		return new Authorized[0];
	}

	@Override
	public boolean findExistAuthorized(Authorized authorized) {
		try {
			return authorizedMapper.selectAuthorized(authorized) != null;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	////////////////////////////////////////////////////////////

	@Override
	public boolean checkToken(String token) {
		return this.token.equals(token);
	}

	@Override
	public boolean checkPassword(Person person) {
		try {
			if (person == null || person.getId() == null || person.getPassword() == null) {
				return false;
			}
			Person p = personMapper.selectPersonById(person.getId());
			if (p == null) {
				return false;
			}
			if (DigestUtils.sha256Hex(person.getPassword()).equals(p.getPassword())) {
				person.setName(p.getName());
				person.setSex(p.getSex());
				person.setCollege(p.getCollege());
				person.setGrade(p.getGrade());
				person.setProfessionClass(p.getProfessionClass());
				person.setPhone(p.getPhone());
				person.setCornet(p.getCornet());
				person.setQq(p.getQq());
				person.setBirthday(p.getBirthday());
				person.setIntroduction(p.getIntroduction());
				person.setTeam(p.getTeam());
				return true;
			}
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	@Override
	public boolean checkPersonnelDisabled(Person person) {
		return findExistAuthorized(person, -1);
	}

	@Override
	public boolean checkPersonnelAdmin(Person person) {
		return findExistAuthorized(person, 1);
	}

	@Override
	public boolean checkPersonnelRoot(Person person) {
		return findExistAuthorized(person, 0);
	}

	/////////////////////////////////////////////////////////////////////////
	private boolean findExistAuthorized(Person person, int permission) {
		try {
			if (person == null) {
				return false;
			}
			if (person.existAuthorized(permission)) {
				return true;
			} else {
				Authorized authorized = authorizedMapper.selectAuthorized(new Authorized(person.getId(), permission));
				if (authorized != null) {
					person.addAuthorized(authorized);
					return true;
				}
			}
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}

	private String[] createPages(int count, int len) {
		String[] pages = new String[countPageCount(count, len)];
		for (int i = 0; i < pages.length; i++) {
			pages[i] = (i + 1) + "";
		}
		return pages;
	}

	private int countPageCount(int count, int len) {
		if (count % len == 0) {
			return count / len;
		} else {
			return count / len + 1;
		}
	}

	private void dealException(Exception e) {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		e.printStackTrace();
	}
}
