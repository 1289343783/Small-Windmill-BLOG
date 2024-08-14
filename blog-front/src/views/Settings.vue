<template>
  <div id="settings">
    <!-- 页头 -->
    <BlogHeader/>

    <!-- 二次元封面 -->
    <BlogWifeCover>
      <h1>个人中心</h1>
    </BlogWifeCover>

    <!-- 个人信息 -->
    <div class="user-info-card">
      <!-- 用户封面 -->
      <div class="user-cover">
        <img alt="封面" src="/settings.jpg"/>
      </div>

      <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules"
          class="login-ruleForm"
          label-width="0px"
      >
        <!-- 用户头像 -->
        <div>
          <div class="user-avatar">
            <img id="avatar" ref="avatar" :src="avatar" alt="头像"/>
            <div
                v-show="isInEditMode && !isProgressVisible"

                class="user-avatar-editor"
                @click="openFileDialog"
            >
              <div class="camera-icon">
                <el-icon>
                  <Icon icon="lucide:camera"/>
                </el-icon>
              </div>
              <div>编辑头像</div>
            </div>
            <input
                id="file-input"
                ref="fileInput"
                accept="image/*"
                style="display: none"
                type="file"
                @change="uploadAvatar"

            />
            <el-progress v-show="isProgressVisible" id="progress" :percentage="progress" type="circle"/>
          </div>

        </div>

        <!-- 用户信息 -->
        <div class="user-info">
          <div class="user-info-item">
            <span class="user-info-label">用户名</span>
            <span class="user-info-value">{{ userInfo.userName }}</span>
          </div>

          <div class="user-info-item">
            <span class="user-info-label">昵称</span>
            <span v-show="!isInEditMode" class="user-info-value">{{ userInfo.nickName }}</span>
            <el-form-item v-show="isInEditMode" prop="nickName">
              <el-input
                  v-model="ruleForm.nickName"
                  class="w-50 m-3"
                  placeholder="请输入前辈的昵称"
                  size="large"
              />
            </el-form-item>
          </div>

          <div class="user-info-item">
            <span class="user-info-label">性别</span>
            <span v-show="!isInEditMode" class="user-info-value">{{
                userInfo.sex == "0" ? "男" : "女"
              }}</span>
            <el-form-item prop="sex">
              <el-radio-group v-show="isInEditMode" v-model="ruleForm.sex">
                <el-radio label="男" value="0"/>
                <el-radio label="女" value="1"/>
              </el-radio-group>
            </el-form-item>
          </div>

          <div class="user-info-item">
            <span class="user-info-label">个性签名</span>
            <span v-show="!isInEditMode" class="user-info-value">{{ userInfo.signature }}</span>
            <el-form-item prop="signature">
              <el-input
                  v-show="isInEditMode"
                  v-model="ruleForm.signature"
                  class="w-50 m-3"
                  placeholder="用一句话来介绍下自己吧"
                  size="large"
              />
            </el-form-item>
          </div>

          <div class="user-info-item">
            <span class="user-info-label">电子邮箱</span>
            <span v-show="!isInEditMode" class="user-info-value">{{ userInfo.email }}</span>
            <el-form-item prop="email">
              <el-input
                  v-show="isInEditMode"
                  v-model="ruleForm.email"
                  class="w-50 m-3"
                  placeholder="请输入前辈的电子邮箱"
                  size="large"
                  type="email"
              />
            </el-form-item>
          </div>

          <div class="user-info-item">
            <span class="user-info-label">手机号码</span>
            <span v-show="!isInEditMode" class="user-info-value">{{
                userInfo.phonenumber || "未知"
              }}</span>
            <el-form-item prop="phonenumber">
              <el-input
                  v-show="isInEditMode"
                  v-model="ruleForm.phonenumber"
                  class="w-50 m-3"
                  placeholder="请输入前辈的手机号码"
                  size="large"
              />
            </el-form-item>
          </div>
        </div>
      </el-form>

      <div class="card-footer">
        <el-button
            v-show="!isInEditMode"
            color="#1892ff"
            type="primary"
            @click="()=>{
              Object.assign(ruleForm, userInfo);
              isInEditMode = true
            }"
        >编辑信息
        </el-button
        >
        <el-button
            v-show="isInEditMode"
            color="#1892ff"
            type="primary"
            @click="saveEditInfo(ruleFormRef)"
        >保存
        </el-button
        >
        <el-button v-show="isInEditMode" @click="cancelEditInfo">取消</el-button>
        <el-button v-show="!isInEditMode" type="danger" @click="handleLogout"
        >退出登录
        </el-button
        >
      </div>
    </div>

    <!-- 页脚 -->
    <BlogFooter/>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref, toRefs} from "vue";
import {setUserInfo} from "@/utils/auth";
import {getUserInfoApi, logout, updateUserInfoApi} from "@/api/user";
import router from "../router";
import {uploadImageApi} from "@/api/image";
import {Icon} from "@iconify/vue";
import {ElMessage} from "element-plus";
import {useAdminStore} from "@/store/modules/user";
import BlogWifeCover from "@/components/BlogWifeCover.vue";
import BlogFooter from "@/components/BlogFooter.vue";
import BlogHeader from "@/components/BlogHeader.vue";

const adminStore = useAdminStore()
let userInfo = ref<IUserInfo>({
  avatar: "",
  email: "",
  id: 0,
  isAdmin: false,
  nickName: "",
  phonenumber: "",
  sex: "",
  signature: "",
  type: "",
  userName: ""
});
let isInEditMode = ref(false);
let ruleFormRef = ref();
let isProgressVisible = ref(false);
const timer = ref()
let progress = ref(0);
let ruleForm = reactive({
  nickName: userInfo.value.nickName,
  sex: userInfo.value.sex == "0" ? "男" : "女",
  email: userInfo.value.email,
  signature: userInfo.value.signature,
  phonenumber: userInfo.value.phonenumber,
  avatar: userInfo.value.avatar,
});

let rules = reactive({
  nickName: [
    {
      required: true,
      message: "昵称不能为空",
      trigger: "blur",
    },
  ],
  email: [
    {
      type: "email",
      required: true,
      message: "邮箱格式错误",
      trigger: "blur",
    },
  ],
  phonenumber: [
    {
      required: false,
      message: "手机号码格式错误",
      trigger: "blur",
      pattern: /(^((\+86)|(86))?(1[3-9])\d{9}$)|(^(0\d{2,3})-?(\d{7,8})$)/,
    },
  ],
});

function handleLogout() {
  logout().then(() => {
    router.replace("/login");
  });
}

const fileInput = ref();
const avatar = ref();

function openFileDialog() {
  // progress.value = 0
  fileInput.value.click();
}

const uploadAvatar = async () => {
  if (fileInput.value.files.length == 0) {
    return;
  }
  // // 显示进度条
  isProgressVisible.value = true;
  let file = fileInput.value.files[0];
  avatar.value.src = URL.createObjectURL(file);
  const config = {
    onUploadProgress: (progressEvent: any) => {
      let v = Math.round(progressEvent.loaded / progressEvent.total * 100)
      progress.value = v == 100 ? 80 : v
    }
  }
  const res = await uploadImageApi(file, config)
  progress.value = 40
  if (res.status == 200) {
    ruleForm.avatar = res.data.thumb.url;
    userInfo.value.avatar = res.data.thumb.url;
    saveEditInfo(ruleFormRef.value)
    progress.value = 100
    isProgressVisible.value = false;
  } else {
    ElMessage.error("哎呀，头像上传出错啦~");
    isProgressVisible.value = false;
    avatar.value.src = ruleForm.avatar;
  }
}

const saveEditInfo = (form: any) => {
  if (!form) return;
  form.validate(async (valid: any) => {
    if (!valid) {
      ElMessage.error("前辈填写的个人信息有点小问题哦~");
      return;
    }
    Object.assign(userInfo.value, ruleForm);
    userInfo.value.sex = ruleForm.sex == "1" ? "1" : "0";
    const res = await updateUserInfoApi(userInfo.value)
    if (res.code == 200) {
      setUserInfo(userInfo.value);
      await getUserInfo()
      isInEditMode.value = false;
      ElMessage.success("更新前辈个人信息成功啦");
      if (adminStore.getIsAdmin()) {
        adminStore.setAdminInfo(userInfo.value)
      }
    }
  })
}

const cancelEditInfo = () => {
  Object.assign(ruleForm, userInfo.value);
  ruleForm.sex = userInfo.value.sex == "0" ? "男" : "女";
  avatar.value.src = ruleForm.avatar;
  ruleFormRef.value.clearValidate();
  isInEditMode.value = false;
}

const getUserInfo = async () => {
  const res = await getUserInfoApi()
  if (res.code == 200) {
    userInfo.value = res.data
    avatar.value.src = userInfo.value.avatar
  }
}

const colors = [
  {color: '#f56c6c', percentage: 20},
  {color: '#e6a23c', percentage: 40},
  {color: '#5cb87a', percentage: 60},
  {color: '#1989fa', percentage: 80},
  {color: '#6f7ad3', percentage: 100},
]


onMounted(() => {
  getUserInfo()
})
</script>

<style lang="less" scoped>
.wife-cover {
  display: flex;
  align-items: center;
  justify-content: center;

  h1 {
    text-align: center;
    position: absolute;
    width: 100%;
    text-shadow: 0 3px 6px rgba(0, 0, 0, 0.4);
    font-size: 40px;
    color: white;
    line-height: 1.5;
    margin-bottom: 15px;
    padding: 0 30px;
    overflow: hidden;
    display: -webkit-box;
    text-overflow: ellipsis;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    box-sizing: border-box;
  }
}

.user-info-card {
  background: white;
  border-radius: 8px;
  box-shadow: var(--card-box-shadow);
  box-sizing: border-box;
  width: 60%;
  margin: 30px auto 20px;
  position: relative;

  .user-cover {
    height: 240px;
    overflow: hidden;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      border-top-left-radius: 8px;
      border-top-right-radius: 8px;
    }
  }

  .container {
    display: flex;
  }

  .user-avatar {
    height: 130px;
    width: 130px;
    margin-left: 35px;
    padding: 5px;
    transform: translateY(-50%);
    background: white;
    border: transparent;
    border-radius: 50%;

    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
      transition: 0.5s ease-out;

      &:hover {
        transform: rotate(360deg);
      }
    }

    .user-avatar-editor {
      width: 130px;
      height: 130px;
      color: white;
      background: rgba(0, 0, 0, 0.4);
      opacity: 1;
      transition: all 0.4s ease;
      position: absolute;
      top: 5px;
      left: 5px;
      border-radius: 50%;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;

      &:hover {
        opacity: 1;
      }

      .camera-icon {
        margin-right: 5px;
      }
    }

    #progress {
      width: 130px;
      height: 130px;
      position: absolute;
      top: 5px;
      left: 5px;
      background: rgba(255, 255, 255, 0.7);
      border-radius: 50%;

      :deep(.el-progress-circle) {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
      }
    }
  }

  .user-info {
    border-radius: 15px;
    margin: -50px 8% 20px;

    .user-info-item {
      border-bottom: 1px solid #ebebeb;
      padding: 15px 0;
      line-height: 36px;

      .user-info-label {
        position: absolute;
        font-size: 17px;
        font-weight: bold;
        color: var(--text-color);
      }

      .user-info-value {
        padding: 0 64px 0 180px;
        font-size: 16px;
        overflow: hidden;
        display: -webkit-box;
        text-overflow: ellipsis;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;
      }

      .el-form-item {
        margin-bottom: 0;
        padding-left: 180px;
      }
    }
  }

  .card-footer {
    padding: 1rem 2rem;
    background-color: rgba(0, 0, 0, 0.03);
    border-top: 1px solid rgba(0, 0, 0, 0);
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
    text-align: end;
  }
}
</style>
