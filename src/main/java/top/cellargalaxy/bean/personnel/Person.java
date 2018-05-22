package top.cellargalaxy.bean.personnel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 人员
 * Created by cellargalaxy on 17-12-7.
 */
public class Person {
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 学院
	 */
	private String college;
	/**
	 * 年级
	 */
	private int grade;
	/**
	 * 专业班级
	 */
	private String professionClass;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 短号
	 */
	private String cornet;
	/**
	 * qq
	 */
	private String qq;
	/**
	 * 生日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date birthday;
	/**
	 * 自我介绍
	 */
	private String introduction;
	/**
	 * 所在队
	 */
	private String team;
	/**
	 * 密码
	 */
	@JsonIgnore
	private String password;

	/**
	 * 权限
	 */
	private final List<Authorized> authorizeds;

	public Person() {
		authorizeds = new LinkedList<>();
	}

	public Person(String id, String name, String sex, String college, int grade, String professionClass, String phone, String cornet, String qq, Date birthday, String introduction, String team, String password) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.college = college;
		this.grade = grade;
		this.professionClass = professionClass;
		this.phone = phone;
		this.cornet = cornet;
		this.qq = qq;
		this.birthday = birthday;
		this.introduction = introduction;
		this.team = team;
		this.password = password;
		authorizeds = new LinkedList<>();
	}

	public void addAuthorized(Authorized authorized) {
		if (authorized != null) {
			authorizeds.add(authorized);
		}
	}

	public boolean existAuthorized(int permission) {
		for (Authorized authorized : authorizeds) {
			if (authorized.getPermission() == permission) {
				return true;
			}
		}
		return false;
	}

	//////////////////////////////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getProfessionClass() {
		return professionClass;
	}

	public void setProfessionClass(String professionClass) {
		this.professionClass = professionClass;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCornet() {
		return cornet;
	}

	public void setCornet(String cornet) {
		this.cornet = cornet;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Person{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", sex='" + sex + '\'' +
				", college='" + college + '\'' +
				", grade=" + grade +
				", professionClass='" + professionClass + '\'' +
				", phone='" + phone + '\'' +
				", cornet='" + cornet + '\'' +
				", qq='" + qq + '\'' +
				", birthday=" + birthday +
				", introduction='" + introduction + '\'' +
				", team='" + team + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
