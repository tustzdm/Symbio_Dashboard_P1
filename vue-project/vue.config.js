// https://cli.vuejs.org/

const path = require("path");

function resolve(dir) {
    return path.join(__dirname, dir);
}

module.exports = {
    baseUrl: process.env.baseUrl,
    outputDir: process.env.outputDir,
    lintOnSave: false,
    transpileDependencies: [
        /\/node_modules\/vue-echarts\//,
        /\/node_modules\/resize-detector\//
    ],
    configureWebpack: () => {
        if (process.env.NODE_ENV === "production") {
            // 为生产环境修改配置...
        } else {
            // 为开发环境修改配置...
        }
    },
    chainWebpack: config => {
        config.resolve.alias
            .set("vue$", "vue/dist/vue.esm.js")
            .set("@", resolve("src"))
            .set("assets", resolve("src/assets"))
            .set("components", resolve("src/components"))
            .set("api", resolve("src/api"))
            .set("utils", resolve("src/utils"))
            .set("store", resolve("src/store"))
            .set("router", resolve("src/router"));

        config.resolve.extensions
            .add(".js")
            .add(".vue")
            .add(".styl");

        config.module.rule("svg").uses.clear();
        config.module
            .rule("svg")
            .use("raw-loader")
            .loader("raw-loader");
    },

    productionSourceMap: false,
    css: {
        sourceMap: true
    },

    devServer: {
        open: process.platform === "darwin",
        host: '0.0.0.0',
        port: 8863,
        https: false,
        hotOnly: false,
        proxy: {
            '/api': {
                target: "http://127.0.0.1:9090",//serve
                // target: "http://172.27.24.62:9090",//haoge
                // target: "http://192.168.170.100:9090",//local
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        },
    }
};