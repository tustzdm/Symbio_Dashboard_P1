import test from './test.vue'
this.selfLog(test);
let test2 = {
    install: function(Vue) {
        Vue.component(test.name, test);
    }
}
this.selfLog(test2);

export default test2;