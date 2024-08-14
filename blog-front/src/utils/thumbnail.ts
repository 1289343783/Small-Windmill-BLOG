const defaultThumbnail = "@/assets/image/article-thumbnail.jpg";

/**
 * 使用默认缩略图
 * @param {Event} event 错误事件
 */
function useDefaultThumbnail(event: any) {
    event.target.src = defaultThumbnail;
}

export {defaultThumbnail, useDefaultThumbnail}