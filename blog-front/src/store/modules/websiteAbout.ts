import { defineStore } from "pinia";

export interface WbsiteAboutState {
    startDate?: string
}
export const useWbsiteAboutStore = defineStore("WbsiteAbout", {
    state: (): WbsiteAboutState => {
        return {
            startDate: "2024-01-01"
        }
    },
    actions: {

    }
})