<template>
  <div class="register">
    <BlogFormCard title="快注册一个账号吧！！">
      <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules"
          class="demo-ruleForm"
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

        <!-- 昵称 -->
        <el-form-item prop="nickName">
          <el-input
              v-model="ruleForm.nickName"
              class="w-50 m-3"
              placeholder="昵称"
              size="large"
          >
            <template #prefix>
              <el-icon class="el-input__icon">
                <Postcard/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <!-- 邮箱 -->
        <el-form-item prop="email">
          <el-input
              v-model="ruleForm.email"
              class="w-50 m-3"
              placeholder="邮箱"
              size="large"
              type="email"
          >
            <template #prefix>
              <el-icon class="el-input__icon">
                <Promotion/>
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

        <!-- 确认密码 -->
        <el-form-item prop="ensurePassword">
          <el-input
              v-model="ruleForm.ensurePassword"
              class="w-50 m-2"
              placeholder="确认密码"
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

        <!-- 注册按钮 -->
        <el-form-item>
          <BlogButton @click="submitForm(ruleFormRef)">注册</BlogButton>
        </el-form-item>
      </el-form>
      <p id="login-hint">
        前辈已有账号？
        <router-link to="/login">快去登录吧！</router-link>
      </p>
    </BlogFormCard>
  </div>
</template>

<script lang="ts" setup>
import {ref, reactive} from "vue";
import {User, Lock, Postcard, Promotion} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import {register} from "@/api/user";
import {md5Encryption} from "@/utils/encrypt";
import router from "../router/index";
import BlogFormCard from "@/components/BlogFormCard.vue";
import BlogButton from "@/components/BlogButton.vue";

let ruleFormRef = ref();

let ruleForm = reactive({
  userName: "",
  nickName: "",
  email: "",
  password: "",
  ensurePassword: "",
});

let rules = reactive({
  userName: [
    {
      required: true,
      message: "用户名不能为空",
      trigger: "change",
    },
  ],
  nickName: [{required: true, message: "昵称不能为空", trigger: "change"}],
  email: [
    {
      required: true,
      message: "邮箱地址不能为空",
      trigger: "change",
    },
    {
      type: "email",
      message: "请输入正确的邮箱地址",
      trigger: "blur",
    },
  ],
  password: [{required: true, message: "密码不能为空", trigger: "change"}],
});

const submitForm = (form: any) => {
  if (!form) return;
  form.validate(async (valid: any) => {
    if (!valid) {
      ElMessage.error("请正确填写前辈的信息");
      return;
    }

    if (ruleForm.password !== ruleForm.ensurePassword) {
      ElMessage.error("两次密码不一致");
      return;
    }

    let password = md5Encryption(ruleForm.password);
    let data: IRegisterDTO = {
      userName: ruleForm.userName,
      nickName: ruleForm.nickName,
      email: ruleForm.email,
      password: password,
      avatar: ""
    }
    const res = await register(data);
    if (res.code == 200) {
      ElMessage.success("注册用户成功 ✨");
      setTimeout(() => {
        router.push("/login");
      }, 2000);
    }
  });
}
</script>

<style scoped>
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url("/login.jpg");
  background-size: cover;
}

#login-hint {
  font-size: 14px;
}

#login-hint a {
  font-size: 14px;
  color: #1890ff;
  text-decoration: none;
}

#login-hint a:hover {
  text-decoration: underline;
}
</style>
