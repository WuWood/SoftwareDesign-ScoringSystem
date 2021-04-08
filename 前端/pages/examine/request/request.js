// pages/examine/request/request.js
Page({

  formSubmit: function(e)
  {
    var description = e.detail.value.textarea;
    var that = this;
    if(description != "")
    {
      if(that.data.contestid != null)
      {
        var data =  "id="+that.data.contestid+"&description="+description+"&action=request&type=2";
      }
      else var data = "description="+description+"&action=request&type=1";
      wx.request({
        url: 'http://127.0.0.1:8080/test/ExamineJudges',
        header: {
          "Content-Type": "application/x-www-form-urlencoded",
          "Cookie": wx.getStorageSync('JSESSIONID')
        },
        method: "POST",
        data: data,
        success: function (res) {
          console.log(res);
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
            case 5:
              wx.showToast({
                title: '错误提交',
                icon: 'none',
                duration: 2000,
              });
              break;
            default:
              break;
          }
        },
        fail: function (res) {
          wx.showToast({
            title: '提交失败，请检查网络状况',
            icon: 'none',
            duration: 2000,
          });
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
    contestid: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    var id = options.id;
    that.setData
    ({
      contestid: id
    });
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