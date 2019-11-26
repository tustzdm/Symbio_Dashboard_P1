const POS_READ = 1;
const POS_WRITE = 2;
const POS_EXECUTION = 3;

function isRoleEnable(role, pos) {
  return ((role >>> (pos-1) ) & 0x01) == 0x01;
}

export default isRoleEnable ;
