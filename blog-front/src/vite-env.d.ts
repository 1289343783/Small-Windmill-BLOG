// declare module {
//     '@kangc/v-md-editor/lib/preview'
// }

declare module '*.vue' {
    import '@kangc/v-md-editor/lib/preview';
    import type { DefineComponent } from 'vue'
    // eslint-disable-next-line @typescript-eslint/no-explicit-any, @typescript-eslint/ban-types
    const component: DefineComponent<{}, {}, any>
    export default component
}


declare module '@kangc/v-md-editor/lib/theme/vuepress.js'
/// <reference types="vite/client" />
