import Vue from "vue";
import VueRouter from "vue-router";
import _import from "@/utils/import";
const Layout = _import("layout/Layout");
Vue.use(VueRouter);

export const constantRouterMap = [
    {
        path: "/401",
        component: _import("errorPage/401"),
        hidden: true
    },
    {
        path: "/404",
        component: _import("errorPage/404"),
        hidden: true
    },
    {
        path: "/",
        component: _import("login/index"),
        hidden: true
    },
    {
        path: "/forms",
        component: Layout,
        redirect: "/forms/index",
        children: [
            {
                path: "index",
                component: _import("forms/index"),
                name: "forms",
                meta: {
                    title: "forms",
                    icon: "",
                },
            }
        ],
        hidden: true
    },
    {
        path: "/addproject",
        component: Layout,
        redirect: "/addproject/index",
        children: [
            {
                path: "index/:type?",
                component: _import("aboutproject/addproject"),
                name: "addproject",
                meta: {
                    title: "addproject",
                    icon: "",
                },
            }
        ],
        hidden: true
    },
    {
        path: "/addversion",
        component: Layout,
        redirect: "/addversion/index",
        children: [
            {
                path: "index",
                component: _import("aboutproject/addversion"),
                name: "addversion",
                meta: {
                    title: "addversion",
                    icon: "",
                },
            }
        ],
        hidden: true
    },
    {
        path: "/addcase",
        component: Layout,
        redirect: "/addcase/index",
        children: [
            {
                path: "index",
                component: _import("aboutproject/addcase"),
                name: "addcase",
                meta: {
                    title: "addcase",
                    icon: "",
                },
            }
        ],
        hidden: true
    }, {
        path: "/prdvisit",
        component: Layout,
        children: [
            {
                path: "/prdvisit/report",
                component: _import("prdvisit/report"),
                name: "report",
                meta: {
                    title: "report",
                    icon: "",
                },
            }
        ],
    },
    { path: "*", redirect: "/404", hidden: true }
];
export const router = new VueRouter({
    routes: constantRouterMap,
    scrollBehavior: () => ({ y: 0 })
});