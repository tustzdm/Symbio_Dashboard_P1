const POS_READ = 1;
const POS_WRITE = 2;
const POS_EXECUTION = 3;

function isRoleEnable(role, pos) {
  return ((role >>> (pos-1) ) & 0x01) == 0x01; //我们需要第三位的值，就要右移2位，判断最后一位是不是1，如果是1就有这个权限
}

export default isRoleEnable ;
