<template>
  <div>
    <router-view :key="$route.fullPath"/>
  </div>
</template>
<script lang="ts" setup>


</script>

<style>
@import url(https://fonts.googleapis.com/css?family=Kanit:900);
@import url(https://fonts.googleapis.com/css?family=Long+Cang&display=swap);

::-webkit-scrollbar {
  height: 14px;
  width: 4px;
}

::-webkit-scrollbar-thumb {
  background-color: rgba(15, 15, 15, 0.8);
  border-radius: 10px;
}

::-webkit-scrollbar-track {
  border-radius: 10px;
}

body {
  overflow: overlay;
  background-color: #f3f6f7;
  /* background: url("/public/settings.jpg") no-repeat;
  background-size: 100% 100%;
  background-attachment: fixed; */
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
  Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
}

:root {
  --theme-color: #19b1f5;
  --text-color: #4c4948;
  --card-box-shadow: 0 3px 8px 6px rgba(7, 17, 27, 0.05);
}

::selection {
  background: var(--theme-color);
  color: #fff;
}
</style>
