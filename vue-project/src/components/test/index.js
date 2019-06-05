import test from './test.vue'
console.log(test);
let test2 = {
    install: function(Vue) {
        Vue.component(test.name, test);
    }
}
console.log(test2);

export default test2;