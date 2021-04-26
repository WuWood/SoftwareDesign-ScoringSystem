// pages/score/score.js
// 获取应用实例
const app = getApp()
const domain = app.globalData.domain;

Page({

  // 获取选手列表
  GetUserList: function() {
    app.Check()

    wx.request({
      url: domain + "/QueryScore",
      data:{
        "ContestId": wx.getStorageSync('ContestId'), //从Storage读取ContestId
      },
      method: 'POST',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded',
        "Cookie": wx.getStorageSync('JSESSIONID')
      },
      success: function (res) {
        console.log("......Successful Request......");
        console.log(res.data);

        wx.setStorageSync('userList', res.data);
      },
    })

  },

  /**
   * 页面的初始数据
   */
  data: {
    userList: [],
    ContestName: wx.getStorageSync('ContestName'), //从Storage读取ContestName
    ContestId: wx.getStorageSync('ContestId'), //从Storage读取ContestId
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.GetUserList();
    this.setData({
      userList: wx.getStorageSync('userList')
    })
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


  /*提交评分*/
  SubmitScore: function(e) {

    console.log(e.detail.value);

    wx.request({
      url: domain + "/AddScore",
      data: e.detail.value,
      method: 'POST',
      header: {
        'content-type': 'application/json',
        "Cookie": wx.getStorageSync('JSESSIONID')
      },
      success: function (res) {
        console.log("......Successful Request......");
        console.log(res.data);
      },
    })

  },

})
