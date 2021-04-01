// pages/login/login-of-wechat/login-wx.js
// 获取应用实例
const app = getApp();
const domain = app.globalData.domain;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  getUserInfo(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })

    this.create_login_wx(e)
  },

  create_login_wx: function (e) {
    wx.login({
      success (res) {
        if (res.code) {
          
          console.log(e.detail.userInfo.nickName);
          console.log(res.code);

          //发起网络请求
          wx.request({
            url: domain + "/LoginServlet2",
            data: {
              nickname: e.detail.userInfo.nickName,
              code: res.code,
              method: "WeChatLogin"
            },
            method: "POST",
            header: {
              //'content-type': 'application/json' // 默认值
              'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
            },
            
            success: function (res) {
              console.log(res.data);
              console.log(".....success request.....");
          
              if (res && res.header && res.header['Set-Cookie']) {
                wx.setStorageSync('cookieKey', res.header['Set-Cookie']); //保存Cookie到Storage
              }
          
              let cookie = wx.getStorageSync('cookieKey'); //取出Cookie
              let header = { 'Content-Type': 'application/x-www-form-urlencoded'};
              if (cookie) {
                  header.Cookie = cookie;
              }
              console.log(cookie)
          
              if (res.data == "1" || res.data == "3") {
                wx.showToast({
                  title: "登录成功",
                  duration: 2000
                })
                // wx.navigateTo({
                //   url: '/pages/index/index',
                // })
                setTimeout(function () {
                  wx.navigateBack({
                    delta: 1 //返回上一页
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
          console.log('无法获取临时登录凭证code' + res.errMsg)
        }
      }
    })
  },

  goto_login: function() {
    wx.navigateTo({
      url: '/pages/login/login/login',
    })
  }

})