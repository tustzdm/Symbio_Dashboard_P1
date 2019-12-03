export default {
  tags: {
    handle: "标签操作",
    closeall: "关闭所有",
    closeother: "关闭其他"
  },
  route: {
    dashboard: "首页",
    index: "Quality Qverview",
    buy: "Test Management",
    prdvisit: "Bugs",
    forms: "Forms",
    searchvisit: "Review Pages",
    addproject: "添加项目",
    addversion: "添加版本",
    addcase: "添加事件",
  },
  app: {
    switchlang: "切换语言成功"
  },
  login: {
    sysName: "智能测试系统",
    title: "系统登录",
    btn: "登录",
    radioEN: "英语",
    radioZH: "中文",
    forgetpwd: "忘记密码",
    remember: "记住我",
    userplaceholder: "请输入用户名",
    pwdplaceholder: "请输入密码",
    valid: {
      userexist: "用户名为空！",
      pwdexist: "密码为空！"
    },
    forget_email: "请输入邮箱",
    forget_code: "请输入验证码",
    forget_passwrd: "请输入新密码",
    confirm_passwrd: "请输入确认密码",
    forget_btn: "重置",
    forget_back: "返回",
    validfaild: "登陆校验未通过，是不是哪里出问题了？",
    pwdChanged: "密码重置成功！"
  },
  sidebarDropDown: {
    profile: "个人中心",
    logout: "退出登录",
    form: "表单样式",
    pageElement:"页面元素"
  },
  navBar:{
    qualityOverview: "项目概况",
    testManagement: "测试管理",
    resultReview: "执行 & 审核",
    bugsOverview: "Bug概况",
    search: {
      tip: '搜索..',
      placehold: '输入搜索内容'
    }
  },
  qualityOverview: {
    productOverview: '产品概况',
    releaseOverview: '版本概况',
    testCaseOverview: '测试用例概况',
    bugFixRate: 'Bug 解决率',
  },
  bugsoverview: {
    customize: "自定义",
    report: {
      piePriority: "优先级",
      barPriority: "优先级 / 状态"
    },
    list: {
      id: "Bug ID",
      summary: "描述",
      Assignee: "责任人",
      Reporter: "报告者",
      Priority: "优先级",
      Status: "状态"
    }
  },
  listOfPRT:{
    productList: "产品列表",
    releaseList: "版本列表",
    testsetList: "测试集列表",
    addProduct: "添加产品",
    addRelease: "添加版本",
    addTestset: "添加测试集",
    caseList: "测试用例",
    addCase: "添加测试用例"
  },
  funcBtns:{
    run: "运行",
    addBug: "标记Bug",
    refresh: "刷新",
    import: "导入",
    filter: "筛选"
  },
  chart: {
    platforms: '测试平台',
    testingType: '测试类型',
    testRunAndBug: '测试用例与Bug',
    passRateByProduct: '测试通过率/产品',
    breakdown: 'Bug 分布',
    productWorkload: '产品工作量',
    dailyTestCases: '日均测试用例',
    radarData: ['总用例', '通过用例'],
    testingTypeItems: ['Product', '单元测试', '整合测试', '系统测试'],
  },
  terms: {
    pass: '通过',
    failed: '失败',
    totalCases: '总用例',
    passCases: '通过用例',
    product: '产品',
    bugReported: '已上报Bug数',
    testRunExecuted: '已执行的测试用例',
  }
};
