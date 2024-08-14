<template>
  <div class="login">
    <BlogFormCard title="欢迎前辈回家">
      <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules"
          class="login-ruleForm"
          label-width="0px"
      >
        <!-- 用户名 -->
        <el-form-item prop="userName">
          <el-input
              v-model="ruleForm.userName"
              class="w-50 m-3"
              placeholder="用户名"
              size="large"
          >
            <template #prefix>
              <el-icon class="el-input__icon">
                <User/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input
              v-model="ruleForm.password"
              class="w-50 m-2"
              placeholder="密码"
              size="large"
              type="password"
          >
            <template #prefix>
              <el-icon class="el-input__icon">
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <!-- 记住我 -->
        <el-form-item prop="type">
          <el-checkbox label="记住密码" name="type"></el-checkbox>
        </el-form-item>

        <!-- 登录按钮 -->
        <el-form-item>
          <BlogButton @click="submitForm(ruleFormRef)"
          >登录
          </BlogButton>
        </el-form-item>
      </el-form>
      <p id="signup-hint">
        前辈还没有账号？
        <router-link to="/register">快来注册一个吧！</router-link>
      </p>
    </BlogFormCard>
  </div>
</template>

<script lang="ts" setup>
import {ref, reactive, onMounted} from "vue";
import {User, Lock} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import {login} from "@/api/user";
import {
  setToken,
  setUserInfo,
  clearToken,
  removeUserInfo,
} from "@/utils/auth";
import {md5Encryption} from "@/utils/encrypt";
import router from "../router/index";
import {useAdminStore} from "@/store/modules/user";
import BlogFormCard from "@/components/BlogFormCard.vue";
import BlogButton from "@/components/BlogButton.vue";

const adminStore = useAdminStore()
let ruleFormRef = ref();

let ruleForm = reactive({
  userName: "",
  password: "",
});

let rules = reactive({
  userName: [
    {
      required: true,
      message: "用户名不能为空",
      trigger: "change",
    },
  ],
  password: [
    {required: true, message: "密码不能为空", trigger: "change"},
  ],
});

const submitForm = (form: any) => {
  if (!form) return;

  form.validate(async (valid: any) => {
    if (!valid) {
      ElMessage.error("用户名和密码不能为空");
      return;
    }
    const res = await login(ruleForm.userName, md5Encryption(ruleForm.password))
    if (res.code == 200) {
      setToken(res.data.token);
      setUserInfo(res.data.userInfo);
      let user = res.data.userInfo
      user.githubUrl = user.githubUrl ? user.githubUrl : adminStore.$state.adminInfo?.githubUrl
      adminStore.setAdminInfo(user)
      await router.push("/");
    }
  });
}

onMounted(() => {
  clearToken();
  removeUserInfo();
})
</script>

<style scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url("/login.jpg");
  background-size: cover;
  text-align: center;
}

#signup-hint {
  font-size: 14px;
}

#signup-hint a {
  font-size: 14px;
  color: #1890ff;
  text-decoration: none;
}

#signup-hint a:hover {
  text-decoration: underline;
}
</style>