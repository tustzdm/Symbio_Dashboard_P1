let env = 'product';//设置全局的console是否生效的条件,开发情况下允许console
function selfLog (x){
    if(env === 'dev'){
        console.log(x)
    }
  }
  
export default selfLog ;