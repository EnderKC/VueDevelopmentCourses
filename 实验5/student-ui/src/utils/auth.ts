/*
 * @Description: 有关用户token的操作，不保存在cookie中，保存在sessionStorage
 * @Author: 李焕哲
 * @CopyRight: 河北地众光电科技有限公司
 * @Date: 2021-08-07 17:27:42
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-10-12 09:04:23
 */

import Cookies from 'js-cookie'

const prefix = 'aaa'

/* 保存token的唯一标识，必须和其它项目区别开 */
const AcccessTokenKey = prefix + '-AcccessToken'
/* 保存refresh token的key */
const RefreshTokenKey = prefix + '-RefreshToken'
/* 记录token有效期的key*/
const TokenExpiryKey = prefix + '-TokenExpiryTime'

// 是采用sessionStorage存储还是采用cookies存储
const useCookie = false

/**
 * 从本地存储中读取指定key对应的值
 * @param key key
 * @returns 返回读取的值
 */
function getItem(key: string): string | null | undefined {
  if (useCookie) {
    return Cookies.get(key)
  } else {
    return sessionStorage.getItem(key)
  }
}

/**
 * 向本地存储中保存键值对
 * @param key 键
 * @param value 值
 */
function setItem(key: string, value: string): void {
  if (useCookie) {
    Cookies.set(key, value)
  } else {
    sessionStorage.setItem(key, value)
  }
}

/**
 * 从本地存储中删除键值对
 * @param key 键
 */
function removeItem(key: string): void {
  if (useCookie) {
    Cookies.remove(key)
  } else {
    sessionStorage.removeItem(key)
  }
}

/**
 * 从本地存储中获取token
 * @returns 返回token,没有返回null
 */
function getToken(): string | null | undefined {
  if (!tokenIsExpired()) {
    return getItem(AcccessTokenKey)
  }
  return null
}

/**
 * 从本地存储中获取refresh token
 * @returns 返回token,没有返回null
 */
function getRefreshToken(): string | null | undefined {
  if (!tokenIsExpired()) {
    return getItem(RefreshTokenKey)
  }
  return null
}

/**
 * 保存token信息
 * @param accessToken 访问token
 * @param expires token有效期，秒为单位
 * @param refreshToken 刷新token
 */
function setToken(accessToken: string, expires: number, refreshToken: string) {
  setItem(AcccessTokenKey, accessToken)
  setItem(RefreshTokenKey, refreshToken)
  setItem(TokenExpiryKey, (expires * 1000 + new Date().getTime()).toString())
}

/**
 * 移除token
 */
function removeToken() {
  removeItem(TokenExpiryKey)
  removeItem(AcccessTokenKey)
  removeItem(RefreshTokenKey)
}

/**
 * 获取token过期时间
 * @returns 整数，以毫秒为单位
 */
function getTokenExpiryTime(): number | null {
  const val = getItem(TokenExpiryKey)
  if (val) {
    return Number(val)
  } else {
    return null
  }
}
/**
 * 设置token过期时间
 * @param expires token的有效期，秒为单位
 * @returns
 */
function setTokenExpiryTime(expires: number) {
  setItem(TokenExpiryKey, (expires * 1000 + new Date().getTime()).toString())
}

function removeTokenExpiryTime() {
  removeItem(TokenExpiryKey)
}

/**
 * 检测token是否已过期
 * @returns 已过期返回true
 */
function tokenIsExpired(): boolean {
  const tokenExpiryTime = getTokenExpiryTime()
  if (tokenExpiryTime === undefined || tokenExpiryTime === null) {
    return true
  }
  // console.log(new Date().getTime(), tokenExpiryTime)
  return new Date().getTime() > tokenExpiryTime
}

/**
 * 获取token的剩余时间,以秒为单位
 * @returns 整数，正整数未过期，否则已过期
 */
function getTokenRemainingTime(): number {
  const tokenExpiryTime = getTokenExpiryTime()
  if (tokenExpiryTime === undefined || tokenExpiryTime === null) {
    return 0
  }

  return (tokenExpiryTime - new Date().getTime()) / 1000
}

export default {
  getItem,
  setItem,
  removeItem,
  getToken,
  getRefreshToken,
  setToken,
  removeToken,
  getTokenExpiryTime,
  setTokenExpiryTime,
  removeTokenExpiryTime,
  tokenIsExpired,
  getTokenRemainingTime
}
