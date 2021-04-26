// index.js
// 获取应用实例
const app = getApp()

Page({
  
  /**
   * 页面的初始数据
   */
  data: {
    onLine: false,
    nickName: "请点击头像登录",
    avatar: "https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132"
  },

  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    
    // 有Cookie则设为在线状态
    if (wx.getStorageSync('JSESSIONID') != "") {
      this.setData({
        onLine: true,
      })

      // 使用微信登录，则获取微信帐号的昵称、头像
      if (wx.getStorageSync('userInfo') != "") {
        let userInfo = wx.getStorageSync('userInfo');
        
        this.setData({
          nickName: userInfo.nickName,
          avatar: userInfo.avatarUrl
        })
      }
      // 使用传统登录，则获取用户名，并使用默认头像
      else {
        this.setData({
          nickName: wx.getStorageSync('username'),
        })
      }
    }

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      title: "评分熊",
      path: "/pages/index/index",
      imageUrl: "/image/card.png",
      success: function (res) {
            console.log(res)
      }
    }
  },
  onShareTimeline: function () {
    return {
      title: "评分熊",
      path: "/pages/index/index",
      success: function (res) {
            console.log(res)
      }
    }
  },


  /*按钮事件 - 打开登录面板*/
  ToLogin: function() {
    wx.navigateTo({
      url: "/pages/login/login-before",
    })
  },

  /*按钮事件 - 退出账户*/
  LogOut: function() {
    wx.showModal({
      title: "是否退出当前账号？",
      success (res) {
        if (res.confirm) {
          wx.clearStorageSync(); // 清除Storage
          wx.reLaunch({
            url: "/pages/index/index" // 重新加载主页
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  /*按钮事件 - 打开参赛页*/
  NavToShow: function() {
    wx.navigateTo({
      url: "/pages/show/show",
    })
  },
  
})
