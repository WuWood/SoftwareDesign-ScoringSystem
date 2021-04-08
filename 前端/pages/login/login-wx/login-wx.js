// pages/login/login-wx/login-wx.js
// 获取应用实例
const app = getApp()
const domain = app.globalData.domain;

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    motto: "将使用微信账户进行登录"
  },

  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  
  onLoad() {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }

    // 设定页面问候语
    this.setData({
      motto: "将使用微信账户 " + app.globalData.userInfo.nickName + " 进行登录"
    })
    
  },

  /*微信登录事件*/
  create_login_wx: function (e) {
    wx.login({
      success (res) {
        if (res.code) {
          
          console.log(app.globalData.userInfo.nickName);
          console.log(res.code);

          // 发起微信登录请求
          wx.request({
            url: domain + "/LoginServlet2",
            data: {
              nickname: app.globalData.userInfo.nickName,
              code: res.code,
              method: "WeChatLogin"
            },
            method: "POST",
            header: {
              //'content-type': 'application/json' // 默认值
              'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
            },
            
            // 处理返回数据
            success: function (res) {
    
              console.log("......Successful Request......");
              console.log(res.data);
          
              // Cookies
              if (res && res.header && res.header['Set-Cookie']) {
                wx.setStorageSync('cookieKey', res.header['Set-Cookie']); //保存Cookie到Storage
              }
              let cookie = wx.getStorageSync('cookieKey'); //取出Cookie
              let header = { 'Content-Type': 'application/x-www-form-urlencoded'};
              if (cookie) {
                  header.Cookie = cookie;
              }
              console.log(cookie)
          
              // 页面交互逻辑
              if (res.data == "1" || res.data == "3") {
                wx.showToast({
                  title: "微信登录成功",
                  duration: 2000
                })
                setTimeout(function () {
                  wx.navigateBack({
                    delta: 2 //返回上一页再返回上一页（返回到主页）
                  })
                }, 1000)
              }
              else if(res.data == "2" || res.data == "4") {
                wx.showToast({
                  title: "微信登录失败",
                  icon: 'none',
                  duration: 3000
                })
              }
          
            },
          })

        }
        else {
          console.log('无法获取临时登录凭证code' + res.errMsg);
        }
      }
    })
  },

})
