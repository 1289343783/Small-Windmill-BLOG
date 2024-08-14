<template>
  <div class="flex items-center justify-center">
    <span v-if="text !== '' && isDirection == 'left'" class="mr-2 text-base font-bold">{{
        text
      }}</span>
    <el-icon :size="iconSize">
      <Icon :icon="iconValue"/>
    </el-icon>
    <span v-if="text !== '' && isDirection == 'right'" :class="styletValue">{{
        text
      }}</span>
  </div>
</template>

<script lang="ts" setup>
import {Icon} from "@iconify/vue";

const props = defineProps({
  iconValue: {
    type: String,
    default: "",
  },
  iconSize: {
    type: Number,
    default: 25,
  },
  text: {
    type: String,
    default: "",
  },
  isDirection: {
    type: String,
    default: "right",
  },
  styletValue: {
    type: String,
    default: "ml-2 text-base font-bold",
  },
});
</script>
