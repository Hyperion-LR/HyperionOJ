
import { getUserInfo } from "@/api/user";
import { UserState } from "../types";

const useUserStore = defineStore("useUserStore", {
  state: (): UserState => ({
    id: "",
    avatar: "",
    mail: "",
    username: "",
    problemAcNumber: "",
    problemSubmitAcNumber: "",
    problemSubmitNumber: "",
    jobNumber: "",
    cpuUsage: "",
    memUsage: "",
    createTime: "",
    lastLoginTime: ""
  }),
  actions: {
    GetUserInfo(account: string) {
      return new Promise((resolve, reject) => {
        getUserInfo(account)
          .then(({ data }) => {
            if (data.code==200) {
              this.id = data.data.id;
              this.avatar = data.data.avatar;
              this.mail = data.data.mail;
              this.username = data.data.username;
              this.problemAcNumber = data.data.problemAcNumber;
              this.problemSubmitAcNumber = data.data.problemSubmitAcNumber;
              this.problemSubmitNumber = data.data.problemSubmitNumber;
            }
            resolve(data);
          })
          .catch((error) => {
            reject(error);
          });
      });
    },
  },
  getters: {},
  persist: {
    key: "user",
    storage: localStorage,
  },
});

export default useUserStore;
