/**
 * Created by cellargalaxy on 18-1-2.
 */

var token = null;


function getToken() {
    if (token == null) {
        token = $("#token").attr("value");
    }
    return token;
}
///////////////////////////////////////////////

function createAddEquipmentsFormVue() {
    var addEquipmentsFormVue = new Vue({
        el: '#addEquipmentsForm',
        data: {
            build: {}
        },
        methods: {
            uploadFile: function () {
                addEquipmentsForm(this.loadData);
            },
            loadData: function (data) {
                if (data.result) {
                    this.build = data.data;
                } else {
                    alert(data.data);
                }
            }
        }
    });
    return addEquipmentsFormVue;
}

function addEquipmentsForm(loadData) {
    var files = $('#equipmentFile').prop('files');
    if (files == null || files == '' || files.length == 0 || files[0] == null || files[0] == '') {
        alert('请选择文件');
        return;
    }
    var data = new FormData();
    data.append('file', files[0]);
    $.ajax({
        url: getRootUrl() + '/monitor/admin/uploadEquipmentFile',
        type: 'post',
        data: data,
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        cache: false,
        processData: false,
        contentType: false,

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}
/////////////////////////////////////////////
function createAddPersonsFormVue() {
    var addPersonsFormVue = new Vue({
        el: '#addPersonsForm',
        data: {
            persons: []
        },
        methods: {
            uploadFile: function () {
                addPersonsForm(this.loadData);
            },
            loadData: function (data) {
                if (data.result) {
                    this.persons = data.data;
                } else {
                    alert(data.data);
                }
            }
        }
    });
    return addPersonsFormVue;
}

function addPersonsForm(loadData) {
    var files = $('#personFile').prop('files');
    if (files == null || files == '' || files.length == 0 || files[0] == null || files[0] == '') {
        alert('请选择文件');
        return;
    }
    var data = new FormData();
    data.append('file', files[0]);
    $.ajax({
        url: getRootUrl() + '/personnel/admin/uploadPersonFile',
        type: 'post',
        data: data,
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        cache: false,
        processData: false,
        contentType: false,

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}
//////////////////////////////////////////////////

function createPaginationVue(flipPage) {
    var paginationVue = new Vue({
        el: '#pagination',
        data: {
            pages: [], localPage: 1, pageCount: 0
        },
        methods: {
            inquirePageCount: function (inquirePageCountFunc) {
                inquirePageCountFunc(this.loadData);
            },
            flipPage: function (page) {
                flipPage(page);
                this.localPage = page;
                var pageLen = 10;
                var start = this.localPage - pageLen;
                var end = this.localPage + pageLen;
                if (start < 1) {
                    start = 1;
                }
                if (end > this.pageCount) {
                    end = this.pageCount
                }
                this.pages = [];
                for (var i = start; i <= end; i++) {
                    this.pages.push(i);
                }
            },
            loadData: function (data) {
                if (data.result) {
                    this.pageCount = data.data;
                }
            }
        }
    });
    return paginationVue;
}

function inquirePersonPageCount(loadData) {
    $.ajax({
        url: getRootUrl() + '/personnel/api/inquirePersonPageCount',
        type: 'get',
        data: {"token": getToken()},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        async: false,

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function inquireMalfunctionPageCount(loadData) {
    $.ajax({
        url: getRootUrl() + '/monitor/api/inquireMalfunctionPageCount',
        type: 'get',
        data: {"token": getToken()},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        async: false,

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

/////////////////////////////////////////////////////

function createStackedNavigationVue() {
    var stackedNavigation = new Vue({
        el: '#stackedNavigation',
        data: {
            navigations: []
        },
        methods: {
            loadData: function (data) {
                if (data.result) {
                    this.navigations = data.data;
                }
            }
        }
    });
    return stackedNavigation;
}

//////////////////////////////////////////////////


//////////////////////////////////////////////////

function createListPermissionTableVue() {
    var listPermissionTableVue = new Vue({
        el: '#listPermissionTable',
        data: {
            permissions: [], permission: 0, remark: null
        },
        methods: {
            inquirePermission: function (permission) {
                inquirePermission(this.loadData, permission);
            },
            inquireAllPermission: function () {
                inquireAllPermission(this.loadData);
            },
            addPermission: function () {
                addPermission(this.permission, this.remark);
                this.inquireAllPermission();
            },
            removePermission: function (permission) {
                removePermission(permission);
                this.inquireAllPermission();
            },
            changePermission: function (permission) {
                let els = this.$refs.listPermissionTable.querySelectorAll('input');
                for (let i = 0; i < els.length; i++) {
                    if (els[i].name != '' && els[i].name == permission) {
                        changePermission(permission, els[i].value);
                        els[i].value = '';
                        this.inquireAllPermission();
                        break;
                    }
                }
            },
            loadData: function (data) {
                if (data.result) {
                    this.permissions = data.data;
                }
            }
        }
    });
    return listPermissionTableVue;
}

function inquirePermission(loadData, permission) {
    if (permission == null || permission == "") {
        alert("无效权限值: " + permission);
        return;
    }
    $.ajax({
        url: getRootUrl() + '/personnel/api/inquirePermission',
        type: 'get',
        data: {"token": getToken(), "permission": permission},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function inquireAllPermission(loadData) {
    $.ajax({
        url: getRootUrl() + '/personnel/api/inquireAllPermission',
        type: 'get',
        data: {"token": getToken()},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function addPermission(permission, remark) {
    $.ajax({
        url: getRootUrl() + '/personnel/admin/addPermission',
        type: 'post',
        data: {permission: permission, remark: remark},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        async: false,

        error: ajaxErrorDeal,
        success: function (data) {
            alert(data.data);
        }
    });
}

function removePermission(permission) {
    if (permission == null || permission == '') {
        alert("无效权限编号: " + permission);
        return;
    }
    if (confirm("会连同其授权一并删除，确认删除权限?: " + permission)) {
        $.ajax({
            url: getRootUrl() + '/personnel/admin/removePermission',
            type: 'post',
            data: {"permission": permission},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            async: false,

            error: ajaxErrorDeal,
            success: function (data) {
                alert(data.data);
            }
        });
    }
}

function changePermission(permission, remark) {
    if (remark == null || remark == '') {
        alert("无修改内容");
        return;
    }
    $.ajax({
        url: getRootUrl() + '/personnel/admin/changePermission',
        type: 'post',
        data: {permission: permission, remark: remark},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        async: false,

        error: ajaxErrorDeal,
        success: function (data) {
            alert(data.data);
        }
    });
}

//////////////////////////////////////////////////

function createListPersonTableVue() {
    var listPersonTableVue = new Vue({
        el: '#listPersonTable',
        data: {
            persons: [], rootUrl: getRootUrl()
        },
        methods: {
            inquirePersonById: function (id) {
                inquirePersonById(this.loadData, id);
            },
            inquirePersons: function (page) {
                inquirePersons(this.loadData, page);
            },
            removePerson: function (id) {
                removePerson(id);
                this.inquirePersons(1);
            },
            loadData: function (data) {
                if (data.result) {
                    this.persons = data.data;
                }
            }
        }
    });
    return listPersonTableVue;
}

function inquirePersonById(loadData, id) {
    if (id == null || id == "") {
        alert("无效id: " + id);
        return;
    }
    $.ajax({
        url: getRootUrl() + '/personnel/api/inquirePersonById',
        type: 'get',
        data: {"token": getToken(), "id": id},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function inquirePersons(loadData, page) {
    if (page == null || page == "") {
        alert("无效页面: " + page);
        return;
    }
    $.ajax({
        url: getRootUrl() + '/personnel/api/inquirePersons',
        type: 'get',
        data: {"token": getToken(), "page": page},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function removePerson(id) {
    if (id == null || id == '') {
        alert("无效id: " + id);
        return;
    }
    if (confirm("会连同其授权一并删除，确认删除人员?: " + id)) {
        $.ajax({
            url: getRootUrl() + '/personnel/admin/removePerson',
            type: 'post',
            data: {"id": id},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            async: false,

            error: ajaxErrorDeal,
            success: function (data) {
                alert(data.data);
            }
        });
    }
}

//================================================

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

function changePerson(id, name, sex, college, grade, professionClass, phone, cornet, qq, birthday, introduction, team, password, pw) {
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
    if (password == null || password == '' || pw == null || pw == '') {
        alert("密码怎么可以为空");
        return;
    }
    if (password != pw) {
        alert("两次密码不一样了呀");
        return;
    }
    $.ajax({
        url: getRootUrl() + '/personnel/admin/changePerson',
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
                location.href = getRootUrl() + '/personnel/page/listPerson';
            }
        }
    });
}

/////////////////////////////////////////////////////////////////////////////////

function createListAuthorizedTableVue() {
    var listAuthorizedTableVue = new Vue({
        el: '#listAuthorizedTable',
        data: {
            authorizeds: [], permissions: [], personId: null, permission: 0
        },
        methods: {
            inquireAuthorizedById: function (id) {
                inquireAuthorizedById(this.loadData, id);
            },
            inquireAuthorizedByPermission: function (permission) {
                inquireAuthorizedByPermission(this.loadData, permission);
            },
            inquireAllAuthorized: function () {
                inquireAllAuthorized(this.loadData);
            },
            addAuthorized: function () {
                addAuthorized(this.personId, this.permission);
                this.inquireAllAuthorized();
            },
            removeAuthorized: function (personId, permission) {
                removeAuthorized(personId, permission);
                this.inquireAllAuthorized();
            },
            loadPermissions: function (data) {
                if (data.result) {
                    this.permissions = data.data;
                }
            },
            loadData: function (data) {
                if (data.result) {
                    this.authorizeds = data.data;
                }
            }
        }
    });
    return listAuthorizedTableVue;
}

function inquireAuthorizedById(loadData, id) {
    $.ajax({
        url: getRootUrl() + '/personnel/api/inquireAuthorizedById',
        type: 'get',
        data: {"token": getToken(), 'id': id},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function inquireAuthorizedByPermission(loadData, permission) {
    $.ajax({
        url: getRootUrl() + '/personnel/api/inquireAuthorizedByPermission',
        type: 'get',
        data: {"token": getToken(), 'permission': permission},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function inquireAllAuthorized(loadData) {
    $.ajax({
        url: getRootUrl() + '/personnel/api/inquireAllAuthorized',
        type: 'get',
        data: {"token": getToken()},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function addAuthorized(personId, permission) {
    if (personId == null || personId == '') {
        alert("无效学号: " + personId);
        return;
    }
    if (permission < 0) {
        alert("无效权限: " + permission);
        return;
    }
    $.ajax({
        url: getRootUrl() + '/personnel/admin/addAuthorized',
        type: 'post',
        data: {personId: personId, permission: permission},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        async: false,

        error: ajaxErrorDeal,
        success: function (data) {
            alert(data.data);
        }
    });
}

function removeAuthorized(personId, permission) {
    if (personId == null || personId == '') {
        alert("无效学号: " + personId);
        return;
    }
    if (permission == null || permission == '') {
        alert("无效权限: " + permission);
        return;
    }
    if (confirm("确认删除" + personId + "的权限: " + permission + "?")) {
        $.ajax({
            url: getRootUrl() + '/personnel/admin/removeAuthorized',
            type: 'post',
            data: {personId: personId, permission: permission},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            async: false,

            error: ajaxErrorDeal,
            success: function (data) {
                alert(data.data);
            }
        });
    }
}

////////////////////////////////////////////////////////////////////////////
function createListNetviewTableVue() {
    var listNetviewTableVue = new Vue({
        el: '#listNetviewTable',
        data: {
            builds: []
        },
        methods: {
            inquireNetview: function () {
                inquireNetview(this.loadData);
            },
            repeatNetview: function () {
                refreshNetview();
            },
            loadData: function (data) {
                if (data.result) {
                    this.builds = data.data;
                }
            }
        }
    });
    return listNetviewTableVue;
}

function inquireNetview(loadData) {
    $.ajax({
        url: getRootUrl() + '/monitor/api/inquireNetview',
        type: 'get',
        data: {"token": getToken()},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function refreshNetview() {
    $.ajax({
        url: getRootUrl() + '/monitor/admin/refreshNetview',
        type: 'post',
        data: {},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            alert(data.data);
        }
    });
}
////////////////////////////////////////////////////////////////////////////////
function createListEquipmentTableVue() {
    var listEquipmentTableVue = new Vue({
        el: '#listEquipmentTable',
        data: {
            builds: [], rootUrl: getRootUrl()
        },
        methods: {
            inquireAllEquipment: function () {
                inquireAllEquipment(this.loadData);
            },
            removeEquipment: function (id) {
                removeEquipment(id);
                this.inquireAllEquipment();
            },
            loadData: function (data) {
                if (data.result) {
                    this.builds = data.data;
                }
            }
        }
    });
    return listEquipmentTableVue;
}

function inquireAllEquipment(loadData) {
    $.ajax({
        url: getRootUrl() + '/monitor/api/inquireAllEquipment',
        type: 'get',
        data: {"token": getToken()},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function removeEquipment(id) {
    if (id == null || id == '') {
        alert("无效编号: " + id);
        return;
    }
    if (confirm("确认删除设备?: " + id)) {
        $.ajax({
            url: getRootUrl() + '/monitor/admin/removeEquipment',
            type: 'post',
            data: {id: id},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            async: false,

            error: ajaxErrorDeal,
            success: function (data) {
                alert(data.data);
            }
        });
    }
}
//////////////////////////////////////////////////////////////////
function createListPlaceTableVue() {
    var listPlaceTableVue = new Vue({
        el: '#listPlaceTable',
        data: {
            places: [], area: '龙洞', build: null, floor: null, number: null
        },
        methods: {
            inquireAllPlace: function () {
                inquireAllPlace(this.loadData);
            },
            addPlace: function () {
                addPlace(this.area, this.build, this.floor, this.number);
                this.inquireAllPlace();
                this.area = '龙洞';
                this.build = null;
                this.floor = null;
                this.number = null;
            },
            removePlace: function (area, build, floor, number) {
                removePlace(area, build, floor, number);
                this.inquireAllPlace();
            },
            loadData: function (data) {
                if (data.result) {
                    this.places = data.data;
                }
            }
        }
    });
    return listPlaceTableVue;
}

function inquireAllPlace(loadData) {
    $.ajax({
        url: getRootUrl() + '/monitor/api/inquireAllPlace',
        type: 'get',
        data: {"token": getToken()},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function addPlace(area, build, floor, number) {
    if (area == null || area == '') {
        alert("写个校区呢");
        return;
    }
    if (build == null || build == '') {
        alert("是哪个楼栋");
        return;
    }
    if (floor == null || floor == '') {
        alert(build + "的哪个楼层");
        return;
    }
    if (number == null || number == '') {
        alert("还差序号呢");
        return;
    }
    $.ajax({
        url: getRootUrl() + '/monitor/admin/addPlace',
        type: 'post',
        data: {area: area, build: build, floor: floor, number: number},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        async: false,

        error: ajaxErrorDeal,
        success: function (data) {
            alert(data.data);
        }
    });
}

function removePlace(area, build, floor, number) {
    if (confirm("确认删除地点?: " + area + '-' + build + '-' + floor + '-' + number)) {
        $.ajax({
            url: getRootUrl() + '/monitor/admin/removePlace',
            type: 'post',
            data: {area: area, build: build, floor: floor, number: number},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            async: false,

            error: ajaxErrorDeal,
            success: function (data) {
                alert(data.data);
            }
        });
    }
}
///////////////////////////////////////////////////////////////////
function createListMalfunctionTableVue() {
    var listMalfunctionTableVue = new Vue({
        el: '#listMalfunctionTable',
        data: {
            malfunctions: []
        },
        methods: {
            inquireMalfunctions: function (page) {
                inquireMalfunctions(this.loadData, page);
            },
            removeMalfunction: function (area, build, floor, number, equipmentId, malfunctionDatetime) {
                removeMalfunction(area, build, floor, number, equipmentId, malfunctionDatetime);
                this.inquireMalfunctions(1);
            },
            loadData: function (data) {
                if (data.result) {
                    this.malfunctions = data.data;
                }
            }
        }
    });
    return listMalfunctionTableVue;
}

function inquireMalfunctions(loadData, page) {
    $.ajax({
        url: getRootUrl() + '/monitor/api/inquireMalfunctions',
        type: 'get',
        data: {"token": getToken(), page: page},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function removeMalfunction(area, build, floor, number, equipmentId, malfunctionDatetime) {
    if (area == null || area == '') {
        alert("写个校区呢");
        return;
    }
    if (build == null || build == '') {
        alert("是哪个楼栋");
        return;
    }
    if (floor == null || floor == '') {
        alert(build + "的哪个楼层");
        return;
    }
    if (number == null) {
        alert("还差序号呢");
        return;
    }
    if (equipmentId == null || equipmentId == '') {
        alert("设备的编号是什么");
        return;
    }
    if (malfunctionDatetime == null || malfunctionDatetime == '') {
        alert("还不知道故障发生的时间");
        return;
    }
    if (confirm("确认删除故障记录?: " + area + '-' + build + '-' + floor + '-' + number + '-' + equipmentId + '(' + malfunctionDatetime + ')')) {
        $.ajax({
            url: getRootUrl() + '/monitor/admin/removeMalfunction',
            type: 'post',
            data: {
                area: area,
                build: build,
                floor: floor,
                number: number,
                equipmentId: equipmentId,
                malfunctionDatetime: malfunctionDatetime
            },
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            async: false,

            error: ajaxErrorDeal,
            success: function (data) {
                alert(data.data);
            }
        });
    }
}

////////////////////////////////////////////////////////////////////
function createEquipmentFormVue(func) {
    var equipmentFormVue = new Vue({
        el: '#equipmentForm',
        data: {
            equipment: {
                id: new Date().getTime(),
                model: null,
                name: null,
                buyDate: new Date().format("yyyy-MM-dd"),
                area: '龙洞',
                build: null,
                floor: null,
                number: null,
                ip: null,
                checkTimes: 3,
                isWarn: 1,
                remark: null,
                installDate: new Date().format("yyyy-MM-dd")
            }
        },
        methods: {
            inquireEquipmentById: function (id) {
                inquireEquipmentById(this.loadData, id);
            },
            post: function () {
                func(this.equipment.id, this.equipment.model, this.equipment.name, this.equipment.buyDate, this.equipment.area, this.equipment.build, this.equipment.floor, this.equipment.number, this.equipment.ip, this.equipment.checkTimes, this.equipment.isWarn, this.equipment.remark, this.equipment.installDate);
            },
            loadData: function (data) {
                if (data.result) {
                    this.equipment = data.data;
                } else {
                    this.equipment = {
                        id: '查无此机',
                        model: null,
                        name: null,
                        buyDate: null,
                        area: null,
                        build: null,
                        floor: null,
                        number: null,
                        ip: null,
                        checkTimes: 3,
                        isWarn: 1,
                        remark: null,
                        installDate: null
                    }
                }
            }
        }
    });
    return equipmentFormVue;
}

function inquireEquipmentById(loadData, id) {
    $.ajax({
        url: getRootUrl() + '/monitor/api/inquireEquipmentById',
        type: 'get',
        data: {"token": getToken(), id: id},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        error: ajaxErrorDeal,
        success: function (data) {
            loadData(data);
        }
    });
}

function addEquipment(id, model, name, buyDate, area, build, floor, number, ip, checkTimes, isWarn, remark, installDate) {
    if (id == null || id == '') {
        alert("给设备加个编号呗");
        return;
    }
    if (model == null || model == '') {
        alert("设备是啥机型");
        return;
    }
    if (buyDate == null || buyDate == '') {
        alert("这台机啥时候买的呢");
        return;
    }
    if (number == null | number == '') {
        number = 0;
    }
    if (checkTimes == null | checkTimes == '') {
        checkTimes = 0;
    }
    if (isWarn == null | isWarn == '') {
        isWarn = 0;
    }
    $.ajax({
        url: getRootUrl() + '/monitor/admin/addEquipment',
        type: 'post',
        data: {
            id: id,
            model: model,
            name: name,
            buyDate: buyDate,
            area: area,
            build: build,
            floor: floor,
            number: number,
            ip: ip,
            checkTimes: checkTimes,
            isWarn: isWarn,
            remark: remark,
            installDate: installDate
        },
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        async: false,

        error: ajaxErrorDeal,
        success: function (data) {
            alert(data.data);
            if (data.result) {
                location.href = getRootUrl() + '/monitor/page/listEquipment';
            }
        }
    });
}

function changeEquipment(id, model, name, buyDate, area, build, floor, number, ip, checkTimes, isWarn, remark, installDate) {
    if (id == null || id == '') {
        alert("给设备加个编号呗");
        return;
    }
    if (model == null || model == '') {
        alert("设备是啥机型");
        return;
    }
    if (buyDate == null || buyDate == '') {
        alert("这台机啥时候买的呢");
        return;
    }
    $.ajax({
        url: getRootUrl() + '/monitor/admin/changeEquipment',
        type: 'post',
        data: {
            id: id,
            model: model,
            name: name,
            buyDate: buyDate,
            area: area,
            build: build,
            floor: floor,
            number: number,
            ip: ip,
            checkTimes: checkTimes,
            isWarn: isWarn,
            remark: remark,
            installDate: installDate
        },
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        async: false,

        error: ajaxErrorDeal,
        success: function (data) {
            alert(data.data);
            if (data.result) {
                location.href = getRootUrl() + '/monitor/page/listEquipment';
            }
        }
    });
}
