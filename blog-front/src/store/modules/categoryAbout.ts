import { getCategoryCountList } from "@/api/category";
import { defineStore } from "pinia";

export interface CategoryAboutState {
    categoryCounts?: ICategory[]
}

export const useCategoryAboutStore = defineStore("categoryCounts", {
    state: (): CategoryAboutState => {
        return {
            categoryCounts: []
        }
    },
    getters: {
        /**
         * 获取标签
         * @returns 标签集合
         */
        getCategoryCountList(): ICategory[] | undefined {
            return this.categoryCounts
        }
    },
    actions: {
        setCategory(categoryCounts: ICategory[]): void {
            this.categoryCounts = categoryCounts
        },
        async getCategoryCountsApi() {
            await getCategoryCountList()
                .then((res) => {
                    if (res.data) {
                        this.setCategory(res.data)
                    }
                })
        },
    }
})