/**
 * Created by cellargalaxy on 17-11-11.
 */

var rootUrl = null;

function getRootUrl() {
    if (rootUrl == null) {
        rootUrl = $("#rootUrl").attr("value");
        if (rootUrl == '/') {
            rootUrl = '';
        }
    }
    return rootUrl;
}
/////////////////////////////////////
function createPersonFormVue(func) {
    var personFormVue = new Vue({
        el: '#personForm',
        data: {
            person: {
                id: null,
                name: null,
                sex: '男',
                college: null,
                grade: null,
                professionClass: null,
                phone: null,
                cornet: null,
                qq: null,
                birthday: null,
                introduction: null,
                team: null,
                password: null,
                pw: null
            }
        },
        methods: {
            inquirePersonById: function (id) {
                inquirePersonById(this.loadData, id);
            },
            post: function () {
                func(this.person.id, this.person.name, this.person.sex, this.person.college, this.person.grade, this.person.professionClass, this.person.phone, this.person.cornet, this.person.qq, this.person.birthday, this.person.introduction, this.person.team, this.person.password, this.person.pw);
            },
            loadData: function (data) {
                if (data.result) {
                    this.person = data.data;
                    this.person.pw = null;
                } else {
                    this.person.id = '查无此人';
                    this.person.name = null;
                    this.person.sex = '男';
                    this.person.college = null;
                    this.person.grade = null;
                    this.person.professionClass = null;
                    this.person.phone = null;
                    this.person.cornet = null;
                    this.person.qq = null;
                    this.person.birthday = null;
                    this.person.introduction = null;
                    this.person.team = null;
                    this.person.password = null;
                    this.person.pw = null;
                }
            }
        }
    });
    return personFormVue;
}
function signUp(id, name, sex, college, grade, professionClass, phone, cornet, qq, birthday, introduction, team, password, pw) {
    if (id == null || id == '') {
        alert("写个学号好让我知道你是谁吧");
        return;
    }
    if (name == null || name == '') {
        alert("写个名字好让我知道你是谁吧");
        return;
    }
    if (college == null || college == '') {
        alert("填个学院呗");
        return;
    }
    if (grade == null || grade == '') {
        alert("年级呢");
        return;
    }
    if (grade < 15 || grade > 99) {
        alert("你" + grade + "级的吗");
        return;
    }
    if (professionClass == null || professionClass == '') {
        alert("专业班级是啥");
        return;
    }
    if (phone == null || phone == '') {
        alert("留个手机吧");
        return;
    }
    if (qq == null || qq == '') {
        alert("给个qq方便联系");
        return;
    }
    if (birthday == null || birthday == '') {
        alert("写一下生日呢");
        return;
    }
    if (introduction!=null&&introduction.length>255){
        alert("简介太长了，不超过255个字符");
        return;
    }
    if (password == null || password == '' || pw == null || pw == '') {
        alert("密码怎么可以为空");
        return;
    }
    if (password != pw) {
        alert("两次密码不一样了呀");
        return;
    }
    $.ajax({
        url: getRootUrl() + '/personnel/signUp',
        type: 'post',
        data: {
            id: id,
            name: name,
            sex: sex,
            college: college,
            grade: grade,
            professionClass: professionClass,
            phone: phone,
            cornet: cornet,
            qq: qq,
            birthday: birthday,
            introduction: introduction,
            team: team,
            password: password,
            pw: pw
        },
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            alert(data.data);
            if (data.result) {
                location.href = getRootUrl() + '/login';
            }
        }
    });
}
//////////////////////////////////////
function createloginPersonnelFormVue() {
    var loginPersonnelForm = new Vue({
        el: '#loginPersonnelForm',
        data: {
            id: null, password: null
        },
        methods: {
            loginPersonnel: function () {
                loginPersonnel(this.id, this.password);
            }
        }
    });
    return loginPersonnelForm;
}
function loginPersonnel(id, password) {
    if (id == null || id == '') {
        alert("写个学号好让我知道你是谁吧");
        return;
    }
    if (password == null) {
        alert("密码都不输怎么登录");
        return;
    }
    $.ajax({
        url: getRootUrl() + '/login',
        type: 'post',
        data: {id: id, password: password},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            if (data.result) {
                location.href = getRootUrl() + '/page/listNetview';
            } else {
                alert(data.data);
            }
        }
    });
}
//////////////////////////////////////
function ajaxErrorDeal() {
    // alert("网络错误!");
}

