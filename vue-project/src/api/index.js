import http from "@/utils/http";
const URLS = {
    LOGIN: ["login.json", "GET"],
    LOGOUT: ["logout.json", "GET"],
    GET_MEUNS: ['meun.json', "GET"],
    GET_MEUN_COUNT: ['count.json', "GET"],
    GET_CAR_COUNT: ['car_bar.json', "GET"],
    GET_PROJECT_INFO: ['/menu/getQualityOverview', 'GET'],
    SAVE_FORMS_TYPES: ['save_forms_types', 'POST'],
    SAVE_PROJECT_INFO: ['save_project', 'POST'],
    SAVE_VERSION_INFO: ['save_version', 'POST'],
    SAVE_CASE_INFO: ['save_case', 'POST'],
    GET_TEST_MANAGERMENT: ['testmanage.json', 'GET'],
    GET_VIEW_PAGE: ['reviewpage.json', 'GET'],
    DEL_VIEW_PAGE_RECORDS: ['del_records', 'POST'],
    SAVE_VIEW_PAGE_RECORDS: ['save_records', 'POST'],
    GET_BUGS_SELECT: ['bug-select.json', 'GET'],
    GET_BUGS_DATA: ['bug-data.json', 'GET'],
    SAVE_BUGS_DATE: ['save_bug', 'POST'],
    DEL_BUGS: ['del_bug', 'POST'],
    FORM_CONTROLLER: ['/menu/getQualityViewLayout', "GET"],
    FORM_CONTROLLER_SAVE: ['form_save', 'POST']
};

export function login(params) {
    return http({
        url: "",
        method: "post",
        data: params
    });
}

export function uploadFileRequest(url, params) {
    return http({
        method: "post",
        url: url,
        data: params,
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
}
export function loginbyUser(username, password) {
    const data = {
        username,
        password
    };
    return http({
        url: URLS.LOGIN[0],
        method: URLS.LOGIN[1],
        data
    });
}

export function logout() {
    return http({
        url: URLS.LOGOUT[0],
        method: URLS.LOGOUT[1]
    });
}

export function getUserInfo(username) {
    return http({
        url: "/user/info",
        method: "get",
        params: { username }
    });
}

export function getMeunList() {
    return http({
        url: URLS.GET_MEUNS[0],
        method: URLS.GET_MEUNS[1]
    });
}
export function getMeunCount() {
    return http({
        url: URLS.GET_MEUN_COUNT[0],
        method: URLS.GET_MEUN_COUNT[1]
    });
}
export function getCarCount(params) {
    return http({
        url: URLS.GET_CAR_COUNT[0],
        method: URLS.GET_CAR_COUNT[1],
        params: params
    });
}
export function getProjectInfo(params) {
    return http({
        url: URLS.GET_PROJECT_INFO[0],
        method: URLS.GET_PROJECT_INFO[1],
        params: params
    });
}
export function saveFormsTypes(params) {
    return http({
        url: URLS.SAVE_FORMS_TYPES[0],
        method: URLS.SAVE_FORMS_TYPES[1],
        data: params
    });
}
export function saveProjectInfo(params) {
    return http({
        url: URLS.SAVE_PROJECT_INFO[0],
        method: URLS.SAVE_PROJECT_INFO[1],
        data: params
    });
}
export function saveVersionInfo(params) {
    return http({
        url: URLS.SAVE_VERSION_INFO[0],
        method: URLS.SAVE_VERSION_INFO[1],
        data: params
    });
}
export function saveCaseInfo(params) {
    return http({
        url: URLS.SAVE_CASE_INFO[0],
        method: URLS.SAVE_CASE_INFO[1],
        data: params
    });
}
export function getTestManager(params) {
    return http({
        url: URLS.GET_TEST_MANAGERMENT[0],
        method: URLS.GET_TEST_MANAGERMENT[1],
        params: params
    });
}
export function getReviewPage(params) {
    return http({
        url: URLS.GET_VIEW_PAGE[0],
        method: URLS.GET_VIEW_PAGE[1],
        params: params
    });
}
export function delRecords(params) {
    return http({
        url: URLS.DEL_VIEW_PAGE_RECORDS[0],
        method: URLS.DEL_VIEW_PAGE_RECORDS[1],
        data: params
    });
}
export function saveRecords(params) {
    return http({
        url: URLS.SAVE_VIEW_PAGE_RECORDS[0],
        method: URLS.SAVE_VIEW_PAGE_RECORDS[1],
        data: params
    });
}
export function saveBugs(params) {
    return http({
        url: URLS.SAVE_BUGS_DATE[0],
        method: URLS.SAVE_BUGS_DATE[1],
        data: params
    });
}
export function delBugs(params) {
    return http({
        url: URLS.DEL_BUGS[0],
        method: URLS.DEL_BUGS[1],
        data: params
    });
}
export function getBugSelect() {
    return http({
        url: URLS.GET_BUGS_SELECT[0],
        method: URLS.GET_BUGS_SELECT[1]
    });
}
export function getBugData(params) {
    return http({
        url: URLS.GET_BUGS_DATA[0],
        method: URLS.GET_BUGS_DATA[1],
        params: params
    });
}
export function getFormControl() {
    return http({
        url: URLS.FORM_CONTROLLER[0],
        method: URLS.FORM_CONTROLLER[1]
    });
}
export function saveFormControl(params) {
    return http({
        url: URLS.FORM_CONTROLLER_SAVE[0],
        method: URLS.FORM_CONTROLLER_SAVE[1],
        data: params
    });
}
// export const uploadUrl  =  "https://jsonplaceholder.typicode.com/posts/";
export const uploadUrl  =  "/file/upload/";