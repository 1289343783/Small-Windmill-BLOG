import { getTagCountList } from "@/api/tsg";
import { defineStore } from "pinia";
import { ElMessage } from "element-plus";


export interface TagAboutState {
    tagCounts?: ITag[]
}

export const useTagAboutStore = defineStore("tagAbout", {
    state: (): TagAboutState => ({
        tagCounts: []
    }),
    getters: {
        /**
         * 获取标签
         * @returns 标签集合
         */
        getTagCounts(): ITag[] | undefined {
            return this.tagCounts
        }
    },
    actions: {
        setTagCounts(tagCounts: ITag[]): void {
            this.tagCounts = tagCounts
        },
        async getTagCountsApi() {
            await getTagCountList().then(res => {
                if (res.code === 200) {
                    this.setTagCounts(res.data)
                }
            }).catch(err => {
                console.log(err);
                ElMessage.error("服务器错误！！")
            })
        }
    }

})