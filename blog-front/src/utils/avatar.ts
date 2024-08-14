// var fallbackAvatar = require("@/assets/image/fallback-avatar.jpg");
let fallbackAvatar = "/image/fallback-avatar.jpg";

/**
 * 获取随机头像 url
 * @returns 头像 url
 */
function getRandomAvatar() {
    let n = Math.random() * 100 as Number
    return `https://avatars.githubusercontent.com/u/683682${n}?s=100&v=4`
}

export {fallbackAvatar, getRandomAvatar}