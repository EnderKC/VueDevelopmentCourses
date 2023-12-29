interface UserLogin {
    userName: string;
    userPassword: string;
}
interface UserRegister extends UserLogin {
    userPassword2:string;
    userEmail:string;
    verificationCode:string;
}
interface SendVerificationCode{
    userEmail:string;
}
interface UserInfo{
    userId:string;
    address:string;
    address2:string;
    phoneNumber:string;
    qqNumber:string;
    wechatNumber:string;
}
export type {UserLogin,UserRegister,SendVerificationCode,UserInfo};