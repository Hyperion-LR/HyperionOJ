/**
 * 用户登录信息
 */
export interface UserInfo {
  /**
  * 用户id
  */
  id: string;
  /**
   * 头像
   */
  avatar: string;
  /**
   * 用户名
   */
  username: string;
  /**
   * 邮箱
   */
  mail: string;

  /**
   * 题目通过数量
   */
  problemAcNumber: string;

  /**
   * 提交通过数量
   */
  problemSubmitAcNumber: string;

  /**
   * 提交总数量
   */
  problemSubmitNumber: string;

  /**
   * job总数量
   */
  jobNumber: string;

  /**
   * cpu使用情况
   */
  cpuUsage: string;

  /**
   * 内存使用情况
   */
  memUsage: string;

  /**
   * 创建时间
   */
  createTime: string;

  /**
   * 最后一次登录时间
   */
  lastLogin: string;
}