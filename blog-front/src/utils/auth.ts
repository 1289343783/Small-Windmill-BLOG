const TOKEN_KEY = "token";
const USER_KEY = "userInfo";

const isLogin = () => {
    return !!localStorage.getItem(TOKEN_KEY);
};

/**
 * 获取令牌
 * @returns token
 */
const getToken = () => {
    return localStorage.getItem(TOKEN_KEY);
};

/**
 * 设置令牌
 * @param {string} token 令牌
 */
const setToken = (token: string) => {
    localStorage.setItem(TOKEN_KEY, token);
};

/**
 * 移除令牌
 */
const clearToken = () => {
    localStorage.removeItem(TOKEN_KEY);
};

/**
 * 获取用户信息
 * @returns userInfo
 */
const getUserInfo = () => {
    const userInfo = localStorage.getItem(USER_KEY);
    if (userInfo !== null) {
        return JSON.parse(userInfo)
    }
    return null;
}


/**
 * 设置用户信息
 * @param {object} userInfo 用户信息
 */
const setUserInfo = (userInfo: IUserInfo) => {
    localStorage.setItem(USER_KEY, JSON.stringify(userInfo))
}

/**
 * 移除用户信息
 */
const removeUserInfo = () => {
    localStorage.removeItem(USER_KEY)
}

// persist:root

const clearAll = () => {
    localStorage.clear();
};

export {isLogin, getToken, setToken, clearToken, getUserInfo, setUserInfo, removeUserInfo, clearAll};
