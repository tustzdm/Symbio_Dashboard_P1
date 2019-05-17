import _import from "@/utils/import";
import { getMeunList, getMeunCount } from "@/api/index";
const Layout = _import("layout/Layout");
let colors = ['#3398DB', "#B5C334", '#3398DB', "#F0805A"];
let menusPromise = new Promise((resolve, reject) => {
    let menusList = [];
    Promise.all([getMeunList(), getMeunCount()]).then(res => {
        let count = res[1].data;
        res[0].data.map((item, i) => {
            let itemCount = count[item.name];
            if(!item.hidden){
                itemCount["color"] = colors[i % 4];
            }
            let meun = {
                path: item.path,
                component: Layout,
                redirect: item.redirect,
                children: [
                    {
                        path: "index",
                        name: item.name,
                        component: _import(item.component),
                        count: itemCount,
                        hidden: item.hidden ? true : false,
                        meta: {
                            title: item.name,
                            icon: item.icon
                        }
                    }
                ]
            };
            menusList.push(meun);
        });
        resolve(menusList);
    });
});
export const asyncRouterMap = menusPromise;


