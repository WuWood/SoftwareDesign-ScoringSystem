// pages/login/login-of-wechat/login-wx.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

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
  
  create_login_wx: function (e) {
    wx.login({
      success (res) {
        if (res.code) {
          //发起网络请求
          wx.request({
            url: 'http://127.0.0.1:8080/LoginServlet',
            data: {
              code: res.code
            },
            method: "POST",
            
            success: function (res) {
              console.log(res.data);
              if (res && res.header && res.header['Set-Cookie']) {
                wx.setStorageSync('cookieKey', res.header['Set-Cookie']);   //保存Cookie到Storage
              }
              let cookie = wx.getStorageSync('cookieKey');//取出Cookie
              let header = { 'Content-Type': 'application/x-www-form-urlencoded'};
              if (cookie) {
                  header.Cookie = cookie;
              }
              console.log(cookie)
            }

          })
        } else {
          console.log('登录失败！' + res.errMsg)
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