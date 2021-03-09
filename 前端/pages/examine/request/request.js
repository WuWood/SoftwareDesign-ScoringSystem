// pages/examine/request/request.js
Page({

  formSubmit: function(e)
  {
    var description = e.detail.value.textarea;
    if(description != "")
    {
      wx.request({
        url: 'http://192.168.0.105:8080/test/ExamineJudges',
        header: {"Content-Type": "application/x-www-form-urlencoded"},
        method: "POST",
        data: "description="+description+"&action=request",
        success: function (res) {
          switch (res.data){
            case 1:
              wx.showToast({
                title: '提交成功',
                icon: 'success',
                duration: 2000,
              });
              break;
            case 2:
              wx.showToast({
                title: '请勿重复提交',
                icon: 'none',
                duration: 2000,
              });
              break;
            default:
              break;
          }
        },
        fail: function (res) {
        }
      });
    }
    else
    {
      wx.showToast({
        title: '描述不可以为空',
        icon: 'none',
        duration: 2000,
      });
    }
  },
  
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

  }
})